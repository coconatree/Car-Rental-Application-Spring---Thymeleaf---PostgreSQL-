package com.car_rental.Logic.RepositoryLogic;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface SelectAll<T> {
    List<T> SelectAll() throws DataAccessException;
}
