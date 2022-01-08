package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Configuration.Utility;
import com.car_rental.Impl.RepositoryImpl.BranchRepoImpl;
import com.car_rental.Impl.RepositoryImpl.RequestRepoImpl;
import com.car_rental.Impl.RepositoryImpl.UserRepoImpl;
import com.car_rental.Interface.ControllerI.RequestCheckControllerI;
import com.car_rental.Interface.ServiceI.RequestCheckServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.DatabaseModel.Request;
import com.car_rental.Model.DatabaseModel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RequestCheckControllerImpl extends BaseController implements RequestCheckControllerI {

    private final RequestCheckServiceI requestCheckService;
    private final RequestRepoImpl requestRepo;
    private final BranchRepoImpl branchRepo;
    private final UserRepoImpl userRepo;

    @Autowired
    public RequestCheckControllerImpl(RequestCheckServiceI requestCheckService,
                                      UserRepoImpl userRepo, RequestRepoImpl requestRepo,
                                      BranchRepoImpl branchRepo) {
        this.requestRepo = requestRepo;
        this.requestCheckService = requestCheckService;
        this.userRepo = userRepo;
        this.branchRepo = branchRepo;
    }

    @Override
    @GetMapping("/employee/request/approve")
    public ModelAndView requestApprove(HttpServletRequest request, ModelMap modelMap, HttpSession session) {
        Optional<Integer> requestId = Utility.extractParameterAsInteger(request, "reqId");
        if (requestId.isEmpty()) {
            return this.SendError("/employee/request", modelMap);
        }

        Optional<AuthUser> authUser = this.ExtractFromSessionAuthUser(session);

        if (authUser.isEmpty()) {
            this.SendError("/", modelMap);
        }

        requestRepo.approveRequest(requestId, authUser.get().getId());

        return new ModelAndView("redirect:/employee/request", modelMap);
    }

    @Override
    @GetMapping("/employee/request/decline")
    public ModelAndView requestDecline(HttpServletRequest request, ModelMap modelMap, HttpSession session) {
        Optional<Integer> requestId = Utility.extractParameterAsInteger(request, "reqId");
        if (requestId.isEmpty()) {
            return this.SendError("/employee/request", modelMap);
        }

        Optional<AuthUser> authUser = this.ExtractFromSessionAuthUser(session);

        User user = userRepo.selectUserByEmail(authUser.get().getUsername());
        requestRepo.declineRequest(requestId, user.getId());


        return new ModelAndView("redirect:/employee/request", modelMap);
    }
    @Override
    @GetMapping("/employee/request")
    public ModelAndView Page(HttpServletRequest request, ModelMap modelMap, HttpSession session) {

        Optional<Page> requestCheckPage = this.requestCheckService.GetPage();

        if (requestCheckPage.isEmpty()) {
            return this.SendError("/employee/request", modelMap);
        }

        Optional<AuthUser> authUser = this.ExtractFromSessionAuthUser(session);
        authUser.ifPresent(curUser -> requestCheckPage.get().setUser(curUser));

        User user = userRepo.selectUserByEmail(authUser.get().getUsername());


        List<Request> requests = requestRepo.getUnhandledRequests(user.getId());
        List<String> departureBranches = new ArrayList<>();
        List<String> destinationBranches = new ArrayList<>();
        for (Request r : requests) {
            departureBranches.add(branchRepo.getBranchNameById(r.startBranch));
            destinationBranches.add(branchRepo.getBranchNameById(r.destBranch));
        }

        modelMap.addAttribute("customerRequests", requests);
        modelMap.addAttribute("departureBranches", departureBranches);
        modelMap.addAttribute("destinationBranches", destinationBranches);
        requestCheckPage.get().Link(modelMap);


        return new ModelAndView("pages/request_check", modelMap);
    }
}