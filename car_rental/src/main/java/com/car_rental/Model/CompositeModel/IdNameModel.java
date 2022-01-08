package com.car_rental.Model.CompositeModel;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class IdNameModel {
    private Integer id;
    private String   name;
}
