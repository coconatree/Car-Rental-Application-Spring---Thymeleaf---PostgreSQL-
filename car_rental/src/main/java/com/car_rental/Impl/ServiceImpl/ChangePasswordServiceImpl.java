package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Interface.RepositoryI.UserRepoI;
import com.car_rental.Interface.ServiceI.ChangePasswordServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Logic.ServiceLogic.RedirectedServiceI;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.DatabaseModel.User;
import com.car_rental.Model.FormModel.ChangePasswordForm;
import com.car_rental.Model.PageModel.ChangePasswordPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChangePasswordServiceImpl extends BaseService implements ChangePasswordServiceI {

    private UserRepoI userRepo;


    @Autowired
    public ChangePasswordServiceImpl(UserRepoI userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public ErrorWithMessage ProcessForm(ChangePasswordForm form, AuthUser additional) {
        try {
            User u = this.userRepo.selectUserByEmail(additional.getUsername());
            this.userRepo.Update(u.getId(), User.from(form));
            return ErrorWithMessage.builder().error(false).build();
        }
        catch (Exception err) {
            this.HandleException(err);
            return ErrorWithMessage.builder().error(true).message("Error processing the password form").build();
        }
    }

    @Override
    public Optional<Page> GetPage() {
        return Optional.of(ChangePasswordPage.builder().build());
    }
}
