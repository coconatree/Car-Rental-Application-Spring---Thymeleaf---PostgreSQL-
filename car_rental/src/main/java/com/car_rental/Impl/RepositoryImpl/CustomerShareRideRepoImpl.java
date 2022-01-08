package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.CustomerShareRideRepoI;
import com.car_rental.Model.CompositeModel.CustomerShareRideComposite;
import com.car_rental.Model.DatabaseModel.CustomerShareRide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerShareRideRepoImpl implements CustomerShareRideRepoI {
    private final JdbcTemplate template;

    @Autowired
    public CustomerShareRideRepoImpl(JdbcTemplate template) {
        this.template = template;
    }


    public List<CustomerShareRideComposite> getAllData(Integer userId) throws DataAccessException {
        String QUERY = "select br.branch_name,\n" +
                "       res.depart_from as start_branch_int, \n" +
                "       sh.ride_id, \n" +
                "       res.total_cost,\n" +
                "       res.starting_date,\n" +
                "       car.model,\n" +
                "       car.brand\n" +
                "       from reservation res\n" +
                "join shared_ride sh on sh.reservation_id = res.reservation_id\n" +
                "join branch br on br.branch_id = res.depart_from\n" +
                "join car_reservation cr on cr.reservation_id = res.reservation_id\n" +
                "join car on car.car_id = cr.car_id\n" +
                "where res.status in (200) and (select not exists (select * from customer_shared_ride csr where csr.ride_id = sh.ride_id and csr.passenger_id = "+userId +"))";

        return this.template.query(QUERY, CustomerShareRideComposite.GetRowMapper());
    }

    public CustomerShareRide Insert(CustomerShareRide data) throws DataAccessException, SQLException {
        final String SHARE_RIDE_INSERT_QUERY = "INSERT INTO customer_shared_ride(ride_id, passenger_id, start_branch) VALUES (?,?,?)";
        this.template.update(SHARE_RIDE_INSERT_QUERY, data.getRideId(), data.getPassengerId(), data.getStartBranch());

        return data;
    }
}
