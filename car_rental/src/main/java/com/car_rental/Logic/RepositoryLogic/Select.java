package com.car_rental.Logic.RepositoryLogic;

import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.Optional;

public interface Select<T> {

    /**
     Generic Select Function

     This interface is used as a building block for the database repository interfaces.
     If a given implementation 'carRepositoryImpl' is going to implement a Select method.
     It's corresponding interface 'carRepositoryI' can extend this interface as 'Select<Car>'.

     @param id Takes the id as a parameter and if exist retrieves the corresponding row.
     @return Optional<T> If the query's result is not null it returns 'Optional.empty()'.
     If the query's result is not null it returns 'Optional.of(result)'.

     */

    T Select(Integer id) throws DataAccessException, SQLException;
}
