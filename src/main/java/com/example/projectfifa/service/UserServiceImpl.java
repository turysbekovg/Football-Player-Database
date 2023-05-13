package com.example.projectfifa.service;

import com.example.projectfifa.dto.request.UserAuthorizationDtoRequest;
import com.example.projectfifa.dto.request.UserRegistrationDtoRequest;
import com.example.projectfifa.dto.response.UserDtoResponse;
import com.example.projectfifa.exception.CustomExceptionMessage;
import com.example.projectfifa.exception.custom.*;
import com.example.projectfifa.mapper.UserMapper;
import com.example.projectfifa.module.security.User;
import com.example.projectfifa.repository.UserRepository;
import com.example.projectfifa.security.JWTTokenProvider;
import com.example.projectfifa.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {  // implementation

    private final UserRepository userRepository; // provide with access to Repository

    private final BCryptPasswordEncoder encoder; // to encode password (hide)

    // to prevent recursions, since AuthentificationManager calls recursively we write Lazy
    @Lazy
    private final AuthenticationManager authenticationManager;  // class of Spring, this is for authentication (check for password and login)

    private final JWTTokenProvider jwtTokenProvider; // in-build

    private final String USERNAME_ALREADY_EXIST = "This login is already taken.";

    private final String AUTHENTICATION_IS_NON_LOCKED_EXCEPTION = "Your account is locked.";


    private User save (User user) { // method to save user
        return userRepository.save(user);
    }

    // method of UserDetailService, which finds a user by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getByUsernameThrowException(username);

        return new UserPrincipal(user); // return user as UserDetails
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username); // finds username, if not returns null
    }

    @Override
    public User getByUsernameThrowException(String username) {
        return this.getByUsername(username).orElseThrow(() ->
                new NotFoundException(CustomExceptionMessage.NOT_FOUND_EXCEPTION_MESSAGE)); // if user not found throw exception
    }

    @Override
    public void registration(UserRegistrationDtoRequest dtoRequest) {
        // in order value to be in low-case, and to get rid of Spaces
        String username = dtoRequest.getUsername().toLowerCase().trim(); // for value to be in low-case, and to get rid of Spaces
        String password = dtoRequest.getPassword().trim(); // to get rid of spaces

        // checking if the username is available or not
        Optional<User> user = this.getByUsername(username);
        if(user.isPresent()) throw new AlreadyExistException(this.USERNAME_ALREADY_EXIST); // if username is take -> throw except

        // creating new user and filling it, or catching an exception
        try {
            User createdUser = new User();

            createdUser.setUsername(username);
            createdUser.setPassword(encoder.encode(password)); // built-in function that encodes the password
            createdUser.setName(dtoRequest.getName());
            createdUser.setIsActive(false);
            createdUser.setIsNonLocked(true);

            this.save(createdUser); // saving created user

        } catch (Exception e) {  // if when creating an error occurs:
            log.error(e.getMessage()); // print the error message, to know which exception happened
            throw new RepositoryCreateException
                    (CustomExceptionMessage.CREATE_EXCEPTION_MESSAGE); // we return a message to client indicating that error occurred
        }
    }

    @Override
    public ResponseEntity<UserDtoResponse> authorization(UserAuthorizationDtoRequest dtoRequest, HttpServletRequest request) {
        String username = dtoRequest.getUsername().toLowerCase().trim(); // for value to be in low-case, and to get rid of Spaces
        String password = dtoRequest.getPassword().trim(); // to get rid of spaces

        try {
            this.authenticate(username, password); // it throws an exception (if username or password is not correct)
        } catch (Exception e) {
            if (e.getMessage().equals("User is disabled")) { // if the user account is not active
                throw new AuthenticationException(CustomExceptionMessage.AUTHENTICATION_IS_ACTIVE_EXCEPTION);
            } else if (e.getMessage().equals("User account is locked")) {
                throw new AuthenticationException(this.AUTHENTICATION_IS_NON_LOCKED_EXCEPTION);
            }
            // log.error(e); // in order to see exception in console
            // e.printStackTrace();
            // it is our custom exception, in-build exception will throw 403 exception
            throw new LoginException(CustomExceptionMessage.LOGIN_EXCEPTION_MESSAGE);
        }

        try { // it throws an exception when generating a Token or in another place
            User user = this.getByUsernameThrowException(username); // find user by username

            UserPrincipal userPrincipal = new UserPrincipal(user); // refract it into userPrincipal

            String IP = jwtTokenProvider.getIpFromClient(request); // get user's IP

            HttpHeaders httpHeaders = this.JWTHeader(userPrincipal, IP); // Pass to Header to create JWT and put Token to Header

        // returns Body, Header, Status
            return new ResponseEntity<>(UserMapper.userToDto(user), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage()); // print error to console
           // e.printStackTrace();
            throw new UnexpectedException(CustomExceptionMessage.UNEXPECTED_EXCEPTION_MESSAGE); // print unexpected exception
        }
    }

    private HttpHeaders JWTHeader(UserPrincipal userPrincipal, String IP) {
        HttpHeaders httpHeaders = new HttpHeaders(); // create new header

        String JWT = jwtTokenProvider.generateToken(userPrincipal, IP); // create new Token

        // HttHeaders -> Class, AUTHORIZATION -> variable w/ word "AUTHORIZATION". ALl available keys are in class HttpHeaders
        httpHeaders.add(HttpHeaders.AUTHORIZATION, JWT);

        return httpHeaders; // this method will return header, which contains token
    }

    private void authenticate(String username, String password) {
        // it checks for if there is a user with that username and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
