package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.CompositeModel.BranchIdAndName;
import com.car_rental.Model.DatabaseModel.Branch;
import com.car_rental.Model.FormModel.ReservationForm;
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
public class ReservationPage extends Page {
    private List<BranchIdAndName> branches;
    private Integer carId;
    private Branch branch;

    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("departureBranch", this.branch);
        model.addAttribute("reservationForm", ReservationForm.builder().build());
        model.addAttribute("branches", this.branches);
        model.addAttribute("carId", this.carId);
    }
}
