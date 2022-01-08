package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.DatabaseModel.Customer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;


@Getter
@Setter
@ToString
@Component
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CustomerRegistrationPage extends Page {
    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("customerForm", Customer.builder().build());
    }
}
