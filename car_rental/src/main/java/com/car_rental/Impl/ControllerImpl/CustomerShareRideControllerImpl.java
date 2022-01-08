package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Impl.ServiceImpl.CustomerShareRideServiceImpl;
import com.car_rental.Interface.ControllerI.CustomerShareRideControllerI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.CustomerShareRideForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping
public class CustomerShareRideControllerImpl extends BaseController implements CustomerShareRideControllerI {
    private final CustomerShareRideServiceImpl service;

    public CustomerShareRideControllerImpl(CustomerShareRideServiceImpl service) {
        this.service = service;
    }


    @Override
    @GetMapping("customer/share")
    public ModelAndView Redirected(HttpServletRequest request, ModelMap modelMap, HttpSession session) {
        Optional<AuthUser> user = ExtractFromSessionAuthUser(session);

        if (user.isEmpty()) {
            return new ModelAndView("redirect:/login", modelMap);
        }

        Optional<Page> page = service.GetPage(user.get().getId());

        if (page.isEmpty()){
            return this.SendError("/", modelMap);
        }

        user.ifPresent(authUser -> page.get().setUser(authUser));
        page.get().Link(modelMap);

        return new ModelAndView("pages/customer_share_ride", modelMap);
    }

    @Override
    @PostMapping("customer/share")
    public ModelAndView Form(HttpServletRequest request, ModelMap modelMap, CustomerShareRideForm formData, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()){
            this.SendError("/customer/share", modelMap);
        }

        ErrorWithMessage formError = formData.ValidateForm();

        if (formError.isError()){
            this.SendError("/customer/share", modelMap);
        }

        Optional<AuthUser> user = ExtractFromSessionAuthUser(session);

        if (user.isEmpty()){
            return new ModelAndView("redirect:/login", modelMap);
        }

        System.out.println("FORM = " + formData.toString());

        ErrorWithMessage err = this.service.ProcessForm(formData,user.get().getId());
        if (err.isError()){
            return this.SendError(err.getMessage(), "/customer/share", modelMap);
        }

        return new ModelAndView("redirect:/customer/share", modelMap);
    }
}
