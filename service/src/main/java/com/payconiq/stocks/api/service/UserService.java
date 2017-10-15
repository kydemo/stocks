package com.payconiq.stocks.api.service;

import com.payconiq.stocks.api.entity.UserEntity;
import com.payconiq.stocks.api.exception.ItemNotFound;
import com.payconiq.stocks.api.repository.UserRepository;
import com.payconiq.stocks.client.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserName(String username) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            logger.warn("UserEntity {} not found!", username);
            throw new ItemNotFound();
        }
        UserEntity userEntity = optionalUser.get();
        logger.debug("UserEntity {} found with id {}", username, userEntity.getId());
        return convert(userEntity);
    }

    private User convert(UserEntity userEntity) {
        User user = new User();
        user.setPassword(userEntity.getPassword());
        user.setUsername(userEntity.getUsername());
        user.setRole(userEntity.getRole());
        return user;
    }
}
