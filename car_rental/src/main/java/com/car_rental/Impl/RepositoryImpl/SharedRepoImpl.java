package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.SharedRepoI;
import com.car_rental.Model.DatabaseModel.SharedRide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SharedRepoImpl implements SharedRepoI {

    private final JdbcTemplate template;

    @Autowired
    public SharedRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void Insert(SharedRide sharedRide) {
        this.template.update(
                "INSERT INTO shared_ride (reservation_id) VALUES (?)",
                sharedRide.getReservationId()
        );
    }
}
