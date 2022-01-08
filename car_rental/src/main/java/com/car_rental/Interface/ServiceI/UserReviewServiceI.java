package com.car_rental.Interface.ServiceI;


import com.car_rental.Logic.ServiceLogic.FormServiceI;
import com.car_rental.Logic.ServiceLogic.RedirectedServiceI;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.UserReviewForm;

public interface UserReviewServiceI extends RedirectedServiceI<Integer>, FormServiceI<UserReviewForm, Integer> {
}
