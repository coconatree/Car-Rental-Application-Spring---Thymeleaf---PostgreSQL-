package com.car_rental.Interface.ServiceI;

import com.car_rental.Logic.ServiceLogic.PageServiceI;
import com.car_rental.Model.FormModel.CustomerRegisterForm;
import com.car_rental.Logic.ServiceLogic.FormServiceI;

public interface CustomerRegisterServiceI extends PageServiceI, FormServiceI<CustomerRegisterForm, Integer> {}
