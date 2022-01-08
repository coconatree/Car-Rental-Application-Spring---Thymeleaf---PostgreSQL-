package com.car_rental.Model.FormModel;

import com.car_rental.Logic.ModelLogic.Form;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter @Setter @Builder @ToString @Component @EqualsAndHashCode
public class DamageReportForm implements Form {
    private String message;
    private Double cost;

    @Override
    public ErrorWithMessage ValidateForm() {
        return ErrorWithMessage.builder().error(false).build();
    }
}
