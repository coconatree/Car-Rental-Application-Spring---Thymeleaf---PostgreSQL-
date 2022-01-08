package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.CarBranchRepoI;
import com.car_rental.Model.DatabaseModel.CarBranch;
import com.car_rental.Model.PrimitiveRowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class CarBranchRepoImpl implements CarBranchRepoI {

    private final JdbcTemplate template;

    @Autowired
    public CarBranchRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public CarBranch Insert(Integer carId, Integer branchId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO car_branch (car_id, branch_id) VALUES (?, ?)",
                    new String[]{"car_id"});
            ps.setInt(1, carId);
            ps.setInt(2, branchId);
            return ps;
        }, keyHolder);
        return CarBranch.builder().carId(carId).branchId(branchId).build();
    }

    @Override
    public Integer Update(Integer carId, Integer branchId) {

        this.template.update("Update car_branch Set branch_id = ? where car_id = ?", branchId, carId);
        return branchId;
    }

    @Override
    public boolean CheckIfCarInBranch(Integer branchId, Integer carId) {
        return this.template.queryForObject("SELECT EXISTS (SELECT * FROM car_branch AS cb  WHERE cb.branch_id = " + branchId + " AND cb.car_id = " + carId + ");", PrimitiveRowMappers.GetBooleanRowMapper());
    }

    @Override
    public boolean CheckIfCarAvailable(int carId) {
        return this.template.queryForObject("SELECT EXISTS (SELECT * FROM car_branch AS cb WHERE cb.car_id = " + carId + ")", PrimitiveRowMappers.GetBooleanRowMapper());
    }

}
