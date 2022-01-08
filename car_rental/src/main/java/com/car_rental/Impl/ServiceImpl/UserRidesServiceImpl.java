package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Impl.RepositoryImpl.ReservationRepoImpl;
import com.car_rental.Impl.RepositoryImpl.UserRepoImpl;
import com.car_rental.Interface.RepositoryI.ReservationRepoI;
import com.car_rental.Interface.RepositoryI.SharedRepoI;
import com.car_rental.Interface.RepositoryI.UserRepoI;
import com.car_rental.Interface.ServiceI.UserRidesServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Model.DatabaseModel.Reservation;
import com.car_rental.Model.PageModel.UserRidesPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserRidesServiceImpl extends BaseService implements UserRidesServiceI {

    private final ReservationRepoI reservationRepo;

    @Autowired
    public UserRidesServiceImpl(ReservationRepoImpl reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    @Override
    public Optional<Page> GetPage(Integer userId) {
        try {
            List<Reservation> list = this.reservationRepo.GetAllSharedReservationFromUserId(userId);

            list.forEach(System.out::println);

            return Optional.of(UserRidesPage.builder()
                    .sharedReservations(list)
                    .build());
        }
        catch (DataAccessException | SQLException err) {
            this.HandleException(err);
        }
        return Optional.empty();
    }
}
