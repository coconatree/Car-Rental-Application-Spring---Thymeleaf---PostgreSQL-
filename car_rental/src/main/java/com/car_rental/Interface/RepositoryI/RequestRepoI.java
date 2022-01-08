package com.car_rental.Interface.RepositoryI;

import com.car_rental.Logic.RepositoryLogic.Insert;
import com.car_rental.Model.DatabaseModel.Request;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface RequestRepoI extends Insert<Request> {

    Request Insert(Request data) throws DataAccessException, SQLException;


    List<Request> getUnhandledRequests(Integer user_id) throws DataAccessException;
}
