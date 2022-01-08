package com.car_rental.Model.CompositeModel;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class CheckBoxValueAndIsChecked {
    private String    value;
    private boolean isChecked;

    public CheckBoxValueAndIsChecked(String value, boolean isChecked) {
        this.value        = value;
        this.isChecked = isChecked;
    }
}
