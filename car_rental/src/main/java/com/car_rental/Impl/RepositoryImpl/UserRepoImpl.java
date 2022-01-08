package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.UserRepoI;
import com.car_rental.Model.DatabaseModel.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepoImpl implements UserRepoI {

    private final JdbcTemplate jdbcTemplate;

    public UserRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User selectUserByEmail(String email) throws DataAccessException {
        return this.jdbcTemplate.queryForObject(
                String.format("SELECT * FROM users AS u WHERE u.email = '%s'",  email),
                User.GetRowMapper()
        );
    }

    @Override
    public User Update(Integer id, User data) throws DataAccessException, SQLException {
        this.jdbcTemplate.update("UPDATE users SET password = ? WHERE user_id = ?", data.getPassword(), id);
        return data;
    }
}
