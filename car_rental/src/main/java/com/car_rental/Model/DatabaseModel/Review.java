package com.car_rental.Model.DatabaseModel;

import lombok.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@Builder
@ToString
@Component
@EqualsAndHashCode
public class Review {
    private int      reviewId;
    private int      reservationId;
    private String content;
    private int      star;

    public static RowMapper<Review> GetRowMapper() {
        return new ReviewRowMapper();
    }

    private static class  ReviewRowMapper implements RowMapper<Review> {
        @Nullable
        @Override
        public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Review.builder()
                    .reviewId(rs.getInt("review_id"))
                    .reservationId(rs.getInt("reservation_id"))
                    .content(rs.getString("content"))
                    .star(rs.getInt("star"))
                    .build();
        }
    }
}
