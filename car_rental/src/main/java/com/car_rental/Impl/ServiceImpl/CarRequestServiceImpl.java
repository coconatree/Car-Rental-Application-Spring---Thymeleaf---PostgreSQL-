package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Impl.RepositoryImpl.BranchRepoImpl;
import com.car_rental.Impl.RepositoryImpl.CarBranchRepoImpl;
import com.car_rental.Impl.RepositoryImpl.RequestCarRepoImpl;
import com.car_rental.Impl.RepositoryImpl.RequestRepoImpl;
import com.car_rental.Impl.ServiceImpl.UtilityServiceImpl.CarAvailableService;
import com.car_rental.Interface.RepositoryI.BranchRepoI;
import com.car_rental.Interface.RepositoryI.CarBranchRepoI;
import com.car_rental.Interface.RepositoryI.RequestCarRepoI;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.Request;
import com.car_rental.Model.FormModel.CarRequestForm;
import com.car_rental.Model.PageModel.CarRequestPage;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Interface.RepositoryI.RequestRepoI;
import com.car_rental.Interface.ServiceI.CarRequestServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CarRequestServiceImpl extends BaseService implements CarRequestServiceI {

    private final BranchRepoI      branchRepo;
    private final RequestRepoI    requestRepo;
    private final CarBranchRepoI carBranchRepo;
    private final RequestCarRepoI requestCarRepo;

    private final CarAvailableService carAvailableService;

    @Autowired
    public CarRequestServiceImpl(
            BranchRepoImpl branchRepo,
            RequestRepoImpl requestRepo,
            RequestCarRepoImpl requestCarRepo,
            CarBranchRepoImpl carBranchRepo,
            CarAvailableService carAvailableService
    ) {
        this.branchRepo = branchRepo;
        this.requestRepo = requestRepo;
        this.carBranchRepo = carBranchRepo;
        this.requestCarRepo = requestCarRepo;
        this.carAvailableService = carAvailableService;
    }

    @Override
    public Optional<Page> GetPage(Integer carId) {
        return Optional.of(CarRequestPage.builder()
                        .startingBranch(branchRepo.SelectBranchWhereCarIsOn(carId))
                        .branches(branchRepo.SelectAllIdAndName())
                .build()
        );
    }

    @Override
    public ErrorWithMessage ProcessForm(CarRequestForm form, Integer employeeId) {
        try {
            ErrorWithMessage errorWithMessage = this.carAvailableService.IsCarAvailable(form.getCarId());

            if (errorWithMessage.isError()) {
                return errorWithMessage;
            }

            // Checking if the car is on the given branch
            boolean valid = this.carBranchRepo.CheckIfCarInBranch(form.getStartingBranch(), form.getCarId());

            if (!valid) {
                return ErrorWithMessage.builder().error(true).message("Car is not on the branch : " + form.getStartingBranch()).build();
            }

            if (Objects.equals(form.getStartingBranch(), form.getEndingBranch())) {
                return ErrorWithMessage.builder().error(true).message("Can't request a car to the same branch").build();
            }

            // Adding the request to the request table
            Request request = this.requestRepo.Insert(Request.from(form));
            this.requestCarRepo.AddRequestCar(form.getCarId(), request.getReqId());


            return ErrorWithMessage.builder().error(false).build();
        }
        catch (Exception err) {
            this.HandleException(err);
            return ErrorWithMessage.builder().error(true).message("Error processing the car request form").build();
        }
    }
}
