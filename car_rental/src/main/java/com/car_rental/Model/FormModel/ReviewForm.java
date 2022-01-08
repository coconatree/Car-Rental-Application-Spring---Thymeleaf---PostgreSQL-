package com.car_rental.Model.FormModel;

import com.car_rental.Logic.ModelLogic.Form;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;

public class ReviewForm implements Form {



    @Override
    public ErrorWithMessage ValidateForm() {
        return ErrorWithMessage.builder().error(false).build();
    }
}
