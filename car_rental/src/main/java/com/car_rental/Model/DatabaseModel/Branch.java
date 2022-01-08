package com.car_rental.Model.DatabaseModel;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Table
@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class Branch {
    private Integer id;
    private String branchName;

    public static RowMapper<Branch> GetBranchIdAndNameRowMapper() {
        return new BranchIdAndNameRowMapper();
    }

    private static class BranchIdAndNameRowMapper implements RowMapper<Branch> {
        @Override
        public Branch mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Branch.builder()
                    .id(rs.getInt("branch_id"))
                    .branchName(rs.getString("branch_name"))
                    .build();
        }
    }
}
