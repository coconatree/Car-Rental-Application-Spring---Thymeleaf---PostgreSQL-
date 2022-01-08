package com.car_rental.Interface.RepositoryI;

import com.car_rental.Logic.RepositoryLogic.Insert;
import com.car_rental.Logic.RepositoryLogic.Update;
import com.car_rental.Model.CompositeModel.ReservationWithCar;
import com.car_rental.Model.DatabaseModel.Reservation;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepoI extends Insert<Reservation>, Update<Reservation> {

    List<ReservationWithCar> selectResWithCarByUserId(Integer user_id) throws DataAccessException;
    List<Reservation> selectResByUserId(Integer user_id) throws DataAccessException;

    List<Reservation> getUnhandledReservations(Integer user_id) throws DataAccessException;

    Reservation updateEndingBranch(Integer branchId, Reservation data) throws DataAccessException;

    List<Reservation> getFinishedReservations(Integer user_id) throws DataAccessException;

    Reservation SelectResById(Integer reservationId) throws DataAccessException;
    Reservation UpdatePayment(Integer id, Reservation data) throws DataAccessException, SQLException;

    List<Reservation> GetAllSharedReservationFromUserId(Integer userId) throws SQLException;
}
