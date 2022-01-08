package com.car_rental.Model.DatabaseModel;

import lombok.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class CarBranch {
    private Integer carId;
    private Integer branchId;

    public static RowMapper<CarBranch> GetRowMapper() {
        return new CarBranchRowMapper();
    }

    private static class CarBranchRowMapper implements RowMapper<CarBranch> {
        @Nullable
        @Override
        public CarBranch mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CarBranch.builder()
                    .carId(rs.getInt("car_id"))
                    .branchId(rs.getInt("branch_id"))
                    .build();
        }
    }
}
