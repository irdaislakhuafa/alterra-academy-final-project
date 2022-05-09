package com.irdaislakhuafa.alterraacademyfinalproject.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.User;
import com.irdaislakhuafa.alterraacademyfinalproject.services.UserService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtPerRequestFilter extends OncePerRequestFilter {
    private final JwtUtility jwtUtility;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        log.info("Get " + JwtConstantVariables.AUTHORIZATION + " from header");
        final String authorization = request.getHeader(JwtConstantVariables.AUTHORIZATION);

        log.info("Checking authorizarion");
        if (authorization != null && authorization.startsWith(JwtConstantVariables.BEARER)) {
            log.info("Success get " + JwtConstantVariables.AUTHORIZATION + " from header");
            final String token = authorization.substring(JwtConstantVariables.BEARER.length()).trim();
            log.info("Success get token from " + JwtConstantVariables.AUTHORIZATION);

            log.info("Get user_id from token");
            final String userId = String.valueOf(jwtUtility.getAllClaimsFromToken(token).get("user_id")).trim();
            log.info("Success get user_id from token");

            log.info("Checking user_id");
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                log.info("Get information from user");
                final Optional<User> user = userService.findById(userId);
                if (!user.isPresent()) {
                    throw new UsernameNotFoundException("User with id: " + userId + " not found");
                }
                log.info("Success get information from user");

                log.info("Validating token");
                if (jwtUtility.isTokenValid(token, user.get())) {
                    log.info("Token is valid");

                    log.info("Preparing user authentication");
                    final var userAuth = new UsernamePasswordAuthenticationToken(
                            user.get().getEmail(),
                            user.get().getPassword(),
                            user.get().getAuthorities());

                    final var authUserDetails = new WebAuthenticationDetailsSource().buildDetails(request);

                    userAuth.setDetails(authUserDetails);
                    log.info("Success build details");

                    log.info("Register user to SecurityContextHolder");
                    SecurityContextHolder.getContext().setAuthentication(userAuth);
                    log.info("Success registered");
                } else {
                    log.error("Token: " + token + " is not valid");
                }

            }
        }

        filterChain.doFilter(request, response);
    }

}
