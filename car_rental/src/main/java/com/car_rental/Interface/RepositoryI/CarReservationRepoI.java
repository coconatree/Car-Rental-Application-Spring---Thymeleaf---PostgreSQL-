package com.car_rental.Interface.RepositoryI;

import com.car_rental.Logic.RepositoryLogic.Insert;
import com.car_rental.Model.DatabaseModel.CarReservation;
import org.springframework.stereotype.Repository;

@Repository
public interface CarReservationRepoI extends Insert<CarReservation> {
    boolean CheckIfReservationExist(Integer carId);
}
