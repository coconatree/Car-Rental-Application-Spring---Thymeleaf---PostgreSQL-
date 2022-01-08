package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Configuration.AuthenticationFacadeImpl;
import com.car_rental.Configuration.AuthenticationFacadeI;
import com.car_rental.Configuration.Utility;
import com.car_rental.Impl.ServiceImpl.ReviewServiceImpl;
import com.car_rental.Interface.ControllerI.ReviewControllerI;
import com.car_rental.Interface.ServiceI.ReviewServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.ReviewForm;
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
@RequestMapping("/car")
public class ReviewControllerImpl extends BaseController implements ReviewControllerI {

    private final AuthenticationFacadeI authenticationFacade;
    private final ReviewServiceI service;

    public ReviewControllerImpl(AuthenticationFacadeImpl authenticationFacade, ReviewServiceImpl service) {
        this.authenticationFacade = authenticationFacade;
        this.service = service;
    }

    @Override
    @GetMapping("/review")
    public ModelAndView Redirected(HttpServletRequest request, ModelMap modelMap, HttpSession session) {

        Optional<Integer> carId = Utility.extractParameterAsInteger(request, "carId");

        if  (carId.isEmpty()) {
            return this.SendError("You need to specify a car", "/car", modelMap);
        }

        Optional<Page> page = this.service.GetPage(carId.get());

        if (page.isEmpty()) {
            return this.SendError("Error retrieving the page", "/car", modelMap);
        }

        Optional<AuthUser> user = authenticationFacade.getAuthenticatedUser();

        user.ifPresent(authUser -> page.get().setUser(authUser));
        page.get().Link(modelMap);

        return new ModelAndView("pages/review", modelMap);
    }

    @Override
    @PostMapping("/review")
    public ModelAndView Form(HttpServletRequest request, ModelMap modelMap, ReviewForm formData, BindingResult bindingResult, HttpSession session) {
        return new ModelAndView("redirect:/car", new ModelMap());
    }
}
