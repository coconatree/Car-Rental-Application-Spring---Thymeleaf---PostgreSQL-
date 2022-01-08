package com.car_rental.Model.DatabaseModel;


import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class RequestCar {
    private Integer carId;
    private Integer requestId;
}

