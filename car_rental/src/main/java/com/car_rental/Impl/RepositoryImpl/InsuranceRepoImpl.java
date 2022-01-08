package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Interface.RepositoryI.InsuranceRepoI;
import com.car_rental.Model.DatabaseModel.Insurance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InsuranceRepoImpl implements InsuranceRepoI {

    private final JdbcTemplate template;

    @Autowired
    public InsuranceRepoImpl(JdbcTemplate template) {
        this.template = template;
    }

    public void Insert(Insurance insurance) {
        this.template.update(
                "INSERT INTO insurance (reservation_id, starting_date, ending_date, insurance_fee) VALUES (?, ?, ?, ?)",
                insurance.getReservationId(),
                insurance.getStartingDate(),
                insurance.getEndingDate(),
                insurance.getInsuranceFee()
        );
    }
}
