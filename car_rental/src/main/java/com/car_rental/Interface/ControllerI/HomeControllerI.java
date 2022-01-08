package com.car_rental.Interface.ControllerI;

import com.car_rental.Logic.ControllerLogic.FormControllerI;
import com.car_rental.Logic.ControllerLogic.PageControllerI;
import com.car_rental.Model.FormModel.BranchSelectionForm;

public interface HomeControllerI extends
        PageControllerI,
        FormControllerI<BranchSelectionForm> {
}
