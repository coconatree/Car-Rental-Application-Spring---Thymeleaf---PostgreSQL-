package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Model.CompositeModel.BranchIdAndName;
import com.car_rental.Interface.RepositoryI.BranchRepoI;
import com.car_rental.Model.CompositeModel.BranchWithBudget;
import com.car_rental.Model.DatabaseModel.Branch;
import com.car_rental.Model.PrimitiveRowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BranchRepoImpl implements BranchRepoI {

    private final JdbcTemplate template;

    @Autowired
    public BranchRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<BranchIdAndName> SelectAllIdAndName() throws DataAccessException {
        return this.template.query(
                "SELECT b.branch_id, b.branch_name FROM branch AS b;",
                BranchIdAndName.GetRowMapper());
    }

    @Override
    public Branch SelectBranchWhereCarIsOn(Integer carId) {
        return this.template.queryForObject(
                "SELECT b.branch_id, b.branch_name " +
                        "FROM car_branch AS cb " +
                        "LEFT JOIN branch AS b ON b.branch_id = cb.branch_id " +
                        "WHERE cb.car_id = " + carId + ";", Branch.GetBranchIdAndNameRowMapper());
    }

    @Override
    public String getBranchNameById(Integer branch_id) throws DataAccessException {
        String SQL =  String.format("SELECT branch_name FROM branch WHERE branch_id = '%s'",  branch_id);
        System.out.println(SQL);
        return this.template.queryForObject(SQL, PrimitiveRowMappers.GetStringRowMapper());
    }

    @Override
    public void IncreaseSpending(Integer branchId, Double price) {
        this.template.update("UPDATE branch SET expense = expense + " + price + " WHERE  branch_id = " + branchId + "; ");
    }

    @Override
    public BranchWithBudget GetBranchById(Integer id) {
        return this.template.queryForObject(
                "SELECT  b.branch_name, b.street_name, b.street_no, b.city, b.country, b.total_budget, b.expense FROM branch AS b WHERE b.branch_id = " + id + ";",
                BranchWithBudget.GetRowMapper()
        );
    }
}
