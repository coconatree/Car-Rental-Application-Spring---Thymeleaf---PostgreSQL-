package com.car_rental.Impl.ServiceImpl.UtilityServiceImpl;

import com.car_rental.Configuration.UserRoles;
import com.car_rental.Impl.RepositoryImpl.UserRepoImpl;
import com.car_rental.Logic.ServiceLogic.BaseService;
import com.car_rental.Model.DatabaseModel.AuthUser;
import com.car_rental.Model.DatabaseModel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthUserServiceImpl extends BaseService  implements UserDetailsService {

    private final UserRepoImpl      userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthUserServiceImpl(UserRepoImpl userRepo, PasswordEncoder passwordEncoder)
    {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> authorizedUser;

        try {
            authorizedUser = Optional.of(userRepo.selectUserByEmail(username));
        }
        catch (Exception err) {
            this.HandleException(err);
            throw new UsernameNotFoundException("Bad credentials");
        }

        authorizedUser.get().setPassword((passwordEncoder.encode(authorizedUser.get().getPassword())));

        AuthUser authUser = AuthUser.builder()
                .id(authorizedUser.get().getId())
                .username(authorizedUser.get().getEmail())
                .password(authorizedUser.get().getPassword())
                .role(authorizedUser.get().getRole())
                .build();

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(authorizedUser.get().getRole()));

        authUser.setGrantedAuthorities(authorities);

        return authUser;
    }
}
