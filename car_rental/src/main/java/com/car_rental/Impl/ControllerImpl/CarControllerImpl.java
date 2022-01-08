package com.car_rental.Impl.ControllerImpl;

import com.car_rental.Configuration.Utility;
import com.car_rental.Impl.ServiceImpl.CarServiceImpl;
import com.car_rental.Interface.ControllerI.CarControllerI;
import com.car_rental.Interface.ServiceI.CarServiceI;
import com.car_rental.Logic.ControllerLogic.BaseController;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Model.CompositeModel.SelectedCheckBoxes;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.FormModel.CarFilterForm;
import com.car_rental.Model.RedirectedDataModel.CarFilterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/car")
public class CarControllerImpl extends BaseController implements CarControllerI {

    private final CarServiceI service;

    @Autowired
    public CarControllerImpl(CarServiceImpl service) {
        this.service = service;
    }

    @Override
    @GetMapping("")
    public ModelAndView Redirected(
            HttpServletRequest request,
            ModelMap modelMap,
            HttpSession session
    ) {


        Optional<Integer> selectedBranchId = Utility.extractParameterAsInteger(request, "branchId");

        if (selectedBranchId.isEmpty()) {
            return this.SendError("Selected branch can't be empty", "/", modelMap);
        }

        Optional<SelectedCheckBoxes> filterSessionData = this.ExtractFromSessionFilterOptions(session);

        // If it doesn't exist on the session than we initialize it using the default values
        if (filterSessionData.isEmpty()) {
            filterSessionData = Optional.of(SelectedCheckBoxes.builder()
                    .selectedModels(Collections.emptyList())
                    .selectedBrands(Collections.emptyList())
                    .selectedGearTypes(Collections.emptyList())
                    .selectedBranch(selectedBranchId.get())
                    .build());
        }

        if (filterSessionData.get().getSelectedModels() == null)
            filterSessionData.get().setSelectedModels(Collections.emptyList());

        if (filterSessionData.get().getSelectedBrands() == null)
            filterSessionData.get().setSelectedBrands(Collections.emptyList());

        if (filterSessionData.get().getSelectedGearTypes() == null)
            filterSessionData.get().setSelectedGearTypes(Collections.emptyList());

        Optional<Page> page = this.service.GetPage(CarFilterData.builder()
                        .selectedModels(filterSessionData.get().getSelectedModels())
                        .selectedBrands(filterSessionData.get().getSelectedBrands())
                        .selectedGearTypes(filterSessionData.get().getSelectedGearTypes())
                        .preferredBranchId(filterSessionData.get().getSelectedBranch())
                        .preferredBranchId(selectedBranchId.get())
                .build());

        if(page.isEmpty()) {
            return this.SendError("/", modelMap);
        }

        Optional<AuthUser> user = this.ExtractFromSessionAuthUser(session);
        user.ifPresent(authUser -> page.get().setUser(authUser));

        page.get().Link(modelMap);

        return new ModelAndView("pages/car", modelMap);
    }

    @Override
    @PostMapping("")
    public ModelAndView Form(
            HttpServletRequest request,
            ModelMap modelMap,
            CarFilterForm formData,
            BindingResult bindingResult,
            HttpSession session
    ) {

        if (bindingResult.hasErrors()) {
            return this.SendError("Error binding the form", "/", modelMap);
        }

        ErrorWithMessage formError = formData.ValidateForm();

        if (formError.isError()) {
            return this.SendError(formError.getMessage(), "/car?carId=" + formData.getPreferredBranch(), modelMap);
        }

        ErrorWithMessage serviceError = this.service.ProcessForm(formData, 0);

        if (serviceError.isError()) {
            return this.SendError(serviceError.getMessage(), "/car?carId=" + formData.getPreferredBranch(), modelMap);
        }

        modelMap.addAttribute("branchId", formData.getPreferredBranch());

        SelectedCheckBoxes selectedCheckBoxes = SelectedCheckBoxes.builder()
                .selectedModels(formData.getModels())
                .selectedBrands(formData.getBrands())
                .selectedGearTypes(formData.getGears())
                .selectedBranch(formData.getPreferredBranch())
                .build();

        session.setAttribute("filterOptions", selectedCheckBoxes);

        return new ModelAndView("redirect:/car", modelMap);
    }
}
