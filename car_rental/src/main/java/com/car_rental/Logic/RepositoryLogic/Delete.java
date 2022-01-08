package com.car_rental.Logic.RepositoryLogic;

import org.springframework.dao.DataAccessException;

import java.sql.SQLException;

public interface Delete<T> {
    T Delete(Integer integer) throws DataAccessException, SQLException;
}
