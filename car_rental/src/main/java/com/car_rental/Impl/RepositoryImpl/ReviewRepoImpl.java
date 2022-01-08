package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.ReviewRepoI;
import com.car_rental.Model.CompositeModel.ReviewWithDate;
import com.car_rental.Model.DatabaseModel.Review;
import com.car_rental.Model.PrimitiveRowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReviewRepoImpl implements ReviewRepoI {

    private final JdbcTemplate template;

    @Autowired
    public ReviewRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<ReviewWithDate> GetReviewWhereCarId(int carId) throws DataAccessException, SQLException {

        final String REVIEW_BY_CAR_ID_QUERY = "SELECT rev.review_id, rev.content, rev.star, res.reservation_id, res.starting_date, res.ending_date FROM  review AS rev, reservation AS res, car_reservation AS car_res\n" +
                "WHERE rev.reservation_id = res.reservation_id\n" +
                "AND res.reservation_id = car_res.reservation_id\n" +
                "AND car_res.car_id = " + carId + ";";

        return this.template.query(REVIEW_BY_CAR_ID_QUERY, ReviewWithDate.GetRowMapper());
    }

    @Override
    public Integer GetReviewAmount(int carId) {

        final String REVIEW_AMOUNT_QUERY = "SELECT count(rev.review_id) FROM review AS rev, reservation AS res, car_reservation AS car_res \n" +
                "WHERE rev.reservation_id = res.reservation_id   \n" +
                "AND car_res.reservation_id = res.reservation_id \n" +
                "AND car_res.car_id = " + carId + ";";

        return this.template.queryForObject(REVIEW_AMOUNT_QUERY, PrimitiveRowMappers.GetIntegerRowMapper());
    }

    @Override
    public boolean checkIfReservationHasReview(Integer reservationId) {
        return this.template.queryForObject("SELECT EXISTS (SELECT * FROM review AS r WHERE r.reservation_id = " + reservationId + ")", PrimitiveRowMappers.GetBooleanRowMapper());
    }

    @Override
    public void Insert(Integer resId, String content, Integer star) throws DataAccessException, SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO review (reservation_id, content, star) VALUES (?, ?, ?);", new String[]{"review_id"});
            ps.setInt(1, resId);
            ps.setString(2, content);
            ps.setInt(3, star);
            return ps;
        }, keyHolder);
    }
}
