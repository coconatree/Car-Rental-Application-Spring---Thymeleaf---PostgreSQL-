package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Impl.ServiceImpl.EmployeeHireServiceImpl;
import com.car_rental.Interface.ControllerI.EmployeeHireControllerI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.EmployeeHireForm;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("manager/hire")
public class EmployeeHireControllerImpl extends BaseController implements EmployeeHireControllerI {
    private final EmployeeHireServiceImpl service;

    @Autowired
    public EmployeeHireControllerImpl(EmployeeHireServiceImpl service) {
        this.service = service;
    }


    @Override
    @GetMapping("")
    public ModelAndView Redirected(HttpServletRequest request, ModelMap modelMap, HttpSession session) {
        Optional<AuthUser> user = ExtractFromSessionAuthUser(session);

        if (user.isEmpty()){
            return this.SendError("/manager",modelMap);
        }

        Optional<Page> page = service.GetPage(user.get().getId());

        if (page.isEmpty()){
            return this.SendError("/manager", modelMap);
        }

        page.get().setUser(user.get());
        page.get().Link(modelMap);
        return new ModelAndView("pages/hire_employee", modelMap);
    }

    @Override
    @PostMapping("")
    public ModelAndView Form(HttpServletRequest request, ModelMap modelMap, EmployeeHireForm formData, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors())
            this.SendError("/manager", modelMap);

        ErrorWithMessage formError = formData.ValidateForm();

        if (formError.isError())
            this.SendError("/manager", modelMap);

        Optional<AuthUser> user = ExtractFromSessionAuthUser(session);

        ErrorWithMessage serviceError = this.service.ProcessForm(formData, user.get().getId());

        if (serviceError.isError())
            this.SendError(serviceError.getMessage(), "/manager", modelMap);

        return new ModelAndView("redirect:/manager", modelMap);
    }
}
