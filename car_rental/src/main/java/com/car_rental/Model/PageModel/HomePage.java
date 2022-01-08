package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.CompositeModel.BranchIdAndName;
import com.car_rental.Model.FormModel.BranchSelectionForm;
import lombok.experimental.SuperBuilder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.List;
@SuperBuilder
public class HomePage extends Page {
    private List<BranchIdAndName> branches;

    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("branches", this.branches);
        model.addAttribute("branchForm", BranchSelectionForm.builder().build());
    }
}
