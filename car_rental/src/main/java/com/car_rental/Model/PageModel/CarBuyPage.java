package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.DatabaseModel.Car;
import lombok.Builder;
import lombok.experimental.SuperBuilder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

@SuperBuilder
public class CarBuyPage extends Page {

    @Builder.Default
    private Car formCar = Car.builder().build();

    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("car", this.formCar);
    }
}
