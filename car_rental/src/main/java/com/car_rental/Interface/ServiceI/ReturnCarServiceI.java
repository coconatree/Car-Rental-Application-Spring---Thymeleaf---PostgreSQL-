package com.car_rental.Interface.ServiceI;

import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.FormServiceI;
import com.car_rental.Logic.ServiceLogic.PageServiceI;
import com.car_rental.Logic.ServiceLogic.RedirectedServiceI;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.ReservationForm;
import com.car_rental.Model.FormModel.ReturnCarForm;

public interface ReturnCarServiceI extends
        RedirectedServiceI<AuthUser>,
        FormServiceI<ReturnCarForm, AuthUser> {

}
