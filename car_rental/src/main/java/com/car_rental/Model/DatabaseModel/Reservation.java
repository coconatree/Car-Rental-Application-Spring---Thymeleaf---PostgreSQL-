package com.car_rental.Model.DatabaseModel;

import com.car_rental.Model.CompositeModel.ReservationWithCar;
import com.car_rental.Model.FormModel.ReservationForm;
import com.car_rental.Model.ReservationStatus;
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
public class Reservation {
    private Integer reservationId;
    private Date     startingDate;
    private Date     endingDate;
    private Double totalCost;
    private Integer status;
    private Double penalty;
    private Integer customerId;
    private Integer departFrom;
    private Integer arriveAt;
    private String   statusStr;
    private Boolean hasReview;

    public static Reservation from(ReservationForm form, double totalCost, int status, int customerId) {
        return Reservation.builder()
                .startingDate(form.getStartingDate())
                .endingDate(form.getEndingDate())
                .totalCost(totalCost)
                .status(status)
                .customerId(customerId)
                .departFrom(form.getDepartFrom())
                .build();
    }

    public static RowMapper<Reservation> GetRowMapper() {
        return new Reservation.ResRowMapper();
    }

    private static class ResRowMapper implements RowMapper<Reservation> {
        @Nullable
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Reservation.builder()
                    .reservationId(rs.getInt("reservation_id"))
                    .startingDate(rs.getDate("starting_date"))
                    .endingDate(rs.getDate("ending_date"))
                    .totalCost(rs.getDouble("total_cost"))
                    .status(rs.getInt("status"))
                    .customerId(rs.getInt("customer_id"))
                    .departFrom(rs.getInt("depart_from"))
                    .arriveAt(rs.getInt("arrive_at"))
                    .build();
        }
    }

    public static RowMapper<ReservationWithCar> GetResWithCarMapper() {
        return new Reservation.ResWithCarRowMapper();
    }

    private static class ResWithCarRowMapper implements RowMapper<ReservationWithCar> {
        @Nullable
        @Override
        public ReservationWithCar mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ReservationWithCar.builder()
                    .reservation(Reservation.builder()
                            .reservationId(rs.getInt("reservation_id"))
                            .startingDate(rs.getDate("starting_date"))
                            .endingDate(rs.getDate("ending_date"))
                            .totalCost(rs.getDouble("total_cost"))
                            .penalty(rs.getDouble("penalty_cost"))
                            .status(rs.getInt("status"))
                            .customerId(rs.getInt("customer_id"))
                            .departFrom(rs.getInt("depart_from"))
                            .arriveAt(rs.getInt("arrive_at"))
                            .build())
                    .car(Car.builder()
                            .id(rs.getInt("car_id"))
                            .brand(rs.getString("brand"))
                            .model(rs.getString("model"))
                            .gearType(rs.getString("gear_type"))
                            .price(rs.getDouble("price"))
                            .managerInteger(rs.getInt("manager_id"))
                            .build())
                    .build();
        }
    }
}
