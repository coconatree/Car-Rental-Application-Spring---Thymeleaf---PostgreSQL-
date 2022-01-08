package com.car_rental.Model.FormModel;

import com.car_rental.Logic.ModelLogic.Form;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class CarRequestForm implements Form {

    @Nullable
    private Integer carId;

    private Integer startingBranch;
    private Integer endingBranch;

    @Override
    public ErrorWithMessage ValidateForm() {
        return ErrorWithMessage.builder().error(false).build();
    }
}
