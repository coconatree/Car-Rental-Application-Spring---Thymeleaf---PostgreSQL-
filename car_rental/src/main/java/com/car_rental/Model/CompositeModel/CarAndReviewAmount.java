package com.car_rental.Model.CompositeModel;

import com.car_rental.Model.DatabaseModel.Car;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class CarAndReviewAmount {
    private Car car;
    private int  amountOfReviews;
}
