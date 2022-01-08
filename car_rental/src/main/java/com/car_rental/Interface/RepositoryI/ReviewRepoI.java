package com.car_rental.Interface.RepositoryI;

import com.car_rental.Model.CompositeModel.ReviewWithDate;
import com.car_rental.Model.DatabaseModel.Review;
import org.springframework.dao.DataAccessException;
import org.springframework.data.relational.core.sql.In;

import java.sql.SQLException;
import java.util.List;

public interface ReviewRepoI {
    List<ReviewWithDate> GetReviewWhereCarId(int carId) throws DataAccessException, SQLException;
    Integer GetReviewAmount(int carId);
    boolean checkIfReservationHasReview(Integer reservationId);
    void Insert(Integer resId, String content, Integer star) throws DataAccessException, SQLException;
}
