package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Interface.RepositoryI.*;
import com.car_rental.Interface.ServiceI.ReturnCarServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.DatabaseModel.CarBranch;
import com.car_rental.Model.DatabaseModel.Reservation;
import com.car_rental.Model.FormModel.ReturnCarForm;
import com.car_rental.Model.PageModel.ReturnCarPage;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class ReturnCarServiceImpl extends BaseService implements ReturnCarServiceI {

    private UserRepoI userRepo;
    private ReservationRepoI reservationRepo;
    private CarBranchRepoI carBranchRepo;
    private BranchRepoI branchRepo;
    private CarRepoI carRepo;

    public ReturnCarServiceImpl(UserRepoI userRepo, ReservationRepoI reservationRepo, CarBranchRepoI carBranchRepo, BranchRepoI branchRepo, CarRepoI carRepo) {
        this.userRepo = userRepo;
        this.reservationRepo = reservationRepo;
        this.carBranchRepo = carBranchRepo;
        this.branchRepo = branchRepo;
        this.carRepo = carRepo;
    }

    @Override
    public ErrorWithMessage ProcessForm(ReturnCarForm form, AuthUser additional) {

        try {
            // Update Reservation status - ending branch, Insert new car_branch entry

            Reservation r = this.reservationRepo.SelectResById(form.getReservationId());
            this.reservationRepo.Update(r.getReservationId(), r);
            this.reservationRepo.updateEndingBranch(form.getBranchChosen(), r);

            this.carBranchRepo.Insert(form.getCarChosen(), form.getBranchChosen());

            return ErrorWithMessage.builder().error(false).build();


        }
        catch (DataAccessException | SQLException err) {
            this.HandleException(err);
            return ErrorWithMessage.builder().error(true).message("Error processing the return form").build();
        }

    }

    @Override
    public Optional<Page> GetPage(AuthUser authUser) {
        try {

            return Optional.of(ReturnCarPage.builder()
                    .branches(this.branchRepo.SelectAllIdAndName())
                    .reservationWithCars(this.reservationRepo.
                            selectResWithCarByUserId(this.userRepo.selectUserByEmail(authUser.getUsername()).getId()))
                    .build());
        }
        catch (DataAccessException err) {
            this.HandleException(err);
            return Optional.empty();
        }
    }
}
