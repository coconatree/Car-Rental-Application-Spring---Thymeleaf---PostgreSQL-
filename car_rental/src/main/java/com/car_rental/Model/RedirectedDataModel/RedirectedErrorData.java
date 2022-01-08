package com.car_rental.Model.RedirectedDataModel;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class RedirectedErrorData {
    private String errorMessage;
    private String afterwardsPath;
}
