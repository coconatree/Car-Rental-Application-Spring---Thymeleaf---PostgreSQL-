package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Model.DatabaseModel.Customer;
import com.car_rental.Interface.RepositoryI.CustomerRepoI;
import com.car_rental.Model.DatabaseModel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class CustomerRepoImpl implements CustomerRepoI {

    private final static Logger LOGGER = Logger.getLogger(CustomerRepoImpl.class.toString());
    private final JdbcTemplate template;

    @Autowired
    public CustomerRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Customer Select(Integer id) throws DataAccessException, SQLException {
        return null;
    }

    private static final String USER_INSERTION_QUERY    = "INSERT INTO users (first_name, middle_name, surname, password, email, role) VALUES (?, ? ,? , ?, ?, ?);";
    private static final String CUSTOMER_INSERT_QUERY = "INSERT INTO customer (user_id, driving_license_id, point) VALUES (?, ?, ?);";

    @Override
    public Customer Insert(Customer data) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(USER_INSERTION_QUERY, new String[]{"user_id"});
            ps.setString(1, data.getFirstName());
            ps.setString(2, data.getMiddleName());
            ps.setString(3, data.getLastName());
            ps.setString(4, data.getPassword());
            ps.setString(5, data.getEmail());
            ps.setString(6, "CUSTOMER");
            return ps;
        }, keyHolder);

        Integer insertedUserId = (Integer) keyHolder.getKey();

        this.template.update(CUSTOMER_INSERT_QUERY, insertedUserId, data.getDrivingLicenseId(), data.getPoint());

        data.setId(insertedUserId);
        return data;
    }

    @Override
    public Customer Update(Integer id, Customer data) throws DataAccessException, SQLException {
        return null;
    }

    @Override
    public Customer Delete(Integer integer) throws DataAccessException, SQLException {
        return null;
    }

    public Customer selectCustomerById(Integer id) {
        return this.template.queryForObject(
                String.format("SELECT * FROM users AS u NATURAL JOIN customer AS c WHERE c.user_id = %d",  id),
                Customer.GetRowMapperCustomer()
        );
    }

    @Override
    public void addBalance(Integer id, Double add) {
        this.template.update("UPDATE customer SET point = point + ? WHERE user_id = ?", add, id);
    }

    @Override
    public void makePayment(Integer id, Double totalCost) {
        this.template.update("UPDATE customer SET point = point - ? WHERE user_id = ?", totalCost, id);
    }
}