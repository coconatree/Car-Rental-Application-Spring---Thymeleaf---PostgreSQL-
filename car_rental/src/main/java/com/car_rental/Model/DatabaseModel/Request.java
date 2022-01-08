package com.car_rental.Model.DatabaseModel;

import com.car_rental.Model.FormModel.CarRequestForm;
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
public class Request {

    private Integer reqId;
    public Integer startBranch;
    public Integer destBranch;
    public Integer status;

    public static Request from(CarRequestForm form) {
        return Request.builder()
                .startBranch(form.getStartingBranch())
                .destBranch(form.getEndingBranch())
                .status(100)
                .build();
    }

    public static ReqRowMapper GetRowMapper() {
        return new Request.ReqRowMapper();
    }

    private static class ReqRowMapper implements RowMapper<Request> {
        @Nullable
        @Override
        public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Request.builder()
                    .reqId(rs.getInt("request_id"))
                    .startBranch(rs.getInt("start_branch"))
                    .destBranch(rs.getInt("dest_branch"))
                    .status(rs.getInt("status"))
                    .build();
        }
    }
}
