package com.car_rental.Interface.ControllerI;

import com.car_rental.Logic.ControllerLogic.FormControllerI;
import com.car_rental.Logic.ControllerLogic.RedirectControllerI;
import com.car_rental.Model.FormModel.CustomerShareRideForm;

public interface CustomerShareRideControllerI extends RedirectControllerI, FormControllerI<CustomerShareRideForm> {
}
