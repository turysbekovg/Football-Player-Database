package com.example.projectfifa.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Set;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component // this means that when Spring starts, it creates an object based on this class, and save the object in its component
public class JWTTokenProvider { // this class is to create Tokens

    private final String TOKEN_EXCEPTION = "An error occurred when checking the token.";

    private final String TOKENS_NOT_EQUAL_EXCEPTION = "Tokens are not equal.";

    public String generateToken(UserPrincipal userPrincipal, String IP) {
        /*  withIssuer -> who created a Token
            withAudience -> who is a user of the Token
            withIssuedAt -> time of the Token creation
            withSubject -> to save owner of the Token
            withClaim -> additional info (i save IP here) (withClaims may be several)
            withExpiresAt -> date of expiration of the Token (its like an Instagram account)
            sign -> we need to sign the Token with unique key
         */
        return JWT.create()
                .withIssuer("")
                .withAudience("")
                .withIssuedAt(new Date())
                .withSubject(userPrincipal.getUsername())
                .withClaim("IP", IP)
                .withExpiresAt(new Date(System.currentTimeMillis() + 180_000_000)) // Current time in milliseconds and + 50 hours (Token will work for 50 hours)
                .sign(HMAC512("secretKey".getBytes()));
    }


    /* this method is to find User's IP
       HttpServletRequest -> contains the User's request */
    public String getIpFromClient (HttpServletRequest request) {
        return request.getRemoteAddr(); // returns IP-Address
    }


    public Authentication getAuthentication(String username, Set<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new // here we create wrapper
                UsernamePasswordAuthenticationToken(username, null, authorities); // pass username and list of roles

        // also we pass detail, and we pass request of a user in this details
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    public boolean isTokenValid(String token, HttpServletRequest request) { // Checks if the token
        JWTVerifier jwtVerifier = getJWTVerifier(); // create decoder for the Token
        String IP = this.getIpFromClient(request); // Get IP from the request

        // decodes the Token and gets IP form it, then compare to IP from the request.
        if (jwtVerifier.verify(token).getClaim("IP").asString().equals(IP)) {
            return true; // if Addresses are equal then return true
        }
        throw new TokenExpiredException(this.TOKENS_NOT_EQUAL_EXCEPTION); // otherwise return an Exception
    }

    // returns parameter Subject, where we contain username (owner of a token)
    public String getSubject (String token) {
        JWTVerifier verifier = getJWTVerifier(); // we get the decoder
        return verifier.verify(token).getSubject(); // decode the token using method verify(), then we call getSubject(), which returns the username
    }

    private JWTVerifier getJWTVerifier() { //
        JWTVerifier jwtVerifier; // creating decoder

        try {
            Algorithm algorithm = HMAC512("secretKey"); // secret key
            jwtVerifier = JWT.require(algorithm).withIssuer("").build(); // our decoder decodes the token w/ secret Key
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(this.TOKEN_EXCEPTION); // throwing exception when error
        }

        return jwtVerifier;
    }

}
