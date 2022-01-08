package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Configuration.AuthenticationFacadeImpl;
import com.car_rental.Configuration.AuthenticationFacadeI;
import com.car_rental.Impl.ServiceImpl.HomeServiceImpl;
import com.car_rental.Interface.ControllerI.HomeControllerI;
import com.car_rental.Interface.ServiceI.HomeServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.BranchSelectionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 Branch Selection Page Controller


 */

@Controller
public class HomeControllerImpl extends BaseController implements HomeControllerI {

    private AuthenticationFacadeI authenticationFacade;
    private final HomeServiceI branchService;

    @Autowired
    public HomeControllerImpl(
            AuthenticationFacadeImpl authenticationFacade,
            HomeServiceImpl branchService) {
        this.authenticationFacade = authenticationFacade;
        this.branchService = branchService;
    }

    @Override
    @GetMapping("")
    public ModelAndView Page(
            HttpServletRequest request,
            ModelMap modelMap,
            HttpSession session
    ) {

        Optional<AuthUser> user = this.ExtractFromSessionAuthUser(session);

        if (user.isEmpty()) {
            return new ModelAndView("redirect:/login", modelMap);
        }

        String role = user.get().getRole();

        if (role.equals("EMPLOYEE")) {
            return new ModelAndView("redirect:/employee", modelMap);
        }

        if (role.equals("MANAGER")) {
            return new ModelAndView("redirect:/manager", modelMap);
        }

        Optional<Page> page = this.branchService.GetPage();
        if (page.isEmpty()) {
            return SendError( "/", modelMap);
        }

        user.ifPresent(authUser -> page.get().setUser(authUser));
        page.get().Link(modelMap);

        return new ModelAndView("pages/index", modelMap);
    }

    @Override
    @PostMapping("")
    public ModelAndView Form(
            HttpServletRequest request,
            ModelMap modelMap,
            BranchSelectionForm formData,
            BindingResult bindingResult,
            HttpSession session
    ) {

        ErrorWithMessage formAndServiceError = this.ValidateAndProcessForm(this.branchService, formData, 0, bindingResult);

        if (formAndServiceError.isError()) {
            this.SendError(formAndServiceError.getMessage(), "/", modelMap);
        }

        modelMap.addAttribute("branchId", formData.getPreferredBranchId());
        return new ModelAndView("redirect:/car", modelMap);
    }
}
