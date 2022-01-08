package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.ReservationRepoI;
import com.car_rental.Model.CompositeModel.ReservationWithCar;
import com.car_rental.Model.DatabaseModel.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepoImpl implements ReservationRepoI {

    private final JdbcTemplate template;

    @Autowired
    public ReservationRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Reservation Insert(Reservation data) throws DataAccessException, SQLException {

        final String RESERVATION_INSERTION_QUERY =
                "INSERT INTO reservation (starting_date, ending_date, total_cost, status, customer_id, depart_from) VALUES (?, ?, ?, ?, ?, ?);";


        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(RESERVATION_INSERTION_QUERY, new String[]{"reservation_id"});
            ps.setDate(1, data.getStartingDate());
            ps.setDate(2, data.getEndingDate());
            ps.setDouble(3, data.getTotalCost());
            ps.setInt(4, data.getStatus());
            ps.setInt(5, data.getCustomerId());
            ps.setInt(6, data.getDepartFrom());
            return ps;
        }, keyHolder);

        data.setReservationId((Integer) keyHolder.getKey());
        return data;
    }


    @Override
    public List<ReservationWithCar> selectResWithCarByUserId(Integer user_id) throws DataAccessException {
        return (this.template.query(
                String.format("SELECT * FROM reservation AS r NATURAL JOIN car_reservation as cr " +
                        "NATURAL JOIN car as c WHERE r.customer_id = '%s'",  user_id),
                Reservation.GetResWithCarMapper()
        ));
    }

    @Override
    public List<Reservation> selectResByUserId(Integer user_id) throws DataAccessException {

        return (this.template.query(
                String.format("SELECT * FROM reservation AS r WHERE r.customer_id = '%s'",  user_id),
                Reservation.GetRowMapper()
        ));
    }
  
    @Override
    public List<Reservation> getUnhandledReservations(Integer user_id) throws DataAccessException {
        return (this.template.query(
                String.format("SELECT * FROM reservation R WHERE R.status = 100 AND (R.depart_from = " +
                        "((SELECT M.branch_id FROM manager M WHERE M.user_id = " +
                        "(SELECT E.manager_id FROM employee E WHERE E.user_id = %s))) " +
                        "OR R.depart_from = ((SELECT M.branch_id FROM manager M WHERE M.user_id = " +
                        "(SELECT E.manager_id FROM employee E WHERE E.user_id = %s))))",  user_id, user_id),
                Reservation.GetRowMapper()
        ));
    }

    @Override
    public List<Reservation> getFinishedReservations(Integer user_id) throws DataAccessException {
        return (this.template.query(
                String.format("SELECT * FROM reservation R WHERE R.status = 500 AND (R.depart_from = " +
                        "((SELECT M.branch_id FROM manager M WHERE M.user_id = " +
                        "(SELECT E.manager_id FROM employee E WHERE E.user_id = %s))) " +
                        "OR R.depart_from = ((SELECT M.branch_id FROM manager M WHERE M.user_id = " +
                        "(SELECT E.manager_id FROM employee E WHERE E.user_id = %s))))",  user_id, user_id),
                Reservation.GetRowMapper()
        ));
    }
    public void makeUndamaged(Optional<Integer> res_id) throws DataAccessException {
        String SQL = String.format("UPDATE reservation SET status = 700 where reservation_id = %s",  res_id.get());
        this.template.update(SQL);
    }

    public void submitDamageReport(Optional<Integer> res_id, String damageReport, Double cost) throws DataAccessException {
        String SQL = String.format("UPDATE reservation SET status = 400 where reservation_id = %s",  res_id.get());
        this.template.update(SQL);
        SQL = String.format("INSERT INTO damage_report (car_id, content, cost) VALUES ((SELECT car_id FROM car_reservation WHERE reservation_id = %s), '%s', %s);",  res_id.get(), damageReport, cost);
        this.template.update(SQL);
    }

    public void approveReservation(Optional<Integer> res_id, Integer user_id) throws DataAccessException {
        String SQL = String.format("UPDATE reservation SET status = 200 where reservation_id = %s",  res_id.get());

        // update reservation status
        // delete car from car branch

        this.template.update(SQL);
        SQL = String.format("DELETE FROM car_branch WHERE car_id = (SELECT car_id FROM car_reservation WHERE reservation_id = %s) AND branch_id = (SELECT M.branch_id FROM manager M WHERE M.user_id = (SELECT E.manager_id FROM employee E WHERE E.user_id = %s))", res_id.get(), user_id);
        this.template.update(SQL);
        SQL = String.format("INSERT INTO employee_reservation (validated_by, reservation_id) VALUES (%s, %s)", user_id, res_id.get());
        this.template.update(SQL);
    }

    public void declineReservation(Optional<Integer> res_id, Integer user_id) throws DataAccessException {
        String SQL = String.format("UPDATE reservation SET status = 300 where reservation_id = %s",  res_id.get());
        this.template.update(SQL);
        SQL = String.format("INSERT INTO employee_reservation (validated_by, reservation_id) VALUES (%s, %s)", user_id, res_id.get());
        this.template.update(SQL);
    }

    @Override
    public Reservation SelectResById(Integer reservationId) {
        return this.template.queryForObject(String.format("Select * From reservation as r where r.reservation_id = %d", reservationId),
                Reservation.GetRowMapper());
    }

    @Override
    public Reservation Update(Integer id, Reservation data) throws DataAccessException, SQLException {
        this.template.update("Update reservation Set status = 500 where reservation_id = ?", id);

        return data;
    }

    @Override
    public Reservation UpdatePayment(Integer id, Reservation data) throws DataAccessException, SQLException {
        this.template.update("Update reservation Set status = 600 where reservation_id = ?", id);

        return data;
    }

    @Override
    public List<Reservation> GetAllSharedReservationFromUserId(Integer userId) throws SQLException {
        return this.template.query(
                "select  r.reservation_id, r.starting_date, r.ending_date, r.total_cost, r.penalty_cost, r.status, r.customer_id, r.depart_from, r.arrive_at from customer_shared_ride as csr\n" +
                        "join shared_ride sr on sr.ride_id = csr.ride_id\n" +
                        "join reservation r on sr.reservation_id = r.reservation_id\n" +
                        "where csr.passenger_id = " + userId + " and r.status in (200, 300, 400);",
                Reservation.GetRowMapper()
        );
    }

    @Override
    public Reservation updateEndingBranch(Integer branchId, Reservation data) throws DataAccessException {
        this.template.update("Update reservation Set arrive_at = ? where reservation_id = ?", branchId, data.getReservationId());

        return data;
    }
}
