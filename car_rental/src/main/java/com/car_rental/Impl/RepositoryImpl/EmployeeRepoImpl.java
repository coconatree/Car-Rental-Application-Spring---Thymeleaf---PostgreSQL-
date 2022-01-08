package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.EmployeeRepoI;
import com.car_rental.Model.DatabaseModel.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class EmployeeRepoImpl implements EmployeeRepoI {
    private final JdbcTemplate template;

    @Autowired
    public EmployeeRepoImpl(JdbcTemplate template) {
        this.template = template;
    }


    private static final String USER_INSERT_QUERY = "INSERT INTO users (first_name, middle_name, surname, password, email, role) VALUES (?, ? ,? , ?, ?, ?);";
    private static final String WORKER_INSERT_QUERY = "INSERT INTO worker (user_id, salary) VALUES (?, ?)";
    private static final String EMPLOYEE_INSERT_QUERY = "INSERT INTO employee (user_id, rating, manager_id) VALUES (?, ?, ?)";

    public Employee Insert(Employee data, Integer managerId) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(USER_INSERT_QUERY, new String[]{"user_id"});
            ps.setString(1, data.getFirstName());
            ps.setString(2, data.getMiddleName());
            ps.setString(3, data.getLastName());
            ps.setString(4, data.getPassword());
            ps.setString(5, data.getEmail());
            ps.setString(6, "EMPLOYEE");

            return ps;
        }, keyHolder);

        Integer insertUserId = (Integer) keyHolder.getKey();
        this.template.update(WORKER_INSERT_QUERY, insertUserId, data.getSalary());

        this.template.update(EMPLOYEE_INSERT_QUERY, insertUserId, 0, managerId);

        data.setId(insertUserId);

        return data;
    }

    public Employee selectEmployeeById(Integer id){
        return this.template.queryForObject(
                String.format("SELECT * FROM users AS u NATURAL JOIN employee AS e WHERE e.user_id = %d", id),
                Employee.GetRowMapperEmployee()
        );
    }

}
