package com.car_rental.Model.CompositeModel;


import lombok.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class CustomerShareRideComposite {
    private Integer rideId;
    private Integer startBranchInt;
    private String startBranch;
    private double price;
    private Date startDate;
    private String brand;
    private String model;

    public static RowMapper<CustomerShareRideComposite> GetRowMapper(){
        return new CustomerShareRideCompositeRowMapper();
    }

    private static class CustomerShareRideCompositeRowMapper implements RowMapper<CustomerShareRideComposite>{

        @Nullable
        @Override
        public CustomerShareRideComposite mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CustomerShareRideComposite.builder()
                    .rideId(rs.getInt("ride_id"))
                    .startBranchInt(rs.getInt("start_branch_int"))
                    .startBranch(rs.getString("branch_name"))
                    .price(rs.getDouble("total_cost"))
                    .startDate(rs.getDate("starting_date"))
                    .brand(rs.getString("brand"))
                    .model(rs.getString("model"))
                    .build();
        }
    }

}
