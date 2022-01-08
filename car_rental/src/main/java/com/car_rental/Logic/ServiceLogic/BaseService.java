package com.car_rental.Logic.ServiceLogic;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseService {

    private static final Logger LOGGER = Logger.getLogger(BaseService.class.toString());

    public void HandleException(Exception err) {
        LOGGER.log(Level.WARNING, "Data Access Exception : ", err);
        err.printStackTrace();
    }
}
