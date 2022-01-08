package com.car_rental.Interface.ServiceI;

import com.car_rental.Logic.ServiceLogic.FormServiceI;
import com.car_rental.Logic.ServiceLogic.RedirectedServiceI;
import com.car_rental.Model.FormModel.CustomerShareRideForm;

public interface CustomerShareRideServiceI extends RedirectedServiceI<Integer>, FormServiceI<CustomerShareRideForm, Integer> {
}
