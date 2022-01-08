package com.car_rental.Model.FormModel;

import com.car_rental.Logic.ModelLogic.Form;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class CarFilterForm implements Form {

    private List<String> models;
    private List<String> brands;
    private List<String> gears;

    private int preferredBranch;

    @Override
    public ErrorWithMessage ValidateForm() {
        return ErrorWithMessage.builder().error(false).build();
    }
}
