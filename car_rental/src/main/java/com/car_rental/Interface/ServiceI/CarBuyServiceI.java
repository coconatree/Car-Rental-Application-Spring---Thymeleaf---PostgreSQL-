package com.car_rental.Interface.ServiceI;

import com.car_rental.Logic.ServiceLogic.FormServiceI;
import com.car_rental.Model.FormModel.CarBuyForm;
import com.car_rental.Logic.ServiceLogic.PageServiceI;

public interface CarBuyServiceI extends PageServiceI, FormServiceI<CarBuyForm, Integer> {
}
