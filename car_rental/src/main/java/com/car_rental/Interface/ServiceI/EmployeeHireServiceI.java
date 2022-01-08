package com.car_rental.Interface.ServiceI;

import com.car_rental.Logic.ControllerLogic.RedirectControllerI;
import com.car_rental.Logic.ServiceLogic.FormServiceI;
import com.car_rental.Logic.ServiceLogic.RedirectedServiceI;
import com.car_rental.Model.FormModel.EmployeeHireForm;

public interface EmployeeHireServiceI extends RedirectedServiceI<Integer>, FormServiceI<EmployeeHireForm, Integer> {}
