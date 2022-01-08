package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.CompositeModel.BranchWithBudget;
import com.car_rental.Model.DatabaseModel.Branch;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Getter
@Setter
@ToString
@Component
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class LandingPageManager extends Page {

    private String                    message;
    private BranchWithBudget branch;

    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("name", this.message);
        model.addAttribute("branchInfo", this.branch);
    }
}
