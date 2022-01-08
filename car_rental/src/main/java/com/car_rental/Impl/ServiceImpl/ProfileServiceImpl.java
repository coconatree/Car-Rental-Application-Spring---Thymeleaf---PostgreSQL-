package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Interface.ServiceI.ProfileServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Model.PageModel.LoginPage;
import com.car_rental.Model.PageModel.ProfilePage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl extends BaseService implements ProfileServiceI {
    @Override
    public Optional<Page> GetPage() {
        try {
            return Optional.of(ProfilePage.builder()
                    .build());
        }
        catch (Exception err) {
            this.HandleException(err);
            return Optional.empty();
        }
    }
}
