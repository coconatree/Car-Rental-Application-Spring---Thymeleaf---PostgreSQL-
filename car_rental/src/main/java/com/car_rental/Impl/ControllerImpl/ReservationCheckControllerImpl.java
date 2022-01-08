package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Configuration.Utility;
import com.car_rental.Impl.RepositoryImpl.BranchRepoImpl;
import com.car_rental.Impl.RepositoryImpl.ReservationRepoImpl;
import com.car_rental.Impl.RepositoryImpl.UserRepoImpl;
import com.car_rental.Interface.ControllerI.ReservationCheckControllerI;
import com.car_rental.Interface.ServiceI.ReservationCheckServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.DatabaseModel.Reservation;
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
public class ReservationCheckControllerImpl extends BaseController implements ReservationCheckControllerI {

    private final ReservationCheckServiceI reservationCheckService;
    private final ReservationRepoImpl reservationRepo;
    private final BranchRepoImpl branchRepo;
    private final UserRepoImpl userRepo;

    @Autowired
    public ReservationCheckControllerImpl(ReservationCheckServiceI reservationCheckService,
                                          UserRepoImpl userRepo, ReservationRepoImpl reservationRepo,
                                            BranchRepoImpl branchRepo) {
        this.reservationRepo = reservationRepo;
        this.reservationCheckService = reservationCheckService;
        this.userRepo = userRepo;
        this.branchRepo = branchRepo;
    }

    @Override
    @GetMapping("/employee/reservation/approve")
    public ModelAndView reservationApprove(HttpServletRequest request, ModelMap modelMap, HttpSession session) {
        Optional<Integer> reservationId = Utility.extractParameterAsInteger(request, "resId");
        if (reservationId.isEmpty()) {
            return this.SendError("/employee/reservation", modelMap);
        }

        Optional<AuthUser> authUser = this.ExtractFromSessionAuthUser(session);

        User user = userRepo.selectUserByEmail(authUser.get().getUsername());
        reservationRepo.approveReservation(reservationId, user.getId());


        return new ModelAndView("redirect:/employee/reservation", modelMap);
    }

    @Override
    @GetMapping("/employee/reservation/decline")
    public ModelAndView reservationDecline(HttpServletRequest request, ModelMap modelMap, HttpSession session) {
        Optional<Integer> reservationId = Utility.extractParameterAsInteger(request, "resId");
        if (reservationId.isEmpty()) {
            return this.SendError("/employee/reservation", modelMap);
        }

        Optional<AuthUser> authUser = this.ExtractFromSessionAuthUser(session);

        User user = userRepo.selectUserByEmail(authUser.get().getUsername());
        reservationRepo.declineReservation(reservationId, user.getId());


        return new ModelAndView("redirect:/employee/reservation", modelMap);
    }
    @Override
    @GetMapping("/employee/reservation")
    public ModelAndView Page(HttpServletRequest request, ModelMap modelMap, HttpSession session) {

        Optional<Page> reservationCheckPage = this.reservationCheckService.GetPage();

        if (reservationCheckPage.isEmpty()) {
            return this.SendError("/employee/reservation", modelMap);
        }

        Optional<AuthUser> authUser = this.ExtractFromSessionAuthUser(session);
        authUser.ifPresent(curUser -> reservationCheckPage.get().setUser(curUser));

        User user = userRepo.selectUserByEmail(authUser.get().getUsername());

        List<Reservation> reservations = reservationRepo.getUnhandledReservations(user.getId());
        List<String> departureBranches = new ArrayList<>();
        List<String> destinationBranches = new ArrayList<>();
        for (Reservation r : reservations) {
            departureBranches.add(branchRepo.getBranchNameById(r.getDepartFrom()));
            // destinationBranches.add(branchRepo.getBranchNameById(r.getArriveAt()));
        }

        modelMap.addAttribute("customerReservations", reservations);
        modelMap.addAttribute("departureBranches", departureBranches);
        modelMap.addAttribute("destinationBranches", destinationBranches);
        reservationCheckPage.get().Link(modelMap);


        return new ModelAndView("pages/reservation_check", modelMap);
    }
}