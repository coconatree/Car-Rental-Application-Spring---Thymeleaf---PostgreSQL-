package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Interface.ControllerI.ChangePasswordControllerI;
import com.car_rental.Interface.ServiceI.ChangePasswordServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.ChangePasswordForm;
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
public class ChangePasswordControllerImpl extends BaseController implements ChangePasswordControllerI {

    private ChangePasswordServiceI changePasswordService;

    @Autowired
    public ChangePasswordControllerImpl(ChangePasswordServiceI changePasswordService) {
        super();
        this.changePasswordService = changePasswordService;
    }

    @Override
    @PostMapping("customer/changepassword")
    public ModelAndView Form(HttpServletRequest request, ModelMap modelMap, ChangePasswordForm formData, BindingResult bindingResult, HttpSession session) {

        Optional<AuthUser> user = ExtractFromSessionAuthUser(session);

        ErrorWithMessage formAndServiceError = this.ValidateAndProcessForm(this.changePasswordService, formData, user.get(), bindingResult);

        if (formAndServiceError.isError()) {
            return this.SendError(formAndServiceError.getMessage(), "/changepassword", modelMap);
        }
        return new ModelAndView("redirect:/customer/info", modelMap);
    }

    @Override
    @GetMapping("/customer/changepassword")
    public ModelAndView Page(HttpServletRequest request, ModelMap modelMap, HttpSession session) {


        Optional<Page> page = this.changePasswordService.GetPage();


        page.get().Link(modelMap);
        return new ModelAndView("pages/change_password", modelMap);

    }
}
