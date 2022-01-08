package com.car_rental.Model.DatabaseModel;

import com.car_rental.Impl.RepositoryImpl.EmployeeRepoImpl;
import com.car_rental.Model.FormModel.EmployeeHireForm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;


@Getter
@Setter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Employee extends User{
    private Double salary;
    protected Employee(UserBuilder<?, ?> b) {
        super(b);
    }

    public static RowMapper<Employee> GetRowMapperEmployee(){
        return new EmployeeRowMapper();
    }

    private static class EmployeeRowMapper implements RowMapper<Employee>{

        @Nullable
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException{
            return Employee.builder()
                    .firstName(rs.getString("first_name"))
                    .middleName(rs.getString("middle_name"))
                    .lastName(rs.getString("surname"))
                    .password(rs.getString("password"))
                    .email(rs.getString("email"))
                    .salary(rs.getDouble("salary"))
                    .build();
        }
    }

    public static Employee from(EmployeeHireForm form){
        return Employee.builder()
                .firstName(form.getFirstName())
                .middleName(form.getMiddleName())
                .lastName(form.getSurname())
                .email(form.getEmail())
                .password(form.getPassword())
                .salary(form.getSalary())
                .build();
    }
}
