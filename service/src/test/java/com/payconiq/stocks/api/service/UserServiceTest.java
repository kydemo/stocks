package com.payconiq.stocks.api.service;

import com.payconiq.stocks.api.entity.UserEntity;
import com.payconiq.stocks.api.exception.ItemNotFound;
import com.payconiq.stocks.api.repository.UserRepository;
import com.payconiq.stocks.client.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void should_throw_not_found_exception_if_user_does_not_exist() throws Exception {
        //when
        when(userRepository.findByUsername("Abcd")).thenReturn(Optional.empty());
        expectedException.expect(ItemNotFound.class);

        //given
        given(userService.findByUserName("Abcd")).willThrow(new ItemNotFound());
    }

    @Test
    public void should_return_user_if_exisst() throws Exception {
        //when
        UserEntity user = new UserEntity();
        String passTest = "PassTest";
        user.setPassword(passTest);
        String roleTest = "RoleTest";
        user.setRole(roleTest);
        String nickTest = "NickTest";
        user.setUsername(nickTest);

        when(userRepository.findByUsername(nickTest)).thenReturn(Optional.of(user));

        //given
        User testUser = userService.findByUserName(nickTest);

        //then
        assertEquals(nickTest, testUser.getUsername());
        assertEquals(roleTest, testUser.getRole());
        assertEquals(passTest, testUser.getPassword());
    }

}

