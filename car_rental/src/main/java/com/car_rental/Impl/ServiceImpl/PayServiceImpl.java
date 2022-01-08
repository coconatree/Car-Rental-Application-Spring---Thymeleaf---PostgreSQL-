package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Interface.RepositoryI.CustomerRepoI;
import com.car_rental.Interface.RepositoryI.ReservationRepoI;
import com.car_rental.Interface.RepositoryI.UserRepoI;
import com.car_rental.Interface.ServiceI.PayServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.AuthUser;

import com.car_rental.Model.DatabaseModel.Customer;
import com.car_rental.Model.DatabaseModel.Reservation;
import com.car_rental.Model.DatabaseModel.User;
import com.car_rental.Model.FormModel.PayForm;

import com.car_rental.Model.PageModel.PayPage;
import com.car_rental.Model.PageModel.ReturnCarPage;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class PayServiceImpl extends BaseService implements PayServiceI {


    private UserRepoI userRepo;
    private ReservationRepoI reservationRepo;
    private CustomerRepoI customerRepo;

    public PayServiceImpl(UserRepoI userRepo, ReservationRepoI reservationRepo, CustomerRepoI customerRepo) {
        this.userRepo = userRepo;
        this.reservationRepo = reservationRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public ErrorWithMessage ProcessForm(PayForm form, AuthUser additional) {
        try {
            User u = this.userRepo.selectUserByEmail(additional.getUsername());
            System.out.println(additional.getUsername());
            System.out.println(u.getId());
            Customer c = this.customerRepo.selectCustomerById(u.getId());

            if( c.getPoint() < form.getTotalCost())
            {
                return ErrorWithMessage.builder().error(true).message("Your balance is not enough for this payment.").build();
            }
            else
            {
                Reservation r = this.reservationRepo.SelectResById(form.getReservationId());
                System.out.println(c.getId());
                this.customerRepo.makePayment(c.getId(), form.getTotalCost());
                this.reservationRepo.UpdatePayment(form.getReservationId(), r);
                return ErrorWithMessage.builder().error(false).build();
            }

        }
        catch (DataAccessException | SQLException err) {
            this.HandleException(err);
            return ErrorWithMessage.builder().error(true).message("Error processing the return form").build();
        }
    }

    @Override
    public Optional<Page> GetPage(AuthUser redirectedData) {
        try {

            return Optional.of(PayPage.builder()
                    .reservationWithCars(this.reservationRepo.
                            selectResWithCarByUserId(this.userRepo.selectUserByEmail(redirectedData.getUsername()).getId()))
                    .build());
        }
        catch (DataAccessException err) {
            this.HandleException(err);
            return Optional.empty();
        }
    }
}
