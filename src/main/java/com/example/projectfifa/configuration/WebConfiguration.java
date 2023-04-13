package com.example.projectfifa.configuration;

import com.example.projectfifa.constant.WebConstant;
import com.example.projectfifa.security.JWTAuthorizationFilter;
import com.example.projectfifa.service.UserPrincipalService;
import com.example.projectfifa.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // class that includes settings of components
@EnableGlobalMethodSecurity(prePostEnabled = true) // this allows us to use one of methods to divide controllers by roles
@EnableWebSecurity // Web - controllers, Security - security
@RequiredArgsConstructor
public class WebConfiguration extends WebSecurityConfigurerAdapter { // purpose of this class is to configure
    // Spring Security component such that it don't limit the
    // access to our classes (controllers)

    //@Lazy
    private final UserPrincipalService userService; // use RequiredArgsConstruct
    private final BCryptPasswordEncoder encoder;

    private final JWTAuthorizationFilter jwtAuthorizationFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // we adjust AuthenticationManager for it to understand through what to find a user by username;
        // we say that the method that it needs is in the class of UserService (loadUserByUsername)
        // we need to find a user by its Username
        // then, before compare their passwords, please encode the password with encoder (because we pass it in usual represent)
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // to give permission to everyone
        // any request in class WebConstant is available for everyone by address, while others only for those who authorized
        http.csrf().disable().authorizeRequests().antMatchers(WebConstant.PERMIT_ALL_URL).permitAll()
                /* .antMatchers("/api/v1/football-player/**").hasRole("ROLE_FOOTBALL_PLAYER_ADMIN")
                .antMatchers("/api/v1/football-club/**").hasRole("ROLE_FOOTBALL_CLUB_ADMIN")
                .antMatchers("/api/v1/player-club/**").hasRole("ROLE_PLAYER_CLUB_ADMIN")
                another way of giving Roles
                */
                .anyRequest().authenticated().and().addFilterBefore(jwtAuthorizationFilter,
                        UsernamePasswordAuthenticationFilter.class); // UsernamePasswordAuthenticationFilter -> type
    }

    @Bean
    @Override // override is because this method is a method of the class WebSecConfAdapter
    public AuthenticationManager authenticationManagerBean() throws Exception {  // with this method we create object for AuthenticationManager
        return super.authenticationManagerBean();
    }
}
