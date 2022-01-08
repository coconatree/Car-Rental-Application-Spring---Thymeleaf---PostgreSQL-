package com.car_rental.Interface.RepositoryI;

import com.car_rental.Model.CompositeModel.BranchIdAndName;
import com.car_rental.Model.CompositeModel.BranchWithBudget;
import com.car_rental.Model.DatabaseModel.Branch;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepoI {
    List<BranchIdAndName> SelectAllIdAndName() throws DataAccessException;
    Branch SelectBranchWhereCarIsOn(Integer carId);

    String getBranchNameById(Integer branch_id) throws DataAccessException;

    void IncreaseSpending(Integer managerId, Double price);

    BranchWithBudget GetBranchById(Integer id);
}
