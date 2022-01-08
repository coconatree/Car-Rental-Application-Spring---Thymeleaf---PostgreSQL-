package com.car_rental.Model.CompositeModel;

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
public class BranchWithBudget {
    private String branchName;
    private String streetName;
    private String streetNo;
    private String city;
    private String country;
    private Integer totalBudget;
    private Integer expense;

    public static RowMapper<BranchWithBudget> GetRowMapper() {
        return new BranchWithBudgetRowMapper();
    }

    private static class BranchWithBudgetRowMapper implements RowMapper<BranchWithBudget> {
        @Nullable
        @Override
        public BranchWithBudget mapRow(ResultSet rs, int rowNum) throws SQLException {
            return BranchWithBudget.builder()
                    .branchName(rs.getString("branch_name"))
                    .streetName(rs.getString("street_name"))
                    .streetNo(rs.getString("street_no"))
                    .city(rs.getString("city"))
                    .country(rs.getString("country"))
                    .totalBudget(rs.getInt("total_budget"))
                    .expense(rs.getInt("expense"))
                    .build();
        }
    }
}
