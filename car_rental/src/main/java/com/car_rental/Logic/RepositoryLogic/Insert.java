package com.car_rental.Logic.RepositoryLogic;

import org.springframework.dao.DataAccessException;

import java.sql.SQLException;

public interface Insert<T> {

    /**
     Takes a T typed data 'Insert<Car> is Car Insert(Car data)' and inserts it to the database.

     @param data Inserts the given data to the database.
     @return T returns the inserted data with its newly created id.

     */
    T Insert(T data) throws DataAccessException, SQLException;
}
