package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Impl.RepositoryImpl.EmployeeRepoImpl;
import com.car_rental.Interface.RepositoryI.EmployeeRepoI;
import com.car_rental.Interface.ServiceI.EmployeeHireServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.Employee;
import com.car_rental.Model.FormModel.EmployeeHireForm;
import com.car_rental.Model.PageModel.EmployeeHirePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class EmployeeHireServiceImpl extends BaseService implements EmployeeHireServiceI {

    private final EmployeeRepoImpl employeeRepo;

    @Autowired
    public EmployeeHireServiceImpl(EmployeeRepoImpl employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public Optional<Page> GetPage(Integer managerId) {
        return Optional.of(EmployeeHirePage.builder().build());
    }

    @Override
    public ErrorWithMessage ProcessForm(EmployeeHireForm form, Integer managerId) {
        try {
            this.employeeRepo.Insert(Employee.from(form), managerId);
            return ErrorWithMessage.builder().error(false).build();
        }
        catch (DataAccessException | SQLException err) {
            this.HandleException(err);
        }
        return ErrorWithMessage.builder().error(true).message("error processing the form").build();
    }


}
