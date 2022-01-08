package com.car_rental.Logic.ServiceLogic;


import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class ErrorWithMessage {
    @Builder.Default
    private final boolean error = true;
    @Builder.Default
    private final String message = "Default Error Message";
}
