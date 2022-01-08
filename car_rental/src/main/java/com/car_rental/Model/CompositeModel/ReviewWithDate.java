package com.car_rental.Model.CompositeModel;

import com.car_rental.Model.DatabaseModel.Review;
import lombok.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class ReviewWithDate {

    private Date startingDate;
    private Date endingDate;

    private Review review;

    public static RowMapper<ReviewWithDate> GetRowMapper() {
        return new ReviewAndDateRowMapper();
    }

    private static class ReviewAndDateRowMapper implements RowMapper<ReviewWithDate> {
        @Nullable
        @Override
        public ReviewWithDate mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ReviewWithDate.builder()
                    .review(Review.builder()
                            .reviewId(rs.getInt("review_id"))
                            .reservationId(rs.getInt("reservation_id"))
                            .content(rs.getString("content"))
                            .star(rs.getInt("star"))
                            .build())
                    .startingDate(rs.getDate("starting_date"))
                    .endingDate(rs.getDate("ending_date"))
                    .build();
        }
    }

}
