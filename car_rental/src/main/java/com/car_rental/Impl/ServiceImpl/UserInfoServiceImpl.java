package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Impl.RepositoryImpl.CustomerRepoImpl;
import com.car_rental.Interface.RepositoryI.CustomerRepoI;
import com.car_rental.Interface.ServiceI.UserInfoServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.FormModel.BalanceAmountForm;
import com.car_rental.Model.PageModel.UserInfoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserInfoServiceImpl extends BaseService implements UserInfoServiceI {


    private final CustomerRepoI customerRepo;

    @Autowired
    public UserInfoServiceImpl(CustomerRepoImpl customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Optional<Page> GetPage() {
        try {
            return Optional.of(UserInfoPage.builder()
                    .build());
        }
        catch (Exception err) {
            this.HandleException(err);
            return Optional.empty();
        }
    }

    @Override
    public ErrorWithMessage ProcessForm(BalanceAmountForm form, Integer customerId) {

        try {
            this.customerRepo.addBalance(customerId, form.getBalance());

            return ErrorWithMessage.builder().error(false).build();
        }
        catch (DataAccessException | SQLException err) {
            this.HandleException(err);
        }
        return ErrorWithMessage.builder().error(true).message("Error processing the form") .build();
    }
}
