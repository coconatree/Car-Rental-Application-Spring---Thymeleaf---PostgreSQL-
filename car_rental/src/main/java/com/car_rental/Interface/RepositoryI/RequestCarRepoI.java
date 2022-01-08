package com.car_rental.Interface.RepositoryI;

import com.car_rental.Logic.RepositoryLogic.Insert;
import com.car_rental.Model.DatabaseModel.RequestCar;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestCarRepoI extends Insert<RequestCar> {
    boolean CheckIfRequestExist(Integer carId);

    void AddRequestCar(Integer carId, Integer reqId);
}
