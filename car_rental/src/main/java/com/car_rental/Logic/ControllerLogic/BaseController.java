package com.car_rental.Logic.ControllerLogic;

import com.car_rental.Logic.ModelLogic.Form;
import com.car_rental.Logic.ModelLogic.Page;
import com.car_rental.Logic.ServiceLogic.ErrorWithMessage;
import com.car_rental.Logic.ServiceLogic.FormServiceI;
import com.car_rental.Model.CompositeModel.SelectedCheckBoxes;
import com.car_rental.Model.DatabaseModel.AuthUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class BaseController {

    private final static Logger LOGGER = Logger.getLogger(BaseController.class.toString());

    /**
     @param service service for processing the form and checking for its errors
     @param form form for checking its errors
     @param br binding result for checking its errors
     @param <T> type parameter which extends Form for FormService and form
     @return returns an ErrorWithMessage which is just a boolean value together with a String message attribute

     */

    protected <T extends Form, K> ErrorWithMessage ValidateAndProcessForm(FormServiceI<T, K> service, T form, K additional, BindingResult br) {

        if (br.hasErrors()) {
            return ErrorWithMessage.builder().error(true).message("Error binding the form").build();
        }

        ErrorWithMessage formError = form.ValidateForm();

        if (formError.isError()) {
            return formError;
        }

        ErrorWithMessage serviceError = service.ProcessForm(form, additional);

        if (serviceError.isError()) {
            return serviceError;
        }

        return ErrorWithMessage.builder().error(false).build();
    }

    /**

     Redirects to the /errors to display an error with the given attributes;

     @param errorMessage    message to be displayed in the /errors page
     @param afterwardsPath the path to be displayed in the error page
     @param modelMap        modelMap for sending URL parameters

     @return returns a spring ModelAndView
     */

    public ModelAndView SendError(String errorMessage, String afterwardsPath, ModelMap modelMap) {

        modelMap.addAttribute("errorMessage", errorMessage);
        modelMap.addAttribute("afterwardsPath", afterwardsPath);

        return new ModelAndView("redirect:/errors", modelMap);
    }

    public ModelAndView SendError(String afterwardsPath, ModelMap modelMap) {

        modelMap.addAttribute("errorMessage", "Error retrieving the page Please try again later");
        modelMap.addAttribute("afterwardsPath", afterwardsPath);

        return new ModelAndView("redirect:/errors",  modelMap);
    }

    public void SetupUserAndPage(Optional<Page> page, Optional<AuthUser> user) {
        if (page.isPresent() && user.isPresent()) {
            page.get().setUser(user.get());
        }
    }

    protected Optional<Integer> ExtractFromSessionInteger(HttpSession session, String key) {
        try {
            return Optional.of((Integer) session.getAttribute(key));
        }
        catch (Exception err) {
            LOGGER.log(Level.INFO, "No integer value found with key : " + key + " found on the session");
        }
        return Optional.empty();
    }

    protected Optional<AuthUser> ExtractFromSessionAuthUser(HttpSession session) {
        try {
            return Optional.of((AuthUser) session.getAttribute("user"));
        }
        catch (Exception err) {
            LOGGER.log(Level.INFO, "No active user found");
        }
        return Optional.empty();
    }

    protected Optional<SelectedCheckBoxes> ExtractFromSessionFilterOptions(HttpSession session) {
        try {
            return Optional.of((SelectedCheckBoxes) session.getAttribute("filterOptions"));
        }
        catch (Exception err) {
            System.out.println("Error: " + err);
            LOGGER.log(Level.INFO, "No active car search filter");
        }
        return Optional.empty();
    }
}
