package com.payconiq.stocks.api.controller;

import com.payconiq.stocks.api.service.UserService;
import com.payconiq.stocks.client.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns all User details.
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    @ResponseBody
    public User users(@PathVariable("username") String username) {
        logger.debug("UserEntity {} requested.", username);
        return userService.findByUserName(username);
    }

}
