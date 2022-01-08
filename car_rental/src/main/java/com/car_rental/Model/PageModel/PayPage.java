package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.CompositeModel.ReservationWithCar;
import com.car_rental.Model.FormModel.PayForm;
import com.car_rental.Model.FormModel.ReturnCarForm;
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
public class PayPage extends Page {

    private List<ReservationWithCar> reservationWithCars;

    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("reservationCars", reservationWithCars);
        model.addAttribute("payForm", PayForm.builder().build());
    }
}
