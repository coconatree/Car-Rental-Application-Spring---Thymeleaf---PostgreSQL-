package com.car_rental.Model.DatabaseModel;


import com.car_rental.Model.FormModel.CustomerShareRideForm;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class CustomerShareRide {
    private Integer rideId;
    private Integer passengerId;
    private Integer startBranch;

    public static RowMapper<CustomerShareRide> GetRowMapper(){
        return new ShareRideRowMapper();
    }

    private static class ShareRideRowMapper implements RowMapper<CustomerShareRide>{
        @Nullable
        @Override
        public CustomerShareRide mapRow(ResultSet rs, int rowNum) throws SQLException{
            return CustomerShareRide.builder()
                    .rideId(rs.getInt("ride_id"))
                    .passengerId(rs.getInt("passenger_id"))
                    .startBranch(rs.getInt("start_branch"))
                    .build();
        }
    }

    public static CustomerShareRide from(CustomerShareRideForm form, Integer passengerId){
        return CustomerShareRide.builder()
                .rideId(form.getRideId())
                .passengerId(passengerId)
                .startBranch(form.getStartBranch())
                .build();
    }
}
