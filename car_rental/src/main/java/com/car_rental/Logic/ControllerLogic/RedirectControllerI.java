package com.car_rental.Logic.ControllerLogic;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface RedirectControllerI {
    ModelAndView Redirected(HttpServletRequest request, ModelMap modelMap, HttpSession session);
}
