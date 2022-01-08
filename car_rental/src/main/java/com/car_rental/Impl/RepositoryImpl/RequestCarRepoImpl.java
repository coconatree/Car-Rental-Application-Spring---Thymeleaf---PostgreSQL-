package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.RequestCarRepoI;
import com.car_rental.Model.DatabaseModel.RequestCar;
import com.car_rental.Model.PrimitiveRowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class RequestCarRepoImpl implements RequestCarRepoI {

    private final JdbcTemplate template;

    @Autowired
    public RequestCarRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RequestCar Insert(RequestCar data) throws DataAccessException, SQLException {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        final String QUERY = "INSERT INTO request_car (car_id, request_id) VALUES (?, ?);";

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(QUERY, new String[]{"request_id"});
            ps.setInt(1, data.getCarId());
            ps.setInt(2, data.getRequestId());
            return ps;
        }, keyHolder);

        System.out.println(keyHolder.getKey());

        return null;
    }

    @Override
    public boolean CheckIfRequestExist(Integer carId) {
        return this.template.queryForObject(
                "SELECT EXISTS (SELECT * FROM request_car AS rc, request AS req WHERE rc.request_id = req.request_id \n " +
                        "AND rc.car_id = " + carId + " AND req.status != 5);", PrimitiveRowMappers.GetBooleanRowMapper());
    }

    @Override
    public void AddRequestCar(Integer carId, Integer reqId) {
        this.template.update("INSERT INTO  request_car (car_id, request_id) VALUES (?, ?)", carId, reqId);
    }
}
