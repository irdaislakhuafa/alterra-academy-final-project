package com.irdaislakhuafa.alterraacademyfinalproject.security;

import com.irdaislakhuafa.alterraacademyfinalproject.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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
                        // swagger
                        "/api/v1/docs/swagger-ui/",
                        "/docs/v1",

                        // users
                        "/api/v1/users/**",

                        // roles
                        "/api/v1/roles/**")
                .permitAll()

                .anyRequest().authenticated()

                // disable session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // enable jwt filter
        // .and().addFilterBefore(jwtPerRequestFilter,
        // UsernamePasswordAuthenticationFilter.class)
        // end
        ;
        http.addFilterBefore(jwtPerRequestFilter, UsernamePasswordAuthenticationFilter.class);

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
