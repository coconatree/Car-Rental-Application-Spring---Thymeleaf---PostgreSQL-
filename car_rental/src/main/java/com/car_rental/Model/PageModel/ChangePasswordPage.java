package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.FormModel.ChangePasswordForm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Getter
@Setter
@ToString
@Component
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ChangePasswordPage extends Page {

    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("changePasswordForm", ChangePasswordForm.builder().build());
    }
}
