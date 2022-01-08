package com.car_rental.Model.DatabaseModel;

import com.car_rental.Model.FormModel.ChangePasswordForm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@ToString
@Component
@SuperBuilder
@EqualsAndHashCode
public class User {

    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String email;
    private String role;

    public static User from(ChangePasswordForm form) {
        return User.builder()
                .id(0)
                .firstName("")
                .middleName("")
                .lastName("")
                .password(form.getPassword1())
                .email("")
                .role("")
                .build();
    }


    public static RowMapper<User> GetRowMapper() {
        return new UserRowMapper();
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Nullable
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getInt("user_id"))
                    .firstName(rs.getString("first_name"))
                    .middleName(rs.getString(  "middle_name"))
                    .lastName(rs.getString("surname"))
                    .password(rs.getString("password"))
                    .email(rs.getString("email"))
                    .role(rs.getString("role"))
                    .build();
        }
    }
}
