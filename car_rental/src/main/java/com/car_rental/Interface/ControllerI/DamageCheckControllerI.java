package com.car_rental.Interface.ControllerI;

import com.car_rental.Logic.ControllerLogic.PageControllerI;
import com.car_rental.Model.FormModel.DamageReportForm;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface DamageCheckControllerI extends PageControllerI {

    @GetMapping("/employee/reservation/report")
    ModelAndView reservationReport(HttpServletRequest request, ModelMap modelMap, HttpSession session);

    @PostMapping("/employee/reservation/report/damaged")
    ModelAndView reservationReportDamage(HttpServletRequest request, ModelMap modelMap, HttpSession session, @ModelAttribute DamageReportForm report, BindingResult br);
}
