package com.car_rental.Logic.ServiceLogic;

import com.car_rental.Logic.ModelLogic.Page;

import java.util.Optional;

public interface PageServiceI {

    /**
     Page Service

     This interface is used for creating a service method for the corresponding PageController's
     'Page()' method. If necessary it connects to repositories and retries the data. It handles
     the errors and builds a Page 'CarListPage'.

     @return Page Returns the built page.
     */

    Optional<Page> GetPage();
}
