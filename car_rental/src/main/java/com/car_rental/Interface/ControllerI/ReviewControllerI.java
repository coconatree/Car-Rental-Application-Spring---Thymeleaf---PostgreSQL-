package com.car_rental.Interface.ControllerI;

import com.car_rental.Logic.ControllerLogic.FormControllerI;
import com.car_rental.Logic.ControllerLogic.RedirectControllerI;
import com.car_rental.Model.FormModel.ReviewForm;

public interface ReviewControllerI extends RedirectControllerI, FormControllerI<ReviewForm> {
}
