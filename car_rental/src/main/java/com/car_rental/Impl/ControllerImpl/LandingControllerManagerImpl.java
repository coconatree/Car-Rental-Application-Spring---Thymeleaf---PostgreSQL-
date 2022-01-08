package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Interface.RepositoryI.BranchRepoI;
import com.car_rental.Interface.RepositoryI.ManagerRepoI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.PageModel.LandingPageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LandingControllerManagerImpl extends BaseController {

    private final BranchRepoI branchRepoI;
    private final ManagerRepoI managerRepo;

    @Autowired
    public LandingControllerManagerImpl(BranchRepoI branchRepoI, ManagerRepoI managerRepo) {
        this.branchRepoI = branchRepoI;
        this.managerRepo = managerRepo;
    }

    @GetMapping("/manager")
    public ModelAndView GetLandingPageEmployee(HttpSession session, ModelMap modelMap) {
        Optional<AuthUser> user = this.ExtractFromSessionAuthUser(session);

        if (user.isEmpty()) {
            return new ModelAndView("redirect/login", modelMap);
        }

        Integer branchId = this.managerRepo.SelectBranchFromManagerId(user.get().getId());

        Page page = LandingPageManager.builder()
                .message(user.get().getUsername() + " - " + user.get().getRole())
                .branch(branchRepoI.GetBranchById(branchId))
                .build();

        page.setUser(user.get());
        page.Link(modelMap);

        return new ModelAndView("pages/landing_page_manager", modelMap);
    }
}
