package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Configuration.Utility;
import com.car_rental.Impl.RepositoryImpl.BranchRepoImpl;
import com.car_rental.Impl.RepositoryImpl.ReservationRepoImpl;
import com.car_rental.Impl.RepositoryImpl.UserRepoImpl;
import com.car_rental.Interface.ControllerI.DamageCheckControllerI;
import com.car_rental.Interface.ServiceI.ReservationCheckServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.DatabaseModel.Reservation;
import com.car_rental.Model.DatabaseModel.User;
import com.car_rental.Model.FormModel.DamageReportForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class DamageCheckControllerImpl extends BaseController implements DamageCheckControllerI {

    private final ReservationCheckServiceI reservationCheckService;
    private final ReservationRepoImpl reservationRepo;
    private final BranchRepoImpl branchRepo;
    private final UserRepoImpl userRepo;

    @Autowired
    public DamageCheckControllerImpl(ReservationCheckServiceI reservationCheckService,
                                     UserRepoImpl userRepo, ReservationRepoImpl reservationRepo,
                                     BranchRepoImpl branchRepo) {
        this.reservationRepo = reservationRepo;
        this.reservationCheckService = reservationCheckService;
        this.userRepo = userRepo;
        this.branchRepo = branchRepo;
    }

    @Override
    @GetMapping("/employee/reservation/report/undamaged")
    public ModelAndView reservationReport(HttpServletRequest request, ModelMap modelMap, HttpSession session) {
        Optional<Integer> reservationId = Utility.extractParameterAsInteger(request, "resId");
        if (reservationId.isEmpty()) {
            return this.SendError("/employee/reservation/report", modelMap);
        }

        reservationRepo.makeUndamaged(reservationId);


        return new ModelAndView("redirect:/employee/reservation/report", modelMap);
    }

    @Override
    @RequestMapping(value = "/employee/reservation/report/damaged", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView reservationReportDamage(HttpServletRequest request, ModelMap modelMap, HttpSession session, @ModelAttribute DamageReportForm report, BindingResult br) {

            Optional<Integer> reservationId = Utility.extractParameterAsInteger(request, "resId");
            if (reservationId.isEmpty() || br.hasErrors()) {
                return this.SendError("/employee/reservation/report", modelMap);
            }


        reservationRepo.submitDamageReport(reservationId, report.getMessage(), report.getCost());

        return new ModelAndView("redirect:/employee/reservation/report", modelMap);
    }


    @Override
    @GetMapping("/employee/reservation/report")
    public ModelAndView Page(HttpServletRequest request, ModelMap modelMap, HttpSession session) {

        Optional<Page> reservationCheckPage = this.reservationCheckService.GetPage();

        if (reservationCheckPage.isEmpty()) {
            return this.SendError("/employee/reservation/report", modelMap);
        }

        Optional<AuthUser> authUser = this.ExtractFromSessionAuthUser(session);
        authUser.ifPresent(curUser -> reservationCheckPage.get().setUser(curUser));

        User user = userRepo.selectUserByEmail(authUser.get().getUsername());


        List<Reservation> reservations = reservationRepo.getFinishedReservations(user.getId());
        List<String> departureBranches = new ArrayList<>();
        List<String> destinationBranches = new ArrayList<>();
        for (Reservation r : reservations) {
            departureBranches.add(branchRepo.getBranchNameById(r.getDepartFrom()));
            destinationBranches.add(branchRepo.getBranchNameById(r.getArriveAt()));
        }

        modelMap.addAttribute("customerReservations", reservations);
        modelMap.addAttribute("departureBranches", departureBranches);
        modelMap.addAttribute("destinationBranches", destinationBranches);
        modelMap.addAttribute("report", DamageReportForm.builder().build());
        reservationCheckPage.get().Link(modelMap);


        return new ModelAndView("pages/damage_check", modelMap);
    }
}