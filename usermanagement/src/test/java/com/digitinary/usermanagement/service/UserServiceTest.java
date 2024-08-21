package com.digitinary.usermanagement.service;

import com.digitinary.usermanagement.dto.ReservationDto;
import com.digitinary.usermanagement.entity.User;
import com.digitinary.usermanagement.exception.FailedToInsertRecordException;
import com.digitinary.usermanagement.exception.PushEventToQueueFailedException;
import com.digitinary.usermanagement.mapper.UserMapper;
import com.digitinary.usermanagement.model.UserModel;
import com.digitinary.usermanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;


import javax.security.auth.login.FailedLoginException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private UserService userService;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void should_save_user() {
        UserModel userModel = UserModel.builder().build();
        User user = User.builder().build();
        when(userMapper.toUser(userModel)).thenReturn(user);
        userService.createUser(userModel);
        verify(userRepository, times(1)).save(user);
    }
    @Test
    void should_throw_exception_if_save_user_didnot_work() {
        UserModel userModel = UserModel.builder().build();
        User user = User.builder().build();
        when(userMapper.toUser(userModel)).thenReturn(user);
        when(userRepository.save(user)).thenThrow(FailedToInsertRecordException.class);
        assertThrows(RuntimeException.class, () -> userService.createUser(userModel));
    }
    @Test
    void should_get_user_ifExists() {
        UserModel userModel = UserModel.builder().build();
        User user = User.builder().build();
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        userService.getUser(1L);
        verify(userRepository, times(1)).findById(1L);
    }
    @Test
    void should_get_exception_ifdoesntExist() {
        UserModel userModel = UserModel.builder().build();
        User user = User.builder().build();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUser(1L));
    }

    @Test
    void should__throw_exception_when_fail_to_push_to_rabbitMQ(){
        ReservationDto reservationDto = ReservationDto.builder().build();
        doThrow(new RuntimeException("Simulated failure")).when(rabbitTemplate)
                .convertAndSend(exchange, routingKey, reservationDto);
        assertThrows(PushEventToQueueFailedException.class, () -> userService.reserveBook(reservationDto));
    }


}