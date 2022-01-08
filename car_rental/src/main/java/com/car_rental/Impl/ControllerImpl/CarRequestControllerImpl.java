package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Configuration.Utility;
import com.car_rental.Impl.ServiceImpl.CarRequestServiceImpl;
import com.car_rental.Interface.ControllerI.CarRequestControllerI;
import com.car_rental.Interface.ServiceI.CarRequestServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.FormModel.CarRequestForm;
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
public class CarRequestControllerImpl extends BaseController implements CarRequestControllerI {

    private final CarRequestServiceI service;

    @Autowired
    public CarRequestControllerImpl(CarRequestServiceImpl service) {
        this.service = service;
    }

    @Override
    @GetMapping("/request")
    public ModelAndView Page(
            HttpServletRequest request,
            ModelMap modelMap,
            HttpSession session
    ) {

        Optional<Integer> carId = Utility.extractParameterAsInteger(request, "carId");

        if (carId.isEmpty()) {
            return this.SendError("No car found to request", "/car", modelMap);
        }

        Optional<Page> page = this.service.GetPage(carId.get());

        if (page.isEmpty()) {
            return this.SendError("/", modelMap);
        }

        page.get().Link(modelMap);
        return new ModelAndView("pages/car_request", modelMap);
    }

    @Override
    @PostMapping("/request")
    public ModelAndView Form(
            HttpServletRequest request,
            ModelMap modelMap,
            CarRequestForm formData,
            BindingResult bindingResult,
            HttpSession session
    ) {

        System.out.println("ERROR" + formData);

        ErrorWithMessage errorWithMessage = this.ValidateAndProcessForm(this.service, formData, 0, bindingResult);

        if (errorWithMessage.isError()) {
            return this.SendError(errorWithMessage.getMessage(), "/", modelMap);
        }

        return new ModelAndView("redirect:/", modelMap);
    }
}
