package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Interface.ServiceI.ErrorServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.PageModel.ErrorPage;
import com.car_rental.Model.RedirectedDataModel.RedirectedErrorData;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ErrorServiceImpl implements ErrorServiceI {

    @Override
    public Optional<Page> GetPage(RedirectedErrorData redirectedData) {
            return Optional.of(ErrorPage
                                            .builder()
                                            .errorMessage(redirectedData.getErrorMessage())
                                            .afterwardsPath(redirectedData.getAfterwardsPath())
                                            .build()
            );
    }

    public Optional<Page> GetDefaultErrorPage() {
        return Optional.of(ErrorPage
                                        .builder()
                                        .errorMessage("Bad Query")
                                        .afterwardsPath("")
                                        .build()
        );
    }
}
