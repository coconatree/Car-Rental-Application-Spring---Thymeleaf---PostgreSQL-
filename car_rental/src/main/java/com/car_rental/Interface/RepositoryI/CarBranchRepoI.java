package com.car_rental.Interface.RepositoryI;

import com.car_rental.Model.DatabaseModel.CarBranch;

public interface CarBranchRepoI {
    CarBranch Insert(Integer carId, Integer branchId);
    Integer Update(Integer carId, Integer branchId);

    boolean CheckIfCarInBranch(Integer branchId, Integer carId);
    boolean CheckIfCarAvailable(int carId);

}
