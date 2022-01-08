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
public class ChangePasswordForm implements Form {
    private String password1;
    private String password2;

    @Override
    public ErrorWithMessage ValidateForm() {
        if (this.password1 == null)
            return ErrorWithMessage.builder().error(true).message("New password can't be empty").build();

        if (this.password2 == null)
            return ErrorWithMessage.builder().error(true).message("You must confirm the password").build();

        if (!this.password2.equals(this.password1))
            return ErrorWithMessage.builder().error(true).message("Password must match!").build();

        return ErrorWithMessage.builder().error(false).build();
    }
}
