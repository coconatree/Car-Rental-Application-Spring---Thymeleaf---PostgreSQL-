package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Impl.ServiceImpl.CustomerRegisterServiceImpl;
import com.car_rental.Interface.ControllerI.CustomerRegistrationControllerI;
import com.car_rental.Interface.ServiceI.CustomerRegisterServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.FormModel.CustomerRegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("")
public class CustomerRegisterControllerImpl extends BaseController implements CustomerRegistrationControllerI {

    private final CustomerRegisterServiceI service;

    public CustomerRegisterControllerImpl(CustomerRegisterServiceImpl service) {
        super();
        this.service = service;
    }

    @Override
    @GetMapping("/register")
    public ModelAndView Page(
            HttpServletRequest request,
            ModelMap modelMap,
            HttpSession session
    ) {
        Optional<Page> page = this.service.GetPage();
        if (page.isPresent()) {
            page.get().Link(modelMap);
            return new ModelAndView("pages/customer_registration", modelMap);
        }
        return this.SendError("", modelMap);
    }

    @Override
    @PostMapping("/register")
    public ModelAndView Form(
            HttpServletRequest request,
            ModelMap modelMap,
            CustomerRegisterForm formData,
            BindingResult bindingResult,
            HttpSession session
    ) {
        ErrorWithMessage formAndServiceError = this.ValidateAndProcessForm(this.service, formData, 0, bindingResult);

        if (formAndServiceError.isError()) {
            return this.SendError(formAndServiceError.getMessage(), "/register", modelMap);
        }
        return new ModelAndView("redirect:/login", modelMap);
    }
}
