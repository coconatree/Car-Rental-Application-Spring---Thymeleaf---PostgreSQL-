package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Getter
@Setter
@ToString
@Component
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SuccessPage extends Page {
    private String message;
    private String afterURL;

    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("message", this.message);
        model.addAttribute("afterURL", this.afterURL);
    }
}
