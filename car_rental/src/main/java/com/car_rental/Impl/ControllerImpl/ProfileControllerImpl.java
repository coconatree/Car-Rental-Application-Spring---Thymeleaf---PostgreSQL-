package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Impl.RepositoryImpl.UserRepoImpl;
import com.car_rental.Impl.ServiceImpl.ProfileServiceImpl;
import com.car_rental.Interface.ControllerI.ProfileControllerI;
import com.car_rental.Interface.ServiceI.ProfileServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.DatabaseModel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class ProfileControllerImpl extends BaseController implements ProfileControllerI {

    private final ProfileServiceImpl profileService;

    private final UserRepoImpl userRepo;

    @Autowired
    public ProfileControllerImpl(ProfileServiceImpl profileService, UserRepoImpl userRepo) {
        this.profileService = profileService;
        this.userRepo = userRepo;
    }

    @Override
    @GetMapping("/customer/profile")
    public ModelAndView Page(HttpServletRequest request, ModelMap modelMap, HttpSession session) {

            Optional<Page> profilePage = this.profileService.GetPage();

            if (profilePage.isEmpty()) {
                return this.SendError("/customer/profile", modelMap);
            }

            Optional<AuthUser> authUser = this.ExtractFromSessionAuthUser(session);
            authUser.ifPresent(curUser -> profilePage.get().setUser(curUser));

            try {
                User user = userRepo.selectUserByEmail(authUser.get().getUsername());
                modelMap.addAttribute("username", user.getFirstName());
                modelMap.addAttribute("ppTitle", user.getFirstName() + "'s profile page");
                profilePage.get().Link(modelMap);
            }
            catch (Exception err){
                profileService.HandleException(err);

            }
            return new ModelAndView("pages/profile", modelMap);
    }
}

