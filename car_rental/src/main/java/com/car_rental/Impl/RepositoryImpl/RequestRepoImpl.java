package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.RequestRepoI;
import com.car_rental.Model.DatabaseModel.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class RequestRepoImpl implements RequestRepoI {

    private final JdbcTemplate template;

    @Autowired
    public RequestRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    private static final String REQUEST_INSERTION_QUERY =
            "INSERT INTO request (start_branch, dest_branch, status) VALUES (?, ?, ?);";

    @Override
    public Request Insert(Request data) throws DataAccessException, SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(REQUEST_INSERTION_QUERY, new String[]{"request_id"});
            ps.setInt(1, data.getStartBranch());
            ps.setInt(2, data.getDestBranch());
            ps.setInt(3, data.getStatus());
            return ps;
        }, keyHolder);

        data.setReqId((Integer) keyHolder.getKey());
        return data;
    }

    @Override
    public List<Request> getUnhandledRequests(Integer user_id) throws DataAccessException {
        return (this.template.query(
                String.format("SELECT * FROM request R WHERE R.status = 100 AND (R.start_branch = " +
                        "((SELECT M.branch_id FROM manager M WHERE M.user_id = " +
                        "(SELECT E.manager_id FROM employee E WHERE E.user_id = %s))))", user_id),
                Request.GetRowMapper()
        ));
    }

    public void approveRequest(Optional<Integer> req_id, Integer user_id) throws DataAccessException {
        String SQL = String.format("UPDATE request SET status = 200 where request_id = %s",  req_id.get());
        this.template.update(SQL);
        SQL = String.format("UPDATE car_branch SET branch_id = (SELECT dest_branch FROM request WHERE request_id = %s) WHERE car_id = (SELECT car_id FROM request_car WHERE request_id = %s) AND " +
                "branch_id = (SELECT start_branch FROM request WHERE request_id = %s)", req_id.get(), req_id.get(), req_id.get());
        this.template.update(SQL);

        SQL = String.format("INSERT INTO employee_request(validated_by, request_id) VALUES (%s, %s)", user_id, req_id.get());
        this.template.update(SQL);
    }

    public void declineRequest(Optional<Integer> req_id, Integer user_id) throws DataAccessException {
        String SQL = String.format("UPDATE request SET status = 300 where request_id = %s",  req_id.get());
        this.template.update(SQL);
        SQL = String.format("INSERT INTO employee_request(employee_id, request_id) VALUES (%s, %s)", user_id, req_id.get());
        this.template.update(SQL);
    }




}
