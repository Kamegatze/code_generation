package com.kamegatze.code_generation.jwt;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kamegatze.code_generation.services.user_details.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import com.auth0.jwt.JWT;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;


/**
 * Java class for work with jwt token. Encoding and decoding jwt.
 * */
@Component
public class JwtUtils {

    @Value("${app.secret}")
    private String secret;

    @Value("${app.time}")
    private int time;


    /**
     * Method for generation token.
     * Object Authentication you can get from object AuthenticationManager from method authenticate.
     * For example: Authentication authentication = authenticationManager.authenticate(
     *      new UsernamePasswordAuthenticateToken(
     *          userLogin,
     *          userPassword
     *      )
     * );
     * */
    public String generationJwtToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Algorithm algorithm = Algorithm.HMAC256(secret);

        Date now = new Date();

        return JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + time))
                .withIssuer("code_generation")
                .withSubject(userDetails.getUsername())
                .withJWTId(String.valueOf(userDetails.getUser().getId()))
                .sign(algorithm);
    }

    /**
     * Verification of the token is that it is issued by this server
     * */
    public boolean validateJwtToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("code_generation")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            return true;
        } catch (JWTVerificationException | IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
        }

        return false;
    }
    /**
     * get username from token
     * */
    public String getUserNameFromJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).withIssuer("code_generation").build().verify(token).getSubject();
    }


    /**
     * get id user from token
     * */
    public String getIdUser(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).withIssuer("code_generation").build().verify(token).getId();
    }

    public static String getJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if(!(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))) {
            return null;
        }

        return headerAuth.substring(7);
    }
}
