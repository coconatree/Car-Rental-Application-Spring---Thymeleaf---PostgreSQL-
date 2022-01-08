package com.car_rental.Configuration;

import com.car_rental.Model.DatabaseModel.User;
import com.car_rental.Model.PageModel.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;

@Configuration
public class BeanConfig {

    @Bean
    public String getString() {
        return "";
    }

    @Bean
    public Integer getInteger() {
        return 0;
    }

    @Bean
    public Double getDouble() {
        return 0.0;
    }

    @Bean
    public Float getFloat(){return 0.0f;}

    @Bean
    public boolean getBoolean() {
        return false;
    }

    @Bean
    public Date getDate() {
        return new Date(System.currentTimeMillis());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CarPage.CarPageBuilder<?, ?> getCarListPageBuilder() {
        return CarPage.builder();
    }

    @Bean
    public User.UserBuilder<?, ?> getUserBuilder() {
        return User.builder();
    }

    @Bean
    public PayPage.PayPageBuilder<?, ?> getPayPageBuilder(){ return PayPage.builder(); }

    @Bean
    public CarRequestPage.CarRequestPageBuilder<?, ?> getMakeRequestPageBuilder() {
        return CarRequestPage.builder();
    }

    @Bean
    public HomePage.HomePageBuilder<?, ?> getBranchSelectionPageBuilder() {
        return HomePage.builder();
    }

    @Bean
    public ReturnCarPage.ReturnCarPageBuilder<?, ?> getReturnCarPageBuilder() { return ReturnCarPage.builder(); }

    @Bean
    public CustomerRegistrationPage.CustomerRegistrationPageBuilder<?, ?> getCustomerRegistrationPageBuilder() {
        return CustomerRegistrationPage.builder();
    }

    @Bean
    public UserRidesPage.UserRidesPageBuilder<?, ?> getUserRidesPageBuilder() {
        return UserRidesPage.builder();
    }

    @Bean
    public UserReservationsPage.UserReservationsPageBuilder<?, ?> getUserReservationsPageBuilder() { return UserReservationsPage.builder();}

    @Bean
    public UserInfoPage.UserInfoPageBuilder<?, ?> getUserInfoPageBuilder()
    {
        return UserInfoPage.builder();
    }

    @Bean
    public ChangePasswordPage.ChangePasswordPageBuilder<?, ?> getChangePasswordPageBuilder() { return ChangePasswordPage.builder(); }
    @Bean
    public ErrorPage.ErrorPageBuilder<?, ?> getErrorPageBuilder() {
        return ErrorPage.builder();
    }

    @Bean
    public SuccessPage.SuccessPageBuilder<?, ?> getSuccessPageBuilder() {
        return SuccessPage.builder();
    }

    @Bean
    public LoginPage.LoginPageBuilder<?, ?> getLoginPageBuilder() {
        return LoginPage.builder();
    }

    @Bean
    public ProfilePage.ProfilePageBuilder<?, ?> getProfilePageBuilder() { return ProfilePage.builder(); }

    @Bean
    public ReservationCheckPage.ReservationCheckPageBuilder<?, ?> getReservationCheckPageBuilder() { return ReservationCheckPage.builder(); }

    @Bean
    public RequestCheckPage.RequestCheckPageBuilder<?, ?> getRequestCheckPageBuilder() { return RequestCheckPage.builder(); }

    @Bean
    public UserReviewPage.UserReviewPageBuilder<?, ?> getUserReviewPageBuilder() { return  UserReviewPage.builder(); }

    @Bean
    public ReservationPage.ReservationPageBuilder<?, ?> getReservationPageBuilder() {return ReservationPage.builder();}

    @Bean
    public ReviewPage.ReviewPageBuilder<?, ?> getReviewPageBuilder() {
        return ReviewPage.builder();
    }

    @Bean
    public LandingPageEmployee.LandingPageEmployeeBuilder<?, ?> getEmployeeLandingPageBuilder() {
        return LandingPageEmployee.builder();
    }

    @Bean
    public LandingPageManager.LandingPageManagerBuilder<?, ?> getManagerLandingPageBuilder() {
        return LandingPageManager.builder();
    }

    @Bean
    public EmployeeHirePage.EmployeeHirePageBuilder<?,?> getEmployeeHireBuilder(){
        return EmployeeHirePage.builder();
    }

    @Bean
    public CustomerShareRidePage.CustomerShareRidePageBuilder<?,?> getCustomerShareRideBuilder(){return CustomerShareRidePage.builder(); }
}
