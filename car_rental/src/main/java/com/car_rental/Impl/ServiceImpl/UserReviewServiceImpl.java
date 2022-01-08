package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Interface.RepositoryI.ReviewRepoI;
import com.car_rental.Interface.ServiceI.UserReviewServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.FormModel.UserReviewForm;
import com.car_rental.Model.PageModel.UserReviewPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserReviewServiceImpl extends BaseService implements UserReviewServiceI {

    private ReviewRepoI reviewRepo;

    @Autowired
    public UserReviewServiceImpl(ReviewRepoI reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Override
    public ErrorWithMessage ProcessForm(UserReviewForm form, Integer additional) {
        try {
            // Insert review to database
            this.reviewRepo.Insert(
                    form.getReservationId(),
                    form.getContent(),
                    form.getStar()
            );
            return ErrorWithMessage.builder().error(false).build();
        }
        catch (DataAccessException | SQLException err) {
            this.HandleException(err);
            return ErrorWithMessage.builder().error(true).message("Error processing the review submit form").build();
        }
    }

    @Override
    public Optional<Page> GetPage(Integer redirectedData) {
        try {
            return Optional.of(UserReviewPage.builder()
                    .build());
        }
        catch (DataAccessException err){
            this.HandleException(err);
        }

        return Optional.empty();
    }
}
