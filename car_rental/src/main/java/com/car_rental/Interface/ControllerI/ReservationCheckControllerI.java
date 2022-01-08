package com.car_rental.Interface.ControllerI;

import com.car_rental.Logic.ControllerLogic.PageControllerI;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ReservationCheckControllerI extends PageControllerI {
    @GetMapping("/employee/reservation/approve")
    ModelAndView reservationApprove(HttpServletRequest request, ModelMap modelMap, HttpSession session);

    @GetMapping("/employee/reservation/decline")
    ModelAndView reservationDecline(HttpServletRequest request, ModelMap modelMap, HttpSession session);
}
