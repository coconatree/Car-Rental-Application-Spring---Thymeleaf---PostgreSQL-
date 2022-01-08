package com.car_rental.Model.RedirectedDataModel;

import com.car_rental.Logic.ModelLogic.Form;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class SuccessData {
    private String message;
    private String  afterURL;
}
