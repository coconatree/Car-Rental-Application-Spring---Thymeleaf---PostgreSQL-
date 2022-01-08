package com.car_rental.Impl.ControllerImpl.ErrorAndSuccessControllerImpl;

import com.car_rental.Configuration.Utility;
import com.car_rental.Impl.ServiceImpl.SuccessServiceImpl;
import com.car_rental.Interface.ControllerI.SuccessControllerI;
import com.car_rental.Model.RedirectedDataModel.SuccessData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SuccessControllerImpl implements SuccessControllerI {

    private final SuccessServiceImpl successService;

    @Autowired
    public SuccessControllerImpl(SuccessServiceImpl successService) {
        this.successService = successService;
    }

    @Override
    @GetMapping("/success")
    public ModelAndView Redirected(
            HttpServletRequest request,
            ModelMap modelMap,
            HttpSession session
    ) {

        String message = Utility.extractParameterAsString(request, "message").orElse("Successful");
        String afterURL  = Utility.extractParameterAsString(request,"afterURL").orElse("/");

        // No need for the optional check because we already have a default values and
        // a default value in SuccessService
        this.successService.GetPage(SuccessData.builder()
                .message(message)
                .afterURL(afterURL)
                .build())
                .get()
                .Link(modelMap);

        return new ModelAndView("pages/success", modelMap);
    }
}
