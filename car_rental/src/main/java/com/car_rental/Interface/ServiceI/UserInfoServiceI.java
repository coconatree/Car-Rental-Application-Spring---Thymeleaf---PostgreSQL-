package com.car_rental.Interface.ServiceI;

import com.car_rental.Logic.ServiceLogic.FormServiceI;
import com.car_rental.Logic.ServiceLogic.PageServiceI;
import com.car_rental.Model.FormModel.BalanceAmountForm;

public interface UserInfoServiceI extends PageServiceI, FormServiceI<BalanceAmountForm, Integer> {
}
