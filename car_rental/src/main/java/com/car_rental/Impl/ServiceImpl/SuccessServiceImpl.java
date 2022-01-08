package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Interface.ServiceI.SuccessServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.PageModel.SuccessPage;
import com.car_rental.Model.RedirectedDataModel.SuccessData;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SuccessServiceImpl implements SuccessServiceI {

    private static final SuccessPage DEFAULT_SUCCESS_PAGE = SuccessPage.builder()
            .message("Successful")
            .afterURL("/")
            .build();

    @Override
    public Optional<Page> GetPage(SuccessData redirectedData) {
        try {
            return Optional.of(SuccessPage.builder()
                    .message(redirectedData.getMessage())
                    .afterURL(redirectedData.getAfterURL())
                    .build()
            );
        }
        catch (Exception err) {
            err.printStackTrace();
            return Optional.of(DEFAULT_SUCCESS_PAGE);
        }
    }
}
