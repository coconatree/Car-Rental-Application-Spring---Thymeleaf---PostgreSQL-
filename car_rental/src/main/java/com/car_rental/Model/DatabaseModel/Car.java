package com.car_rental.Model.DatabaseModel;

import com.car_rental.Model.CompositeModel.CarAndReviewAmount;
import com.car_rental.Model.FormModel.CarBuyForm;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
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
public class Car {
    @Id
    private Integer id;
    private String brand;
    private String model;
    private String gearType;
    private Double price;
    private Double pricePerDay;
    private Integer managerInteger;

    public static Car from(CarBuyForm form, Integer managerInteger) {
        return Car.builder()
                .brand(form.getBrand())
                .model(form.getModel())
                .gearType(form.getGearType())
                .price(form.getPrice())
                .pricePerDay(form.getPricePerDay())
                .managerInteger(managerInteger)
                .build();
    }

    public static RowMapper<Car> GetMapper() {
        return new CarRowMapper();
    }

    private static class CarRowMapper implements RowMapper<Car> {
        @Nullable
        @Override
        public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Car.builder()
                    .id(rs.getInt("car_id"))
                    .brand(rs.getString("brand"))
                    .model(rs.getString("model"))
                    .gearType(rs.getString("gear_type"))
                    .price(rs.getDouble("price"))
                    .pricePerDay(rs.getDouble("price_per_day"))
                    .managerInteger(rs.getInt("manager_id"))
                    .build();
        }
    }

    public static RowMapper<CarAndReviewAmount> GetCarWithReviewMapper() {
        return new CarWithReviewRowMapper();
    }

    private static class CarWithReviewRowMapper implements RowMapper<CarAndReviewAmount> {
        @Nullable
        @Override
        public CarAndReviewAmount mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CarAndReviewAmount.builder()
                    .car(Car.builder()
                            .id(rs.getInt("car_id"))
                            .brand(rs.getString("brand"))
                            .model(rs.getString("model"))
                            .gearType(rs.getString("gear_type"))
                            .price(rs.getDouble("price"))
                            .managerInteger(rs.getInt("manager_id"))
                            .build())
                    .amountOfReviews(rs.getInt("amount_of_review"))
                    .build();
        }
    }
}
