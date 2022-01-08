package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Impl.RepositoryImpl.*;
import com.car_rental.Impl.ServiceImpl.UtilityServiceImpl.CarAvailableService;
import com.car_rental.Interface.RepositoryI.*;
import com.car_rental.Interface.ServiceI.ReservationServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.DatabaseModel.*;
import com.car_rental.Model.FormModel.ReservationForm;
import com.car_rental.Model.PageModel.ReservationPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class ReservationServiceImpl extends BaseService  implements ReservationServiceI {

    private final CarReservationRepoI carReservationRepo;
    private final ReservationRepoI reservationRepo;
    private final BranchRepoI branchRepo;
    private final CarBranchRepoI carBranchRepo;
    private final CarRepoI carRepo;
    private final InsuranceRepoI insuranceRepo;
    private final SharedRepoI sharedRepo;

    private final CarAvailableService carAvailableService;


    private static final int RESERVATION_STATUS_DEFAULT = 100;

    @Autowired
    public ReservationServiceImpl(
            CarReservationRepoImpl carReservationRepo,
            ReservationRepoImpl reservationRepo,
            BranchRepoImpl branchRepo,
            CarBranchRepoI carBranchRepo,
            CarRepoImpl carRepo,
            InsuranceRepoImpl insuranceRepo,
            SharedRepoImpl sharedRepo,
            CarAvailableService carAvailableService
    ) {
        this.carReservationRepo = carReservationRepo;
        this.reservationRepo = reservationRepo;
        this.branchRepo = branchRepo;
        this.carBranchRepo = carBranchRepo;
        this.carRepo = carRepo;
        this.insuranceRepo = insuranceRepo;
        this.sharedRepo = sharedRepo;
        this.carAvailableService = carAvailableService;
    }

    @Override
    public Optional<Page> GetPage(Integer carId) {
        try {
            return Optional.of(ReservationPage.builder()
                    .branch(this.branchRepo.SelectBranchWhereCarIsOn(carId))
                    .branches(this.branchRepo.SelectAllIdAndName())
                    .build()
            );
        }
        catch (Exception err){
            this.HandleException(err);
            return Optional.empty();
        }
    }

    @Override
    public ErrorWithMessage ProcessForm(ReservationForm form, Integer customerId) {
        try {

            // Validate that the given car is not reserved or requested
            Integer carId = form.getCarId();
            boolean valid = this.carBranchRepo.CheckIfCarInBranch(form.getDepartFrom(), carId);

            if (!valid) {
                return ErrorWithMessage.builder().error(true).message("Car is not in the selected branch").build();
            }

            // Calculating the total cost here

            int numOfDaysBetween = Period.between(
                    this.convertToLocalDate(form.getStartingDate()),
                    this.convertToLocalDate(form.getEndingDate())
            ).getDays();
            
            // Getting the car for retrieving its price
            Car car = this.carRepo.Select(carId);

            double totalCost = numOfDaysBetween * car.getPricePerDay();

            Reservation reservation = Reservation.from(
                    form,
                    totalCost,
                    RESERVATION_STATUS_DEFAULT,
                    customerId
            );

            // Checking if the car is on available
            ErrorWithMessage carAvailableError = this.carAvailableService.IsCarAvailable(form.getCarId());
            if (carAvailableError.isError()) {
                return carAvailableError;
            }

            // Creating a record of reservation
            reservation = this.reservationRepo.Insert(reservation);

            if (form.isHasInsurance()) {
                this.insuranceRepo.Insert(Insurance.builder()
                        .reservationId(reservation.getReservationId())
                        .startingDate(form.getStartingDate())
                        .insuranceFee(car.getPrice() / 10)
                        .build());
            }

            // System.out.println("Insurance fee : " + car.getPrice() / 10);

            this.carReservationRepo.Insert(CarReservation.builder()
                    .reservationId(reservation.getReservationId())
                    .carId(car.getId())
                    .build());

            this.sharedRepo.Insert(SharedRide.builder()
                    .reservationId(reservation.getReservationId())
                    .build());

            // System.out.println("Price for the reservation : " + totalCost);

            return ErrorWithMessage.builder().error(false).build();
        }
        catch (Exception err) {
            this.HandleException(err);
        }
        return ErrorWithMessage.builder().error(true).message("Error processing the reservation form").build();
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
