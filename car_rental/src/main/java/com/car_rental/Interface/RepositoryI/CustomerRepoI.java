package com.car_rental.Interface.RepositoryI;

import com.car_rental.Logic.RepositoryLogic.CRUD;
import com.car_rental.Model.DatabaseModel.Customer;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface CustomerRepoI extends CRUD<Customer> {

    void addBalance(Integer id, Double add) throws SQLException, DataAccessException;
    void makePayment(Integer id, Double totalCost) throws SQLException, DataAccessException;
    Customer selectCustomerById(Integer id);

}
