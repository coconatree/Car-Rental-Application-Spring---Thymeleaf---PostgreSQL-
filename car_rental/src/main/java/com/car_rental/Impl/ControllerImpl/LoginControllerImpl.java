package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Impl.ServiceImpl.LoginServiceImp;
import com.car_rental.Interface.ControllerI.LoginControllerI;
import com.car_rental.Interface.ServiceI.LoginServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.FormModel.AuthenticationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

// ControllerAnnotation
@Controller
public class LoginControllerImpl extends BaseController implements LoginControllerI {

    private final LoginServiceI loginService;

    @Autowired
    public LoginControllerImpl(LoginServiceImp loginService) {
        this.loginService = loginService;
    }

    @Override
    @GetMapping("/login")
    public ModelAndView Page(
            HttpServletRequest request,
            ModelMap modelMap,
            HttpSession session
    ) {

        Optional<Page> loginPage = this.loginService.GetPage();

        if (loginPage.isEmpty()) {
            return this.SendError("/login", modelMap);
        }
        loginPage.get().Link(modelMap);

        return new ModelAndView("pages/login", modelMap);
    }
}
