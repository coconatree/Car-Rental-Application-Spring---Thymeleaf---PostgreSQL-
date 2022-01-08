package com.car_rental.Interface.RepositoryI;

import com.car_rental.Logic.RepositoryLogic.Select;
import com.car_rental.Logic.RepositoryLogic.Update;
import com.car_rental.Model.DatabaseModel.Customer;
import com.car_rental.Model.DatabaseModel.User;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepoI extends Update<User> {

    User selectUserByEmail(String email) throws DataAccessException;
}
