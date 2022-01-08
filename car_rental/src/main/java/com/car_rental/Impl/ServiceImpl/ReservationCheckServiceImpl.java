package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Interface.ServiceI.ProfileServiceI;
import com.car_rental.Interface.ServiceI.ReservationCheckServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Model.PageModel.LoginPage;
import com.car_rental.Model.PageModel.ProfilePage;
import com.car_rental.Model.PageModel.ReservationCheckPage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationCheckServiceImpl extends BaseService implements ReservationCheckServiceI {
    @Override
    public Optional<Page> GetPage() {
        try {
            return Optional.of(ReservationCheckPage.builder()
                    .build());
        }
        catch (Exception err) {
            this.HandleException(err);
            return Optional.empty();
        }
    }
}
