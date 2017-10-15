package com.payconiq.stocks.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configuration.
 *
 * @author Kaan Yamanyar
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SALT = "payconiq-static-salt";

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserAuthenticationService userDetailsService;

    @Autowired
    private Md5PasswordEncoder md5PasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(md5PasswordEncoder);
        authProvider.setUserDetailsService(userDetailsService);
        //TODO It's more safe to use ReflectionSaltSource
        SystemWideSaltSource saltSource = new SystemWideSaltSource();
        saltSource.setSystemWideSalt(SALT);
        authProvider.setSaltSource(saltSource);
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/stocks/**").authenticated()
                .anyRequest().permitAll().and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        http.httpBasic();
        http.csrf().disable();
    }
}