package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.FormModel.ReturnCarForm;
import com.car_rental.Model.FormModel.UserReviewForm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Getter
@Setter
@ToString
@Component
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserReviewPage extends Page {

    private Integer resId;
    @Override
    protected void Initialize(ModelMap model) {
        model.addAttribute("userReviewForm", UserReviewForm.builder().build());
        model.addAttribute("resId", resId);
    }
}
