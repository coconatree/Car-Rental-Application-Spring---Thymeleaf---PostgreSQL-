package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.CarReservationRepoI;
import com.car_rental.Model.DatabaseModel.CarReservation;
import com.car_rental.Model.PrimitiveRowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

@Repository
public class CarReservationRepoImpl implements CarReservationRepoI {

    private final JdbcTemplate template;

    @Autowired
    public CarReservationRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public CarReservation Insert(CarReservation data) throws DataAccessException, SQLException {

        final String CAR_RESERVATION_INSERT_QUERY = "INSERT INTO car_reservation (car_id, reservation_id) VALUES (?, ?);";
        this.template.update(CAR_RESERVATION_INSERT_QUERY, data.getCarId(), data.getReservationId());

        return data;
    }

    @Override
    public boolean CheckIfReservationExist(Integer carId) {
        return this.template.queryForObject(
                "SELECT EXISTS (SELECT * FROM car_reservation AS cr, reservation AS res WHERE cr.reservation_id = res.reservation_id\n " +
                "AND cr.car_id = " + carId +  " AND res.status !=  5);", PrimitiveRowMappers.GetBooleanRowMapper());
    }
}
