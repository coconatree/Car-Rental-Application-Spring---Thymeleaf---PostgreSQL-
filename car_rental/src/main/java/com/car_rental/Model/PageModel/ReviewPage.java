package com.car_rental.Model.PageModel;

import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.CompositeModel.ReviewWithDate;
import com.car_rental.Model.DatabaseModel.Review;
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
public class ReviewPage extends Page {

    private List<ReviewWithDate> reviewList;
    private boolean hasReview;

    @Override
    protected void Initialize(ModelMap model) {

        this.hasReview = !this.reviewList.isEmpty();

        model.addAttribute("reviewList", this.reviewList);
        model.addAttribute("hasReview", this.hasReview);
    }
}
