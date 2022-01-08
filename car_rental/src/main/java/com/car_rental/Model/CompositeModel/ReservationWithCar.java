package com.car_rental.Model.CompositeModel;

import com.car_rental.Model.DatabaseModel.Car;
import com.car_rental.Model.DatabaseModel.Reservation;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class ReservationWithCar {
    private Reservation reservation;
    private Car car;
}
