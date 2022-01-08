package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Impl.ServiceImpl.CarBuyServiceImpl;
import com.car_rental.Interface.ControllerI.CarBuyControllerI;
import com.car_rental.Interface.ServiceI.CarBuyServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.CarBuyForm;
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
@RequestMapping("/manager")
public class CarBuyControllerImpl extends BaseController implements CarBuyControllerI {

    private final CarBuyServiceI carBuyPageService;

    @Autowired
    public CarBuyControllerImpl(CarBuyServiceImpl carBuyPageService) {
        this.carBuyPageService = carBuyPageService;
    }

    @Override
    @GetMapping("/purchase")
    public ModelAndView Page(
            HttpServletRequest request,
            ModelMap modelMap,
            HttpSession session
    ) {

        Optional<AuthUser> user = ExtractFromSessionAuthUser(session);

        if (user.isEmpty()) {
            return new ModelAndView("redirect:/login", modelMap);
        }

        Optional<Page> page = this.carBuyPageService.GetPage();

        if (page.isEmpty()) {
            return this.SendError("/", modelMap);
        }

        user.ifPresent(authUser -> page.get().setUser(authUser));
        page.get().Link(modelMap);

        return new ModelAndView("pages/car_buy", modelMap);
    }

    @Override
    @PostMapping("/purchase")
    public ModelAndView Form(
            HttpServletRequest request,
            ModelMap modelMap,
            CarBuyForm formData,
            BindingResult bindingResult,
            HttpSession session
    ) {

        if(bindingResult.hasErrors()) {
            return this.SendError("Error binding the form", "/", modelMap);
        }

        ErrorWithMessage formError = formData.ValidateForm();

        if (formError.isError()) {
            return this.SendError(formError.getMessage(), "/", modelMap);
        }

        Optional<Integer> userId = this.ExtractFromSessionInteger(session, "userId");

        if (userId.isEmpty()) {
            return new ModelAndView("redirect:/login", modelMap);
        }

        ErrorWithMessage serviceError = this.carBuyPageService.ProcessForm(formData, userId.get());

        if (serviceError.isError()) {
            return this.SendError(serviceError.getMessage(), "/", modelMap);
        }

        return new ModelAndView("redirect:/", modelMap);
    }
}
