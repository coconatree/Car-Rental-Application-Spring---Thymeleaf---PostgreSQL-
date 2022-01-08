package com.car_rental.Interface.ControllerI;

import com.car_rental.Logic.ControllerLogic.PageControllerI;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface RequestCheckControllerI extends PageControllerI {
    @GetMapping("/employee/request/approve")
    ModelAndView requestApprove(HttpServletRequest request, ModelMap modelMap, HttpSession session);

    @GetMapping("/employee/request/decline")
    ModelAndView requestDecline(HttpServletRequest request, ModelMap modelMap, HttpSession session);
}
