package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.CompositeModel.BranchIdAndName;
import com.car_rental.Model.CompositeModel.CarAndReviewAmount;
import com.car_rental.Model.CompositeModel.CheckBoxValueAndIsChecked;
import com.car_rental.Model.DatabaseModel.Car;
import com.car_rental.Model.FormModel.CarFilterForm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Component
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CarPage extends Page {

    private List<BranchIdAndName> branches;

    private int preferredBranchId;

    private List<CheckBoxValueAndIsChecked> models;
    private List<CheckBoxValueAndIsChecked> brands;
    private List<CheckBoxValueAndIsChecked> gears;

    private List<CarAndReviewAmount> carsAndReviewAmount;

    private boolean isReservationList;
    private boolean isRequestList;

    @Override
    protected void Initialize(ModelMap model) {

        model.addAttribute("branches", this.branches);

        model.addAttribute("isListEmpty", !this.carsAndReviewAmount.isEmpty());

        model.addAttribute("isReservationList", this.isReservationList);
        model.addAttribute("isRequestList", this.isRequestList);

        model.addAttribute("modelList",   this.models);
        model.addAttribute("brandList",   this.brands);
        model.addAttribute("gearList", this.gears);
        model.addAttribute("carsAndReviewAmount", this.carsAndReviewAmount);



        CarFilterForm form = CarFilterForm.builder()
                .models(this.models.stream()
                        .filter(CheckBoxValueAndIsChecked::isChecked)
                        .map(CheckBoxValueAndIsChecked::getValue).collect(Collectors.toList()))
                .brands(this.brands.stream()
                        .filter(CheckBoxValueAndIsChecked::isChecked)
                        .map(CheckBoxValueAndIsChecked::getValue).collect(Collectors.toList()))
                .gears(this.gears.stream()
                        .filter(CheckBoxValueAndIsChecked::isChecked)
                        .map(CheckBoxValueAndIsChecked::getValue).collect(Collectors.toList()))
                .preferredBranch(this.preferredBranchId)
                .build();

        model.addAttribute("carFilterForm", form);
    }
}
