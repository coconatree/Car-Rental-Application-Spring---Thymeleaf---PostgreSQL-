package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Configuration.Utility;
import com.car_rental.Impl.ServiceImpl.ReservationServiceImpl;
import com.car_rental.Interface.ControllerI.ReservationControllerI;
import com.car_rental.Interface.ServiceI.ReservationServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.CompositeModel.CarIdWithBranchName;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.ReservationForm;
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
@RequestMapping("/customer/car")
public class ReservationControllerImpl extends BaseController implements ReservationControllerI {

    private final ReservationServiceI service;

    @Autowired
    public ReservationControllerImpl(ReservationServiceImpl service) {
        this.service = service;
    }

    @Override
    @GetMapping("/reserve")
    public ModelAndView Redirected(HttpServletRequest request, ModelMap modelMap, HttpSession session) {

        Optional<Integer> carId = Utility.extractParameterAsInteger(request, "carId");

        if (carId.isEmpty()) {
            return this.SendError("This page requires a car id", "/", modelMap);
        }

        Optional<AuthUser> user = this.ExtractFromSessionAuthUser(session);

        if(user.isEmpty()) {
            return new ModelAndView("redirect:/login", new ModelMap());
        }

        Optional<Page> page = this.service.GetPage(carId.get());

        if (page.isEmpty()) {
            return  this.SendError("/", modelMap);
        }

        page.get().setUser(user.get());
        page.get().Link(modelMap);

        return new ModelAndView("pages/reservation", modelMap);
    }

    @Override
    @PostMapping("/reserve")
    public ModelAndView Form(HttpServletRequest request, ModelMap modelMap, ReservationForm formData, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return this.SendError("Error binding the form", String.format("/customer/car/reserve?carId=%s", formData.getCarId()), modelMap);
        }

        ErrorWithMessage formError = formData.ValidateForm();

        if (formError.isError()) {
            return this.SendError(
                    formError.getMessage(),
                    String.format("/customer/car/reserve?carId=%s", formData.getCarId()),
                    modelMap
            );
        }

        Optional<Integer> userId = this.ExtractFromSessionInteger(session, "userId");

        if (userId.isEmpty()) {
            return new ModelAndView("redirect:/login", new ModelMap());
        }

        ErrorWithMessage serviceError = this.service.ProcessForm(formData, userId.get());

        if (serviceError.isError()) {
            return this.SendError(
                    serviceError.getMessage(),
                    String.format("/customer/car/reserve?carId=%s", formData.getCarId()),
                    modelMap
            );}

        return new ModelAndView("redirect:/", modelMap);
    }
}
