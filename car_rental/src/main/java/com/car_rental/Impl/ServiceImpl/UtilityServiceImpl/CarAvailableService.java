package com.car_rental.Impl.ServiceImpl.UtilityServiceImpl;

import com.car_rental.Impl.RepositoryImpl.CarReservationRepoImpl;
import com.car_rental.Impl.RepositoryImpl.CarBranchRepoImpl;
import com.car_rental.Impl.RepositoryImpl.RequestCarRepoImpl;
import com.car_rental.Interface.RepositoryI.CarBranchRepoI;
import com.car_rental.Interface.RepositoryI.CarReservationRepoI;
import com.car_rental.Interface.RepositoryI.RequestCarRepoI;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarAvailableService {

    private final CarBranchRepoI carBranchRepo;

    @Autowired
    public CarAvailableService(
            CarBranchRepoImpl carBranchRepo
    )
    {
        this.carBranchRepo = carBranchRepo;
    }

    public ErrorWithMessage IsCarAvailable(int carId) {
        if (!this.carBranchRepo.CheckIfCarAvailable(carId)) {
            return ErrorWithMessage.builder().error(true).message("Car is not available").build();
        }
        return ErrorWithMessage.builder().error(false).build();
    }
}

