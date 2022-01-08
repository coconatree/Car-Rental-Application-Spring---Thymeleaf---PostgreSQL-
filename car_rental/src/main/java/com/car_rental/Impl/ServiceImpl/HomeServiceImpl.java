package com.car_rental.Impl.ServiceImpl;

import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.PageModel.HomePage;
import com.car_rental.Interface.ServiceI.HomeServiceI;
import com.car_rental.Model.FormModel.BranchSelectionForm;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Impl.RepositoryImpl.BranchRepoImpl;
import com.car_rental.Interface.RepositoryI.BranchRepoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HomeServiceImpl extends BaseService implements HomeServiceI {
    private final BranchRepoI branchRepo;

    @Autowired
    public HomeServiceImpl(BranchRepoImpl branchRepo) {
        this.branchRepo = branchRepo;
    }

    @Override
    public Optional<Page> GetPage() {
        try {
            return Optional.of(HomePage.builder()
                    .branches(this.branchRepo.SelectAllIdAndName())
                    .build());

        }
        catch (DataAccessException err) {
            this.HandleException(err);
            return Optional.empty();
        }
    }

    @Override
    public ErrorWithMessage ProcessForm(BranchSelectionForm form, Integer additional) {
        return ErrorWithMessage.builder().error(false).build();
    }
}
