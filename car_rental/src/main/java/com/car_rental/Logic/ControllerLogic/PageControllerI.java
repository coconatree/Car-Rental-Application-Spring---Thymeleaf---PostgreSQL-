package com.car_rental.Logic.ControllerLogic;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface PageControllerI {

    /**
     Generic Page Controller (Get Controller)

     This interface is used for creating get controllers which in the end returns HTML.

     param model Takes a 'org.springframework.ui.Model' typed model as a parameter.
     Model object is a part of the Spring's MVC abstraction, and It is a basically a
     hash map like interface. Spring provides us this model object, and we can
     'addAttributes' to later on retrieve them inside our Thymeleaf templates.

     @return String Returns the relative path of Thymeleaf template which we want to render.
     Afterwards Spring links our model which was passed by Spring as a function parameter and
     the returned Thymeleaf template.
     */

    ModelAndView Page(HttpServletRequest request, ModelMap modelMap, HttpSession session);
}
