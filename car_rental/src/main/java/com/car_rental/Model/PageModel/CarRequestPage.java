package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.CompositeModel.BranchIdAndName;
import com.car_rental.Model.DatabaseModel.Branch;
import com.car_rental.Model.FormModel.CarRequestForm;
import lombok.*;
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
public class CarRequestPage extends Page {

    private Branch startingBranch;
    private List<BranchIdAndName> branches;

    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("startingBranch", this.startingBranch);
        model.addAttribute("branches", this.branches);

        model.addAttribute("startingBranchValue", this.startingBranch);
        model.addAttribute("request", CarRequestForm.builder().build());
    }
}
