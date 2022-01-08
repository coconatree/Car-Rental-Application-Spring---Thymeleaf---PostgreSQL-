package com.car_rental.Interface.RepositoryI;

import com.car_rental.Logic.RepositoryLogic.Insert;
import com.car_rental.Logic.RepositoryLogic.Select;
import com.car_rental.Model.CompositeModel.CarAndReviewAmount;
import com.car_rental.Model.DatabaseModel.Car;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepoI extends Insert<Car>, Select<Car> {
    List<String> SelectAllModel()        throws DataAccessException;
    List<String> SelectAllBrand()        throws DataAccessException;
    List<String> SelectAllGearTypes() throws DataAccessException;

    List<CarAndReviewAmount> SelectFiltered(
            List<String> models,
            List<String> brands,
            List<String> gearTypes
    );

    List<CarAndReviewAmount> SelectFilteredWithBranchId(
            List<String> models,
            List<String> brands,
            List<String> gearTypes,
            Integer branchId
    );
}
