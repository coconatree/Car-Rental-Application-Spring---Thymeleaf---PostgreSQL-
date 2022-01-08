package com.car_rental.Interface.ServiceI;

import com.car_rental.Logic.ServiceLogic.FormServiceI;
import com.car_rental.Logic.ServiceLogic.RedirectedServiceI;
import com.car_rental.Model.CompositeModel.CarIdWithBranchName;
import com.car_rental.Model.FormModel.ReservationForm;

public interface ReservationServiceI extends RedirectedServiceI<Integer>, FormServiceI<ReservationForm, Integer> {}
