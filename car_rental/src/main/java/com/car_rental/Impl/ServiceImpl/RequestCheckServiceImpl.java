package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Interface.ServiceI.RequestCheckServiceI;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Model.PageModel.RequestCheckPage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestCheckServiceImpl extends BaseService implements RequestCheckServiceI {
    @Override
    public Optional<Page> GetPage() {
        try {
            return Optional.of(RequestCheckPage.builder()
                    .build());
        }
        catch (Exception err) {
            this.HandleException(err);
            return Optional.empty();
        }
    }
}
