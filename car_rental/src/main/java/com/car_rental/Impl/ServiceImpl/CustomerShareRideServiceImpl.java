package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Impl.RepositoryImpl.CustomerShareRideRepoImpl;
import com.car_rental.Interface.ServiceI.CustomerShareRideServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.CompositeModel.CustomerShareRideComposite;
import com.car_rental.Model.DatabaseModel.CustomerShareRide;
import com.car_rental.Model.FormModel.CustomerShareRideForm;
import com.car_rental.Model.PageModel.CustomerShareRidePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@Service
public class CustomerShareRideServiceImpl extends BaseService implements CustomerShareRideServiceI {
    private final CustomerShareRideRepoImpl rideSharingRepo;

    @Autowired
    public CustomerShareRideServiceImpl(CustomerShareRideRepoImpl rideSharingRepo) {
        this.rideSharingRepo = rideSharingRepo;
    }

    @Override
    public Optional<Page> GetPage(Integer redirectedData) {
        try {
            List<CustomerShareRideComposite> resultList = rideSharingRepo.getAllData(redirectedData);
            Optional<Page> page = Optional.of(CustomerShareRidePage.builder()
                    .shareRideCompositeList(resultList)
                    .build());

            resultList.forEach(System.out::println);

            return page;

        }
        catch (DataAccessException err){
            this.HandleException(err);

        }
        return Optional.empty();
    }

    @Override
    public ErrorWithMessage ProcessForm(CustomerShareRideForm form, Integer additional) {
        try{
            this.rideSharingRepo.Insert(CustomerShareRide.from(form, additional));
            return ErrorWithMessage.builder().error(false).build();
        }
        catch (DataAccessException | SQLException err){
            this.HandleException(err);
        }

        return ErrorWithMessage.builder().error(true).build();
    }
}
