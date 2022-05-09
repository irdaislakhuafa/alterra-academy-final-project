package com.irdaislakhuafa.alterraacademyfinalproject.security;

import com.irdaislakhuafa.alterraacademyfinalproject.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value(value = "${app.base.url}")
    private String BASE_URL;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtPerRequestFilter jwtPerRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // start
        http
                .csrf().disable()
                .authorizeRequests()

                // permit url
                .antMatchers(
                        // init
                        "/",
                        BASE_URL + "/hello-world",

                        // swagger
                        BASE_URL + "/docs/**",
                        "/docs/v1",
                        "/v2/**",

                        // users
                        BASE_URL + "/users/register",
                        BASE_URL + "/users/auth",

                        // roles
                        BASE_URL + "/roles/**")
                .permitAll()

                // admin
                .antMatchers(
                        // authors
                        BASE_URL + "/authors",
                        BASE_URL + "/authors/",

                        // users
                        BASE_URL + "/users",
                        BASE_URL + "/users/")
                .hasAnyAuthority("ROLE_ADMIN")

                // user
                .antMatchers(
                        // authors
                        BASE_URL + "/authors/findBy/**")
                .hasAnyAuthority("ROLE_USER")

                .anyRequest().authenticated()

                // disable session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // enable jwt filter
                .and().addFilterBefore(jwtPerRequestFilter, UsernamePasswordAuthenticationFilter.class)
        // end
        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new DaoAuthenticationProvider() {
            {
                setPasswordEncoder(passwordEncoder);
                setUserDetailsService(userService);
            }
        });
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
