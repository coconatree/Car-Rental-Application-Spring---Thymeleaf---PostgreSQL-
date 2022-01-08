package com.car_rental.Logic.ServiceLogic;

import com.car_rental.Logic.ModelLogic.Page;

import java.util.Optional;

public interface RedirectedServiceI<T> {
    Optional<Page> GetPage(T redirectedData);
}
