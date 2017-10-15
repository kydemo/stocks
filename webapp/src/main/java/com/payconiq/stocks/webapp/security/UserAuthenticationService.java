package com.payconiq.stocks.webapp.security;

import com.payconiq.stocks.client.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple UserAuthenticationService.
 *
 * @author Kaan Yamanyar
 */
@Service
public class UserAuthenticationService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);
    private final RestTemplate restTemplate;

    public UserAuthenticationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        logger.debug("Trying to login {}", username);
        try {
            User user = restTemplate.getForObject(
                    "/users/{username}", User.class, username);
            logger.debug("User {} found.", user.getUsername());
            return generateUserDetail(user);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new UsernameNotFoundException(username + " not found.");
            else throw exception;
        }
    }

    private org.springframework.security.core.userdetails.User generateUserDetail(User user) {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return new StocksPrincipalUser(user.getUsername(), user.getPassword(), roles);
    }
}

