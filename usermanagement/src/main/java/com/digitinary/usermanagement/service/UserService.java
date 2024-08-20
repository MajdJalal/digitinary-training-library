package com.digitinary.usermanagement.service;


import com.digitinary.usermanagement.dto.ReservationDto;
import com.digitinary.usermanagement.entity.User;
import com.digitinary.usermanagement.exception.AlreadyExistsRecordException;
import com.digitinary.usermanagement.exception.FailedToInsertRecordException;
import com.digitinary.usermanagement.exception.PushEventToQueueFailedException;
import com.digitinary.usermanagement.mapper.UserMapper;
import com.digitinary.usermanagement.model.MembershipModel;
import com.digitinary.usermanagement.model.UserModel;
import com.digitinary.usermanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public UserService(UserRepository userRepository, UserMapper userMapper, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * @param userModel
     * @throws FailedToInsertRecordException
     * @author Majd Alkhawaja
     */
    public void createUser(UserModel userModel) {
        User user = userMapper.toUser(userModel);
        try{
            userRepository.save(user);
        } catch (Exception e){
            logger.error(e.getMessage() + " please try again");
            throw new FailedToInsertRecordException("could not insert a new user record");
        }

    }


    /**
     * @param reservationDto
     * @throws PushEventToQueueFailedException
     * @author Majd Alkhawaja
     */
    public void reserveBook(ReservationDto reservationDto) {
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, reservationDto);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new PushEventToQueueFailedException("failed to push to RabbitMQ");
        }

    }

    /**
     * @param userId
     * @throws AlreadyExistsRecordException
     * @return UserModel
     * @author Majd Alkhawaja
     */
    public UserModel getUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) throw new AlreadyExistsRecordException("user exists");
        return userMapper.toUserModel(optionalUser.get());
    }
}
