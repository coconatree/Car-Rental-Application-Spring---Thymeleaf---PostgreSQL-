package com.car_rental.Configuration;

import com.car_rental.Model.DatabaseModel.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class AuthenticationFacadeImpl implements AuthenticationFacadeI {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationFacadeImpl.class.toString());

    @Override
    public Optional<AuthUser> getAuthenticatedUser() {
        try {
            return Optional.of((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        catch (Exception err) {
            LOGGER.log(Level.INFO, "No user found for the page: " + err.getMessage());
        }
        return Optional.empty();
    }
}
