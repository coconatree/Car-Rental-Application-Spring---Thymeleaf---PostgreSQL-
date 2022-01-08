package com.car_rental.Interface.ServiceI;

import com.car_rental.Logic.ServiceLogic.FormServiceI;
import com.car_rental.Logic.ServiceLogic.PageServiceI;
import com.car_rental.Logic.ServiceLogic.RedirectedServiceI;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.ChangePasswordForm;

public interface ChangePasswordServiceI extends PageServiceI, FormServiceI<ChangePasswordForm, AuthUser> {
}
