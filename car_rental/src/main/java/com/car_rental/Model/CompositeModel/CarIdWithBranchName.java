package com.car_rental.Model.CompositeModel;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class CarIdWithBranchName {
    private Integer carId;
    private Integer branchId;
    private String   branchName;
}
