package com.car_rental.Interface.ServiceI;

import com.car_rental.Logic.ServiceLogic.PageServiceI;
import com.car_rental.Model.FormModel.BranchSelectionForm;
import com.car_rental.Logic.ServiceLogic.FormServiceI;

public interface HomeServiceI extends
        PageServiceI,
        FormServiceI<BranchSelectionForm, Integer> {
}
