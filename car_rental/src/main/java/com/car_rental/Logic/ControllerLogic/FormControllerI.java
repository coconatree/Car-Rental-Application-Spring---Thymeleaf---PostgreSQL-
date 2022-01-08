package com.car_rental.Logic.ControllerLogic;

import com.car_rental.Logic.ModelLogic.Form;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface FormControllerI<T extends Form> {

    /**
     Generic Form Controller (Post Controller)

     This interface is used for creating post controllers which in the end processes
     forms, initializes the data to be directed and redirects the URL with the data.

     */

    ModelAndView Form(
            HttpServletRequest request,
            ModelMap modelMap,
            T formData,
            BindingResult bindingResult,
            HttpSession session);
}
