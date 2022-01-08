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
public class CustomerRegisterForm implements Form {
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String email;
    private Integer drivingLicenseId;

    @Override
    public ErrorWithMessage ValidateForm() {

        if (this.firstName == null)
            return ErrorWithMessage.builder().error(true).message("First name can't be empty").build();

        if (this.middleName == null)
            return ErrorWithMessage.builder().error(true).message("Middle name can't be empty").build();

        if (this.lastName == null)
            return ErrorWithMessage.builder().error(true).message("Last name can't be empty").build();

        if (this.password == null)
            return ErrorWithMessage.builder().error(true).message("Password name can't be empty").build();

        if (this.email == null)
            return ErrorWithMessage.builder().error(true).message("Email name can't be empty").build();

        if (this.drivingLicenseId == null)
            return ErrorWithMessage.builder().error(true).message("Driving license can't be empty").build();

        return ErrorWithMessage.builder().error(false).build();
    }
}
