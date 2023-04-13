package com.example.projectfifa.security;


import com.example.projectfifa.constant.WebConstant;
import com.example.projectfifa.exception.custom.AuthenticationException;
import com.example.projectfifa.module.security.User;
import com.example.projectfifa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;


/* we created the Filter class, which happens before the execution of the request.
 In this filter we get the Token, decode it, get the Username from Token, and find the user.
 We save the User in Spring as a person who entered to the system. We pass username and roles of the user.
 Therefore, Spring saves info about the user, so Spring knows about their roles and username
 */
@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JWTTokenProvider jwtTokenProvider; // getting access to JWT Token provider
    private final UserService userService; // access to userService

    // in-built method
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /* if array with all addresses contains address to which a request was sent, we leave the filter
           (because it is available to everyone). So the token is not required */

        if(Arrays.stream(WebConstant.PERMIT_ALL_URL).
                anyMatch(v -> v.equals(request.getRequestURI()))) {
            filterChain.doFilter(request, response); // filter goes forward w/out problems
            return;
        }

        String headerAuthorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // if the Token is empty, or if it Starts with 'Bearer' then we will the method
        if(headerAuthorization == null || !headerAuthorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // remove characters of the length Bearer, with space. Leave only token itself
            String JWT = headerAuthorization.substring("Bearer ".length());

            String username = jwtTokenProvider.getSubject(JWT); // getting username

            jwtTokenProvider.isTokenValid(JWT, request); // checks for Token Validity

            User user = userService.getByUsernameThrowException(username); // checking username

            // userPrincipal -> wrapper excluding unnecessary things, leaving only important
            UserPrincipal userPrincipal = new UserPrincipal(user);

            if (!user.getIsNonLocked()) {
                throw new AuthenticationException("");
            }

            Authentication authentication = // getting a wrapper
                    jwtTokenProvider.getAuthentication(userPrincipal.getUsername(), new HashSet<>(userPrincipal.getAuthorities()), request);

            // put the user to the session w/ setAuthentication method
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            // if error occurs, we clear memory of the context, saying that no one is authorized in the system
            SecurityContextHolder.clearContext();
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // setting status as Unauthorized to our response (401)
            return;
        }

        filterChain.doFilter(request, response);
    }

}
