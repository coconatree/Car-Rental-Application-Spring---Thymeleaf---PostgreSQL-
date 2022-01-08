package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.PageModel.LandingPageEmployee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LandingControllerEmployeeImpl extends BaseController {

    @GetMapping("/employee")
    public ModelAndView GetLandingPageEmployee(HttpSession session, ModelMap modelMap) {
        Optional<AuthUser> user = this.ExtractFromSessionAuthUser(session);

        if (user.isEmpty()) {
            return new ModelAndView("redirect/login", modelMap);
        }

        Page page = LandingPageEmployee.builder()
                .message(user.get().getUsername() + " - " + user.get().getRole())
                .build();

        page.setUser(user.get());
        page.Link(modelMap);

        return new ModelAndView("pages/landing_page_employee", modelMap);
    }
}
