package com.car_rental.Logic.RepositoryLogic;

import org.springframework.dao.DataAccessException;

import java.sql.SQLException;

public interface Update<T> {
    T Update(Integer id, T data) throws DataAccessException, SQLException;
}
