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
public class SharedRide {
    private Integer rideId;
    private Integer reservationId;
    private Double sharedCost;

    public static RowMapper<SharedRide> GetRowMapper() {
        return new SharedRideRowMapper();
    }

    private static class SharedRideRowMapper implements RowMapper<SharedRide> {
        @Nullable
        @Override
        public SharedRide mapRow(ResultSet rs, int rowNum) throws SQLException {
            return SharedRide.builder()
                    .rideId(rs.getInt("ride_id"))
                    .reservationId(rs.getInt("reservation_id"))
                    .sharedCost(rs.getDouble("shared_cost"))
                    .build();
        }
    }
}
