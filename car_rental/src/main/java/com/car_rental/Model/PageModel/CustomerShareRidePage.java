package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.CompositeModel.CustomerShareRideComposite;
import com.car_rental.Model.FormModel.CustomerShareRideForm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.List;


@Getter
@Setter
@ToString
@Component
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CustomerShareRidePage extends Page {
    private List<CustomerShareRideComposite> shareRideCompositeList;


    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("share_rides",shareRideCompositeList);
        model.addAttribute("shareForm", CustomerShareRideForm.builder().build());
    }
}
