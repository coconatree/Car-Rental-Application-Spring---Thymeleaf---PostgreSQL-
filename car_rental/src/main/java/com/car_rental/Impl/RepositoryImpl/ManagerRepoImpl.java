package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.ManagerRepoI;
import com.car_rental.Model.PrimitiveRowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerRepoImpl implements ManagerRepoI {

    private final JdbcTemplate template;

    @Autowired
    public ManagerRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public int SelectBranchFromManagerId(int managerId) {
        return this.template.queryForObject("SELECT m.branch_id FROM manager AS m WHERE m.user_id = " + managerId + ";", PrimitiveRowMappers.GetIntegerRowMapper());
    }
}
