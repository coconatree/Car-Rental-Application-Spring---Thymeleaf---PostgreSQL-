package com.car_rental.Interface.ServiceI;

import com.car_rental.Logic.ServiceLogic.FormServiceI;
import com.car_rental.Logic.ServiceLogic.PageServiceI;
import com.car_rental.Model.FormModel.AuthenticationForm;
import org.springframework.stereotype.Service;

@Service
public interface LoginServiceI extends PageServiceI, FormServiceI<AuthenticationForm, Integer> {
}
