package com.car_rental.Logic.ModelLogic;

import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.DatabaseModel.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Component
@SuperBuilder
@EqualsAndHashCode
public abstract class Page implements Serializable {

    /**

     This is the parent class for all the page models. It holds the
     common attributes for the page models.

     */

    @Nullable
    private AuthUser user;
    private boolean isManager;
    private boolean isLoggedIn;
    private boolean isCustomer;

    /**
     This abstract method is used to pass the model to the subclass and
     let it add its data to the view's model.
     */

    protected abstract void Initialize(ModelMap model);

    /**
     Takes a model as a parameter and first initializes itself and then
     calls the subclass's initialize method.
     */

    private void initializeSelf(ModelMap model) {
        model.addAttribute("user", this.user);
        model.addAttribute("isLoggedIn", this.user != null);

        model.addAttribute("isCustomer", this.user != null &&
                this.user.getRole().equals("CUSTOMER"));
        model.addAttribute("isEmployee", this.user != null &&
                this.user.getRole().equals("EMPLOYEE"));
        model.addAttribute("isManager", this.user != null &&
                this.user.getRole().equals("MANAGER"));
        this.Initialize(model);
    }

    /**
     Takes a 'org.springframework.ui.Model' model and passes it to initialization function.
     This allows us to transfer our page models to the Spring's MVC.
     */

    public void Link(ModelMap model) {
        this.initializeSelf(model);
    }
}
