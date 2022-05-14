package com.irdaislakhuafa.alterraacademyfinalproject.security;

import com.irdaislakhuafa.alterraacademyfinalproject.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    private final String ADMIN = "ROLE_ADMIN";
    private final String USER = "ROLE_USER";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // start
        http
                .csrf().disable()
                .authorizeRequests()

                // swagger docs and init (PermitAll)
                .antMatchers(
                        // init
                        "/",
                        BASE_URL + "/hello-world",

                        // swagger
                        BASE_URL + "/docs/**",
                        "/docs/v1",
                        "/v2/**")
                .permitAll()

                // author address (PermitAll)
                .antMatchers(HttpMethod.GET, BASE_URL + "/authors/address")
                .permitAll()
                // author address PUT/POST/DELETE
                .antMatchers(HttpMethod.PUT, BASE_URL + "/authors/address").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.POST, BASE_URL + "/authors/address").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.DELETE, BASE_URL + "/authors/address").hasAnyAuthority(ADMIN, USER)

                // authors
                .antMatchers(HttpMethod.GET, BASE_URL + "/authors/**").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.PUT, BASE_URL + "/authors").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.POST, BASE_URL + "/authors").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.DELETE, BASE_URL + "/authors").hasAnyAuthority(ADMIN)

                // roles PUT/POST/DELETE (admin)
                .antMatchers(HttpMethod.POST, BASE_URL + "/roles/").hasAnyAuthority(ADMIN)
                .antMatchers(HttpMethod.PUT, BASE_URL + "/roles/").hasAnyAuthority(ADMIN)
                .antMatchers(HttpMethod.DELETE, BASE_URL + "/roles/").hasAnyAuthority(ADMIN)
                // roles GET
                .antMatchers(HttpMethod.GET, BASE_URL + "/roles/**").permitAll()

                // user (PermitAll)
                .antMatchers(
                        BASE_URL + "/users/register",
                        BASE_URL + "/users/auth")
                .permitAll()

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
