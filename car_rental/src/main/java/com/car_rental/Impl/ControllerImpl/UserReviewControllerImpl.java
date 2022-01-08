package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Configuration.AuthenticationFacadeI;
import com.car_rental.Configuration.AuthenticationFacadeImpl;
import com.car_rental.Configuration.Utility;
import com.car_rental.Impl.ServiceImpl.UserReviewServiceImpl;
import com.car_rental.Interface.ControllerI.UserReviewControllerI;
import com.car_rental.Interface.ServiceI.UserReviewServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.UserReviewForm;
import jdk.jshell.execution.Util;
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
public class UserReviewControllerImpl extends BaseController implements UserReviewControllerI {

    private final AuthenticationFacadeI authenticationFacade;
    private final UserReviewServiceI userReviewService;

    @Autowired
    public UserReviewControllerImpl(
            AuthenticationFacadeImpl authenticationFacade,
            UserReviewServiceImpl userReviewService
    )
    {
        this.authenticationFacade = authenticationFacade;
        this.userReviewService = userReviewService;
    }

    @Override
    @GetMapping("/review")
    public ModelAndView Redirected(HttpServletRequest request, ModelMap modelMap, HttpSession session) {

        Optional<Integer> resId = Utility.extractParameterAsInteger(request, "resId");

        if (resId.isEmpty()) {
            return this.SendError("This page requires a reservation id", "/", modelMap);
        }

        Optional<AuthUser> user = this.ExtractFromSessionAuthUser(session);

        if(user.isEmpty()) {
            return new ModelAndView("redirect:/login", new ModelMap());
        }

        Optional<Page> page = this.userReviewService.GetPage(resId.get());

        if (page.isEmpty()) {
            return this.SendError("/", modelMap);
        }

        modelMap.addAttribute("rID", resId.get());

        page.get().setUser(user.get());
        page.get().Link(modelMap);

        return new ModelAndView("pages/user_review", modelMap);
    }

    @Override
    @PostMapping("/review")
    public ModelAndView Form(HttpServletRequest request, ModelMap modelMap, UserReviewForm formData, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return this.SendError("Error binding the form", String.format("/customer/?resId=%s", formData.getReservationId()), modelMap);
        }

        ErrorWithMessage formError = formData.ValidateForm();

        if (formError.isError()) {
            return this.SendError(
                    formError.getMessage(),
                    String.format("/customer/car/reserve?carId=%s", formData.getReservationId()),
                    modelMap
            );
        }

        Optional<Integer> userId = this.ExtractFromSessionInteger(session, "userId");

        if (userId.isEmpty()) {
            return new ModelAndView("redirect:/login", new ModelMap());
        }

        System.out.println("FORM " + formData);
        ErrorWithMessage serviceError = this.userReviewService.ProcessForm(formData, userId.get());

        if (serviceError.isError()) {
            return this.SendError(
                    serviceError.getMessage(),
                    String.format("/customer/review?resId=%s", formData.getReservationId()),
                    modelMap
            );}

        return new ModelAndView("redirect:/customer/reservations", modelMap);
    }


}
