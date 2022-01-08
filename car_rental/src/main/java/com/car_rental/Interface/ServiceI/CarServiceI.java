package com.car_rental.Interface.ServiceI;

import com.car_rental.Logic.ServiceLogic.FormServiceI;
import com.car_rental.Logic.ServiceLogic.RedirectedServiceI;
import com.car_rental.Model.FormModel.CarFilterForm;
import com.car_rental.Model.RedirectedDataModel.CarFilterData;

public interface CarServiceI extends RedirectedServiceI<CarFilterData>, FormServiceI<CarFilterForm, Integer> {}
