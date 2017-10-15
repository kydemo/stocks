package com.payconiq.stocks.webapp.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class StocksPrincipalUser extends org.springframework.security.core.userdetails.User {

    public StocksPrincipalUser(String username, String password, List<GrantedAuthority> roles) {
        super(username, password, roles);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StocksPrincipalUser{");
        sb.append(getUsername());
        sb.append('}');
        return sb.toString();
    }

}
