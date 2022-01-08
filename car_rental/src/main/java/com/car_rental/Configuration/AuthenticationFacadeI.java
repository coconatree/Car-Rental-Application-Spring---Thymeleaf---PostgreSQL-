package com.car_rental.Configuration;

import com.car_rental.Model.DatabaseModel.AuthUser;

import java.util.Optional;

public interface AuthenticationFacadeI {
    Optional<AuthUser> getAuthenticatedUser();
}

