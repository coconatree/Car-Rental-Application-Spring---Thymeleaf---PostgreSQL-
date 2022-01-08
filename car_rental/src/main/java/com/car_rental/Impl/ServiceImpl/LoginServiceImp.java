package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Interface.ServiceI.LoginServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.FormModel.AuthenticationForm;
import com.car_rental.Model.PageModel.LoginPage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImp extends BaseService implements LoginServiceI {

    @Override
    public Optional<Page> GetPage() {
        try {
            return Optional.of(LoginPage.builder()
                    .build());
        }
        catch (Exception err) {
            this.HandleException(err);
            return Optional.empty();
        }
    }

    @Override
    public ErrorWithMessage ProcessForm(AuthenticationForm form, Integer additional) {
        return ErrorWithMessage.builder().error(false).build();
    }
}
