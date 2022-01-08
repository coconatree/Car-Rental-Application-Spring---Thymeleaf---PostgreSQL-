package com.car_rental.Configuration;

import com.car_rental.Impl.ServiceImpl.UtilityServiceImpl.AuthUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.car_rental.Configuration.UserRoles.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AuthUserServiceImpl   authUserService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, AuthUserServiceImpl authUserService) {
        this.passwordEncoder = passwordEncoder;
        this.authUserService  =  authUserService;
    }

    @Autowired
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // For us to be able to process forms we need the cors and csrf to be disabled !!!
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/register")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/car/**").hasAuthority(CUSTOMER.name())
                .antMatchers("/customer/**").hasAnyAuthority(CUSTOMER.name())
                .antMatchers("/employee/**").hasAnyAuthority(EMPLOYEE.name(), MANAGER.name())
                .antMatchers("/manager/**").hasAuthority(MANAGER.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/errors?errorMessage=Error+authenticating+Please+try+again+later&afterwardsPath=/login")
                .successHandler(authenticationSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(authUserService);

        return provider;
    }
}
