package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Configuration.AuthenticationFacadeI;
import com.car_rental.Interface.ControllerI.ReturnCarControllerI;
import com.car_rental.Interface.ServiceI.ReturnCarServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.ReturnCarForm;
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
public class ReturnCarControllerImpl extends BaseController implements ReturnCarControllerI {

    private AuthenticationFacadeI authenticationFacade;
    private ReturnCarServiceI returnCarService;

    @Autowired
    public ReturnCarControllerImpl(AuthenticationFacadeI authenticationFacade, ReturnCarServiceI returnCarService) {
        this.authenticationFacade = authenticationFacade;
        this.returnCarService = returnCarService;
    }

    @Override
    @PostMapping("/return")
    public ModelAndView Form(HttpServletRequest request, ModelMap modelMap, ReturnCarForm formData, BindingResult bindingResult, HttpSession session) {
        Optional<AuthUser> user = ExtractFromSessionAuthUser(session);

        ErrorWithMessage formAndServiceError = this.ValidateAndProcessForm(this.returnCarService, formData, user.get(), bindingResult);

        if (formAndServiceError.isError()) {
            return this.SendError(formAndServiceError.getMessage(), "/customer/return", modelMap);
        }

        modelMap.addAttribute("message", "Car has returned successfully, you can pay after our crew check for damages.");
        modelMap.addAttribute("afterURL", "/customer/reservations");
        return new ModelAndView("redirect:/success", modelMap);
    }

    @Override
    @GetMapping("/return")
    public ModelAndView Page(HttpServletRequest request, ModelMap modelMap, HttpSession session) {

        Optional<Page> page = this.returnCarService.GetPage(this.authenticationFacade.getAuthenticatedUser().get());
        Optional<AuthUser> user = ExtractFromSessionAuthUser(session);
        user.ifPresent(authUser -> page.get().setUser(authUser));

        if (page.isEmpty()) {
            return SendError( "/", modelMap);
        }

        page.get().Link(modelMap);


        return new ModelAndView("pages/return_car", modelMap);
    }
}
