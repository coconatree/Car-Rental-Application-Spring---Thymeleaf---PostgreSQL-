package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Configuration.AuthenticationFacadeI;
import com.car_rental.Interface.ControllerI.PayControllerI;
import com.car_rental.Interface.ServiceI.PayServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.PayForm;
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
@RequestMapping("/customer")
public class PayControllerImpl extends BaseController implements PayControllerI {

    private AuthenticationFacadeI authenticationFacade;
    private PayServiceI service;

    @Autowired
    public PayControllerImpl(AuthenticationFacadeI authenticationFacade, PayServiceI service) {
        this.authenticationFacade = authenticationFacade;
        this.service = service;
    }

    @Override
    @PostMapping("/pay")
    public ModelAndView Form(HttpServletRequest request, ModelMap modelMap, PayForm formData, BindingResult bindingResult, HttpSession session) {
        Optional<AuthUser> user = ExtractFromSessionAuthUser(session);

        ErrorWithMessage formAndServiceError = this.ValidateAndProcessForm(this.service, formData, user.get(), bindingResult);

        if (formAndServiceError.isError()) {
            return this.SendError(formAndServiceError.getMessage(), "/customer/pay", modelMap);
        }

        modelMap.addAttribute("message", "Paid successfully, you can review your experience from your profile");
        modelMap.addAttribute("afterURL", "/customer/reservations");
        return new ModelAndView("redirect:/success", modelMap);
    }

    @Override
    @GetMapping("/pay")
    public ModelAndView Page(HttpServletRequest request, ModelMap modelMap, HttpSession session) {
        Optional<Page> page = this.service.GetPage(this.authenticationFacade.getAuthenticatedUser().get());
        Optional<AuthUser> user = ExtractFromSessionAuthUser(session);
        user.ifPresent(authUser -> page.get().setUser(authUser));

        if (page.isEmpty()) {
            return SendError( "/", modelMap);
        }

        page.get().Link(modelMap);


        return new ModelAndView("pages/pay", modelMap);
    }
}
