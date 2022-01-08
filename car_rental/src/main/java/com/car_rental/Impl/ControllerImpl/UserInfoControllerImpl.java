package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Impl.RepositoryImpl.CustomerRepoImpl;
import com.car_rental.Impl.RepositoryImpl.UserRepoImpl;
import com.car_rental.Impl.ServiceImpl.UserInfoServiceImpl;
import com.car_rental.Interface.ControllerI.UserInfoControllerI;
import com.car_rental.Interface.ServiceI.UserInfoServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.DatabaseModel.Customer;
import com.car_rental.Model.DatabaseModel.Reservation;
import com.car_rental.Model.DatabaseModel.User;
import com.car_rental.Model.FormModel.BalanceAmountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
public class UserInfoControllerImpl extends BaseController implements UserInfoControllerI {

    private UserInfoServiceImpl userInfoService;
    private CustomerRepoImpl customerRepo;
    private UserRepoImpl userRepo;

    @Autowired
    public UserInfoControllerImpl(UserInfoServiceImpl userInfoService, CustomerRepoImpl customerRepo, UserRepoImpl userRepo) {
        this.userInfoService = userInfoService;
        this.customerRepo = customerRepo;
        this.userRepo = userRepo;

    }

    @Override
    @GetMapping("/customer/info")
    public ModelAndView Page(HttpServletRequest request, ModelMap modelMap, HttpSession session) {
        Optional<Page> page = this.userInfoService.GetPage();

        if (page.isEmpty()) {
            return this.SendError("/customer/info", modelMap);
        }

        Optional<AuthUser> authUser = this.ExtractFromSessionAuthUser(session);
        authUser.ifPresent(curUser -> page.get().setUser(curUser));

        try {
            User user = userRepo.selectUserByEmail(authUser.get().getUsername());

            Customer customer = customerRepo.selectCustomerById(user.getId());
            modelMap.addAttribute("userInfo", customer);

            page.get().Link(modelMap);
        }
        catch (Exception err)
        {
            userInfoService.HandleException(err);
        }
        return new ModelAndView("pages/user_info", modelMap);
    }

    @Override
    @PostMapping("customer/info")
    public ModelAndView Form(HttpServletRequest request, ModelMap modelMap, BalanceAmountForm formData, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors())
            return this.SendError("/customer/info", modelMap);

        ErrorWithMessage formError = formData.ValidateForm();

        if (formError.isError())
            return this.SendError(formError.getMessage(), "/customer/info", modelMap);

        Optional<AuthUser> user = ExtractFromSessionAuthUser(session);

        if (user.isEmpty())
            return new ModelAndView("redirect:/login", modelMap);

        ErrorWithMessage serviceError = this.userInfoService.ProcessForm(formData, user.get().getId());

        if (serviceError.isError())
            return this.SendError(serviceError.getMessage(), "/customer/info", modelMap);

        return new ModelAndView("redirect:/customer/info");
    }
}
