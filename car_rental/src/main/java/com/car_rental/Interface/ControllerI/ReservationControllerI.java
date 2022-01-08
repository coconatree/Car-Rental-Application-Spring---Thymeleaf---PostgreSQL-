package com.car_rental.Interface.ControllerI;

import com.car_rental.Logic.ControllerLogic.FormControllerI;
import com.car_rental.Logic.ControllerLogic.RedirectControllerI;
import com.car_rental.Model.FormModel.ReservationForm;

public interface ReservationControllerI extends
        RedirectControllerI,
        FormControllerI<ReservationForm>
{}
