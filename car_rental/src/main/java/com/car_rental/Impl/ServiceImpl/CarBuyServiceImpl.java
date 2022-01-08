package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Impl.RepositoryImpl.BranchRepoImpl;
import com.car_rental.Impl.RepositoryImpl.CarBranchRepoImpl;
import com.car_rental.Impl.RepositoryImpl.ManagerRepoImpl;
import com.car_rental.Interface.RepositoryI.BranchRepoI;
import com.car_rental.Interface.RepositoryI.CarBranchRepoI;
import com.car_rental.Interface.RepositoryI.ManagerRepoI;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.FormModel.CarBuyForm;
import com.car_rental.Interface.ServiceI.CarBuyServiceI;
import com.car_rental.Model.DatabaseModel.Car;
import com.car_rental.Model.PageModel.CarBuyPage;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Impl.RepositoryImpl.CarRepoImpl;
import com.car_rental.Interface.RepositoryI.CarRepoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarBuyServiceImpl extends BaseService implements CarBuyServiceI {

    private final CarRepoI carRepo;
    private final CarBranchRepoI carBranchRepo;
    private final ManagerRepoI managerRepo;
    private final BranchRepoI branchRepo;

    @Autowired
    public CarBuyServiceImpl(
            CarRepoImpl carRepo,
            CarBranchRepoImpl carBranchRepo,
            ManagerRepoImpl managerRepo,
            BranchRepoImpl branchRepo
    )
    {
        this.carRepo = carRepo;
        this.carBranchRepo = carBranchRepo;
        this.managerRepo = managerRepo;
        this.branchRepo = branchRepo;
    }

    @Override
    public Optional<Page> GetPage() {
        return Optional.of(
                CarBuyPage.builder()
                        .build()
        );
    }

    @Override
    public ErrorWithMessage ProcessForm(CarBuyForm form, Integer managerId) {
        try {
            // Adding the car
            Car car = this.carRepo.Insert(Car.from(form, managerId));

            // Adding the car to the car branch table
            this.carBranchRepo.Insert(car.getId(), managerRepo.SelectBranchFromManagerId(managerId));

            int branchId = this.managerRepo.SelectBranchFromManagerId(managerId);

            // Increasing the spending of the branch
            this.branchRepo.IncreaseSpending(branchId, car.getPrice());

            return ErrorWithMessage.builder().error(false).build();
        }
        catch (Exception err) {
            this.HandleException(err);
            return ErrorWithMessage.builder().error(true).message("Error processing car buy form").build();
        }
    }
}
