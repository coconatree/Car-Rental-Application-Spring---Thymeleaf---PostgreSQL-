package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.CompositeModel.BranchIdAndName;
import com.car_rental.Model.CompositeModel.ReservationWithCar;
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
public class ReturnCarPage extends Page {

    private List<BranchIdAndName> branches;
    private List<ReservationWithCar> reservationWithCars;
    @Override
    protected void Initialize(ModelMap model) {

        model.addAttribute("allBranches", branches);
        model.addAttribute("reservationWithCars", reservationWithCars);
        model.addAttribute("returnForm", ReturnCarForm.builder().build());
    }
}
