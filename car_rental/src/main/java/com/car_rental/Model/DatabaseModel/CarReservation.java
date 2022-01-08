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
public class CarReservation {
    private int carId;
    private int reservationId;

    public static RowMapper<CarReservation> GetRowMapper() {
        return new CarReservationRowMapper();
    }

    private static class CarReservationRowMapper implements RowMapper<CarReservation> {
        @Nullable
        @Override
        public CarReservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CarReservation.builder()
                    .carId(rs.getInt("car_id"))
                    .reservationId(rs.getInt("reservation_id"))
                    .build();
        }
    }

}
