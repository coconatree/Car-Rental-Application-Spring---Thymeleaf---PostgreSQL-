package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Impl.RepositoryImpl.ReviewRepoImpl;
import com.car_rental.Interface.RepositoryI.ReviewRepoI;
import com.car_rental.Interface.ServiceI.ReviewServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.FormModel.ReviewForm;
import com.car_rental.Model.PageModel.ReviewPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class ReviewServiceImpl extends BaseService implements ReviewServiceI {

    private final ReviewRepoI reviewRepo;

    @Autowired
    public ReviewServiceImpl(ReviewRepoImpl reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Override
    public Optional<Page> GetPage(Integer carId) {
        try {
            return Optional.of(ReviewPage.builder()
                    .reviewList(reviewRepo.GetReviewWhereCarId(carId))
                    .build());
        }
        catch (DataAccessException | SQLException err){
            this.HandleException(err);
        }

        return Optional.empty();
    }
}