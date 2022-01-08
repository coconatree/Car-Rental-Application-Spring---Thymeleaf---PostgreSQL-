package com.car_rental.Configuration;

import com.car_rental.Model.DatabaseModel.AuthUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
    public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger LOGGER = Logger.getLogger(CustomAuthenticationSuccessHandler.class.toString());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();

        AuthUser authUser =  (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        response.setStatus(HttpServletResponse.SC_OK);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        session.setAttribute("userId", authUser.getId());
        session.setAttribute("user", authUser);

        if(!authorities.isEmpty()) {
            authorities.forEach(authority -> ConsumeAuthority(authority, session, response));
        }
        else {
            this.RedirectResponse(response, "/success");
        }
    }

    private <T extends GrantedAuthority> void ConsumeAuthority(T authority, HttpSession session, HttpServletResponse response) {

        if (authority.getAuthority().equals("CUSTOMER")) {
            session.setAttribute("role", UserRoles.CUSTOMER);
            this.RedirectResponse(response, "/");
        }
        else if (authority.getAuthority().equals("EMPLOYEE")) {
            session.setAttribute("role", UserRoles.EMPLOYEE);
            this.RedirectResponse(response, "/employee");
        }
        else if (authority.getAuthority().equals("MANAGER")) {
            session.setAttribute("role", UserRoles.MANAGER);
            this.RedirectResponse(response, "/manager");
        }
    }

    private void RedirectResponse(HttpServletResponse response, String path) {
        try {
            response.sendRedirect(path);
        }
        catch (IOException err) {
            LOGGER.log(Level.WARNING, "Error redirecting the response int Custom Authentication Success Handler");
        }
    }
}
