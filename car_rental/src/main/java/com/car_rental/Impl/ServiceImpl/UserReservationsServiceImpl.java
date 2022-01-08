package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Interface.ServiceI.UserReservationsServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Model.PageModel.ProfilePage;
import com.car_rental.Model.PageModel.UserReservationsPage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserReservationsServiceImpl extends BaseService implements UserReservationsServiceI {

    @Override
    public Optional<Page> GetPage() {
        try {
            return Optional.of(UserReservationsPage.builder()
                    .build());
        }
        catch (Exception err) {
            this.HandleException(err);
            return Optional.empty();
        }
    }
}
