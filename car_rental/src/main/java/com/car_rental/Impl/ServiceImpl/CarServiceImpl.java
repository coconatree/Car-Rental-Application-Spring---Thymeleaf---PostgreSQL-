package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Impl.RepositoryImpl.BranchRepoImpl;
import com.car_rental.Impl.RepositoryImpl.CarRepoImpl;
import com.car_rental.Impl.RepositoryImpl.ReviewRepoImpl;
import com.car_rental.Interface.RepositoryI.BranchRepoI;
import com.car_rental.Interface.RepositoryI.CarBranchRepoI;
import com.car_rental.Interface.RepositoryI.CarRepoI;
import com.car_rental.Interface.RepositoryI.ReviewRepoI;
import com.car_rental.Interface.ServiceI.CarServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.CompositeModel.CarAndReviewAmount;
import com.car_rental.Model.CompositeModel.CheckBoxValueAndIsChecked;
import com.car_rental.Model.DatabaseModel.Car;
import com.car_rental.Model.FormModel.CarFilterForm;
import com.car_rental.Model.PageModel.CarPage;
import com.car_rental.Model.RedirectedDataModel.CarFilterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl extends BaseService implements CarServiceI {

    private final CarRepoI carRepo;
    private final BranchRepoI branchRepo;

    @Autowired
    CarServiceImpl(CarRepoImpl carRepo, BranchRepoImpl branchRepo, ReviewRepoImpl reviewRepo, CarBranchRepoI carBranchRepo) {
        this.carRepo = carRepo;
        this.branchRepo = branchRepo;
    }

    @Override
    public Optional<Page> GetPage(CarFilterData redirectedData) {
        try {

            List<CarAndReviewAmount> carList = carRepo.SelectFilteredWithBranchId(
                    redirectedData.getSelectedModels(),
                    redirectedData.getSelectedBrands(),
                    redirectedData.getSelectedGearTypes(),
                    redirectedData.getPreferredBranchId()
            );

            boolean isReservationList = true;

            if (carList.isEmpty()) {
                carList = carRepo.SelectFiltered(
                        redirectedData.getSelectedModels(),
                        redirectedData.getSelectedBrands(),
                        redirectedData.getSelectedGearTypes()
                );
                isReservationList = false;
            }

            return Optional.of(CarPage.builder()
                    .branches(
                            branchRepo.SelectAllIdAndName()
                    )
                    .preferredBranchId(
                            redirectedData.getPreferredBranchId()
                    )
                    .carsAndReviewAmount(carList)
                    .brands(CreateOptionsWithCheckedAttr(
                            this.carRepo.SelectAllBrand(), redirectedData.getSelectedBrands())
                    )
                    .models(CreateOptionsWithCheckedAttr(
                            this.carRepo.SelectAllModel(), redirectedData.getSelectedModels())
                    )
                    .gears(CreateOptionsWithCheckedAttr(
                            this.carRepo.SelectAllGearTypes(), redirectedData.getSelectedGearTypes())
                    )
                    .isReservationList(isReservationList)
                    .isRequestList(!isReservationList)
                    .build()
            );
        }
        catch (DataAccessException err) {
            this.HandleException(err);
            return Optional.empty();
        }
    }

    @Override
    public ErrorWithMessage ProcessForm(CarFilterForm form, Integer additional) {
        return ErrorWithMessage.builder().error(false).build();
    }

    private List<CheckBoxValueAndIsChecked> CreateOptionsWithCheckedAttr(List<String> allOfTheData, List<String> checkedData) {
        return allOfTheData.stream().map(e -> {
            if (checkedData != null && checkedData.contains(e)) {
                return new CheckBoxValueAndIsChecked(e, true);
            }
            return new CheckBoxValueAndIsChecked(e, false);
        }).collect(Collectors.toList());
    }
}
