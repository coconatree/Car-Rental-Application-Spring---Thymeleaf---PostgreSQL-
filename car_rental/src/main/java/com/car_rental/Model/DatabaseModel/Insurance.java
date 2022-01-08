package com.car_rental.Model.DatabaseModel;

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
public class Insurance {
    private Integer insuranceId;
    private Integer reservationId;
    private Date startingDate;
    private Date endingDate;
    private Double insuranceFee;

    public static RowMapper<Insurance> GetRowMapper() {
        return new InsuranceRowMapper();
    }

    private static class InsuranceRowMapper implements RowMapper<Insurance> {
        @Nullable
        @Override
        public Insurance mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Insurance.builder()
                    .insuranceId(rs.getInt("insurance_id"))
                    .reservationId(rs.getInt("reservation_id"))
                    .startingDate(rs.getDate("starting_date"))
                    .endingDate(rs.getDate("ending_date"))
                    .insuranceFee(rs.getDouble("insurance_fee"))
                    .build();
        }
    }
}
