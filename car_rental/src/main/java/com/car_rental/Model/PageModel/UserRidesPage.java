package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.DatabaseModel.Reservation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.List;

@Getter
@Setter
@ToString
@Component
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserRidesPage extends Page {

    private List<Reservation> sharedReservations;

    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("sharedReservations", this.sharedReservations);
    }
}
