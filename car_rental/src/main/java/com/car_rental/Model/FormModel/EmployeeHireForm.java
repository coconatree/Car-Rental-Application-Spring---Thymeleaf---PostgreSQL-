package com.car_rental.Model.FormModel;

import com.car_rental.Logic.ModelLogic.Form;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import lombok.*;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class EmployeeHireForm implements Form {
    private String firstName;
    private String middleName;
    private String surname;
    private String email;
    private String password;
    private Double salary;

    @Override
    public ErrorWithMessage ValidateForm() {
        return ErrorWithMessage.builder().error(false).build();
    }
}
