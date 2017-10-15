package com.payconiq.stocks.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends
        WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user")  // #1
                .password("password")
                .roles("DEFAULT")
                .and()
                .withUser("admin") // #2
                .password("password")
                .roles("ADMIN", "USER");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO Security disabled for an easier DEMO. Security configuration example can be checked in webapp config.
        http.authorizeRequests()
                .antMatchers("/ExampleURL/**").authenticated()
                .anyRequest().permitAll();
        http.httpBasic();
        http.csrf().disable();
    }
}