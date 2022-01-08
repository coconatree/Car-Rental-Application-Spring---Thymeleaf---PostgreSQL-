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
public class BranchIdAndName {
    private Integer id;
    private String  name;

    public static RowMapper<BranchIdAndName> GetRowMapper() {
        return new BranchIdAndNameRowMapper();
    }

    private static class BranchIdAndNameRowMapper implements RowMapper<BranchIdAndName> {
        @Nullable
        @Override
        public BranchIdAndName mapRow(ResultSet rs, int rowNum) throws SQLException {
            return BranchIdAndName.builder()
                    .id(rs.getInt("branch_id"))
                    .name(rs.getString("branch_name"))
                    .build();
        }
    }
}
