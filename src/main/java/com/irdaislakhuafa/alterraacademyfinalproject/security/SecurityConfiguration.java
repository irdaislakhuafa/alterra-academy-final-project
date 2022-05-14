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

                // books
                .antMatchers(BASE_URL + "/books/**").hasAnyAuthority(ADMIN, USER)
                // books authors
                .antMatchers(BASE_URL + "/books/authors").hasAnyAuthority(ADMIN, USER)
                // books categories
                .antMatchers(BASE_URL + "/books/categories").hasAnyAuthority(ADMIN, USER)
                // books publishers
                .antMatchers(BASE_URL + "/books/publishers").hasAnyAuthority(ADMIN, USER)
                // books borrowing student and book
                .antMatchers(BASE_URL + "/books/borrowing/**").hasAnyAuthority(ADMIN, USER)

                // categories
                .antMatchers(HttpMethod.GET, BASE_URL + "/categories/**").permitAll()
                .antMatchers(HttpMethod.POST, BASE_URL + "/categories").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.PUT, BASE_URL + "/categories").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.DELETE, BASE_URL + "/categories").hasAnyAuthority(ADMIN, USER)

                // publishers
                .antMatchers(HttpMethod.GET, BASE_URL + "/publishers/**").permitAll()
                .antMatchers(HttpMethod.POST, BASE_URL + "/publishers").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.PUT, BASE_URL + "/publishers").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.DELETE, BASE_URL + "/publishers").hasAnyAuthority(ADMIN)

                // roles
                .antMatchers(HttpMethod.GET, BASE_URL + "/roles/**").permitAll()
                .antMatchers(HttpMethod.POST, BASE_URL + "/roles").hasAnyAuthority(ADMIN)
                .antMatchers(HttpMethod.PUT, BASE_URL + "/roles").hasAnyAuthority(ADMIN)
                .antMatchers(HttpMethod.DELETE, BASE_URL + "/roles").hasAnyAuthority(ADMIN)

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
