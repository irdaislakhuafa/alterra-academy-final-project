package com.irdaislakhuafa.alterraacademyfinalproject.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // start
        http
                .csrf().disable()

                .authorizeRequests()

                .antMatchers(
                        // swagger
                        "/api/v1/docs/swagger-ui/",
                        "/docs/v1",

                        // users
                        "/api/v1/users/**")
                .permitAll()

                .anyRequest().fullyAuthenticated()
                .and().httpBasic()
        // end
        ;
    }
}
