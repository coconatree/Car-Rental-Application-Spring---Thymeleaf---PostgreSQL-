package com.car_rental.Model.DatabaseModel;

import com.car_rental.Model.FormModel.CustomerRegisterForm;
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
public class Customer extends User {
    private Integer drivingLicenseId;
    private Double point;

    private final static double DEFAULT_CUSTOMER_POINT = 0.0;

   public static RowMapper<Customer> GetRowMapperCustomer() {
        return new CustomerRowMapper();
    }

    private static class CustomerRowMapper implements RowMapper<Customer> {

        @Nullable
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Customer.builder()
                    .id(rs.getInt("user_id"))
                    .firstName(rs.getString("first_name"))
                    .middleName(rs.getString("middle_name"))
                    .lastName(rs.getString("surname"))
                    .password(rs.getString("password"))
                    .email(rs.getString("email"))
                    .drivingLicenseId(rs.getInt("driving_license_id"))
                    .point(rs.getDouble("point"))
                    .build();
        }
    }

    public static Customer from(CustomerRegisterForm form) {
        return Customer.builder()
                .firstName(form.getFirstName())
                .middleName(form.getMiddleName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .password(form.getPassword())
                .drivingLicenseId(form.getDrivingLicenseId())
                .point(DEFAULT_CUSTOMER_POINT)
                .build();
    }
}
