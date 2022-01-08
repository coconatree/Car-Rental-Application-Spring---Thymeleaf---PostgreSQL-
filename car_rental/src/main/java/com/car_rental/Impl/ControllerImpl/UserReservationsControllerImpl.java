package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Impl.RepositoryImpl.ReservationRepoImpl;
import com.car_rental.Impl.RepositoryImpl.UserRepoImpl;
import com.car_rental.Impl.ServiceImpl.UserReservationsServiceImpl;
import com.car_rental.Interface.ControllerI.UserReservationsControllerI;
import com.car_rental.Interface.RepositoryI.ReviewRepoI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.CompositeModel.ReservationWithCar;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.DatabaseModel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class UserReservationsControllerImpl extends BaseController implements UserReservationsControllerI {

    private final UserReservationsServiceImpl userReservationsService;
    private final ReservationRepoImpl reservationRepo;
    private final UserRepoImpl userRepo;
    private final ReviewRepoI reviewRepo;

    @Autowired
    public UserReservationsControllerImpl(UserReservationsServiceImpl userReservationsService, ReservationRepoImpl reservationRepo, UserRepoImpl userRepo, ReviewRepoI reviewRepo) {
        this.userReservationsService = userReservationsService;
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;
        this.reviewRepo = reviewRepo;
    }

    @Override
    @GetMapping("/customer/reservations")
    public ModelAndView Page(HttpServletRequest request, ModelMap modelMap, HttpSession session) {

        boolean hasApproved = false, hasWaiting = false, hasDeclined = false, hasDReport = false,
                hasFinished = false, hasPaid = false, hasIntact = false;

        Optional<Page> page = this.userReservationsService.GetPage();

        if (page.isEmpty()) {
            return this.SendError("/customer/reservations", modelMap);
        }

        Optional<AuthUser> authUser = this.ExtractFromSessionAuthUser(session);
        authUser.ifPresent(curUser -> page.get().setUser(curUser));

        try {
            User user = userRepo.selectUserByEmail(authUser.get().getUsername());

            List<ReservationWithCar> reservations = reservationRepo.selectResWithCarByUserId(user.getId());
            System.out.println(reservations.isEmpty());

            if( !reservations.isEmpty())
            {
                for (ReservationWithCar r:
                        reservations) {

                    r.getReservation().setHasReview(this.reviewRepo.checkIfReservationHasReview(r.getReservation().getReservationId()));

                    if(r.getReservation().getStatus() == 100)
                    {
                        hasWaiting = true;
                        r.getReservation().setStatusStr("Created");
                    }
                    else if(r.getReservation().getStatus() == 200)
                    {
                        hasApproved = true;
                        r.getReservation().setStatusStr("Approved");
                    }
                    else if(r.getReservation().getStatus() == 300)
                    {
                        hasDeclined = true;
                        r.getReservation().setStatusStr("Declined");
                    }
                    else if(r.getReservation().getStatus() == 400)
                    {
                        hasDReport = true;
                        r.getReservation().setStatusStr("Damage Report Created");
                    }
                    else if(r.getReservation().getStatus() == 500)
                    {
                        hasFinished = true;
                        r.getReservation().setStatusStr("Finished");
                    }
                    else if(r.getReservation().getStatus() == 600)
                    {
                        hasPaid = true;
                        r.getReservation().setStatusStr("Paid");
                    }
                    else if(r.getReservation().getStatus() == 700)
                    {
                        hasIntact = true;
                        r.getReservation().setStatusStr("Intact, no additional cost");
                    }
                }
            }

            modelMap.addAttribute("hasWaiting", hasWaiting);
            modelMap.addAttribute("hasApproved", hasApproved);
            modelMap.addAttribute("hasDReport", hasDReport);
            modelMap.addAttribute("hasDeclined", hasDeclined);
            modelMap.addAttribute("hasFinished", hasFinished);
            modelMap.addAttribute("hasPaid", hasPaid);
            modelMap.addAttribute("hasIntact", hasIntact);
            modelMap.addAttribute("hasReservation", !reservations.isEmpty());

            modelMap.addAttribute("customerReservations", reservations);

            page.get().Link(modelMap);
        }
        catch (Exception err)  {
            userReservationsService.HandleException(err);
        }

        return new ModelAndView("pages/user_reservations", modelMap);
    }
}
