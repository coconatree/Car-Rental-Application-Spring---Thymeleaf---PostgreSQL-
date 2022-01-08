package com.car_rental.Logic.ServiceLogic;

import com.car_rental.Logic.ModelLogic.Form;

public interface FormServiceI<T extends Form, K> {

    /**
     Generic Page Service

     This interface is used for creating a service method for the corresponding PageController's
     'Form()' method. It connects to the needed repositories and processes the form data.
     It contains the form processing logic for T typed form. ('FormService<MakeCarReservationForm>')

     @return boolean Returns false if an error happens and true if the form is
     successfully processed.
     */

    ErrorWithMessage ProcessForm(T form, K additional);
}
