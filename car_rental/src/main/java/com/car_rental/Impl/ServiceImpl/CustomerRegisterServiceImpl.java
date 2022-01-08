package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.PageModel.CustomerRegistrationPage;
import com.car_rental.Model.DatabaseModel.Customer;
import com.car_rental.Model.FormModel.CustomerRegisterForm;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Impl.RepositoryImpl.CustomerRepoImpl;
import com.car_rental.Interface.RepositoryI.CustomerRepoI;
import com.car_rental.Interface.ServiceI.CustomerRegisterServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerRegisterServiceImpl extends BaseService implements CustomerRegisterServiceI {

    private final CustomerRepoI customerRepo;

    @Autowired
    public CustomerRegisterServiceImpl(CustomerRepoImpl customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Optional<Page> GetPage() {
        return Optional.of(CustomerRegistrationPage.builder().build());
    }

    @Override
    public ErrorWithMessage ProcessForm(CustomerRegisterForm form, Integer additional) {
        try {
            this.customerRepo.Insert(Customer.from(form));
            return ErrorWithMessage.builder().error(false).build();
        }
        catch (Exception err) {
            this.HandleException(err);
            return ErrorWithMessage.builder().error(true).message("Error processing the customer form").build();
        }
    }
}
