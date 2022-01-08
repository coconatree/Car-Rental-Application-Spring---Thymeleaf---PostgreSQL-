package com.car_rental.Model.FormModel;

import com.car_rental.Logic.ModelLogic.Form;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class ReservationForm implements Form {

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startingDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endingDate;

    private Integer departFrom;

    private boolean hasInsurance;
    private boolean shared;

    private Integer carId;

    @Override
    public ErrorWithMessage ValidateForm() {

        if (startingDate == null || endingDate == null) {
            return ErrorWithMessage.builder().error(true).message("Starting date and ending date cant be empty").build();
        }

        if (startingDate.after(endingDate))
            return ErrorWithMessage.builder().error(true).message("Starting date can't be after the ending date").build();

        return ErrorWithMessage.builder().error(false).build();
    }
}
