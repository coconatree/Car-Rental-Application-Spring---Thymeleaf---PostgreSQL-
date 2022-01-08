package com.car_rental.Impl.ControllerImpl.ErrorAndSuccessControllerImpl;

import com.car_rental.Configuration.Utility;
import com.car_rental.Impl.ServiceImpl.ErrorServiceImpl;
import com.car_rental.Interface.ControllerI.ErrorControllerI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Model.RedirectedDataModel.RedirectedErrorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class ErrorControllerImpl  extends BaseController implements ErrorControllerI {

    private final ErrorServiceImpl errorService;

    @Autowired
    public ErrorControllerImpl(ErrorServiceImpl errorService) {
        this.errorService = errorService;
    }

    @Override
    @RequestMapping("/errors")
    public ModelAndView Redirected(
            HttpServletRequest request,
            ModelMap modelMap,
             HttpSession session
    ) {

        Optional<String> errorMessage   = Utility.extractParameterAsString(request, "errorMessage");
        Optional<String> afterwardsPath = Utility.extractParameterAsString(request, "afterwardsPath");

        Optional<Page> page = this.errorService.GetPage(RedirectedErrorData.builder()
                .errorMessage(errorMessage.orElse("Something Happened"))
                .afterwardsPath(afterwardsPath.orElse(""))
                .build());

        if (page.isEmpty()) {
            page = this.errorService.GetDefaultErrorPage();
        }

        page.get().Link(modelMap);

        return new ModelAndView("pages/error", modelMap);
    }
}
