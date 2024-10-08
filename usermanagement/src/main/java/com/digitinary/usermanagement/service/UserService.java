package com.digitinary.usermanagement.service;


import com.digitinary.usermanagement.dto.ReservationDto;
import com.digitinary.usermanagement.dto.UserFilterationDto;
import com.digitinary.usermanagement.entity.User;
import com.digitinary.usermanagement.exception.AlreadyExistsRecordException;
import com.digitinary.usermanagement.exception.FailedToInsertRecordException;
import com.digitinary.usermanagement.exception.PushEventToQueueFailedException;
import com.digitinary.usermanagement.mapper.UserMapper;
import com.digitinary.usermanagement.model.MembershipModel;
import com.digitinary.usermanagement.model.UserModel;
import com.digitinary.usermanagement.repository.UserRepository;
import com.digitinary.usermanagement.repository.specifications.UserSpecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<UserModel> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        Page<UserModel> userModels = userPage.map(userMapper::toUserModel);
        return userModels;
    }

    /**
     *  searching the users with the
     * specs and the pageable passed
     * @param userFilterationDto
     * @param pageable
     * @return Page of UserModel
     */
    public Page<UserModel> searchUsers(UserFilterationDto userFilterationDto, Pageable pageable) {
        Specification<User> specs = buildUserSpecification(userFilterationDto);
        Page<User> users = userRepository.findAll(specs, pageable);
        return users.map(userMapper::toUserModel);
    }

    /**
     * adds specifications based on the passed filters
     * @param userFilterationDto
     * @return
     */
    private Specification<User> buildUserSpecification(UserFilterationDto userFilterationDto) {
        Specification<User> specs = Specification.where(null);

        if(userFilterationDto.getPassedUserId() != null) {
            specs  = specs.and(UserSpecs.hasId(userFilterationDto.getPassedUserId()));
        }
        if(userFilterationDto.getPassedUserName() != null) {
            specs  = specs.and(UserSpecs.containsName(userFilterationDto.getPassedUserName()));
        }
        if(userFilterationDto.getPassedUserPhoneNumber() != null) {
            specs  = specs.and(UserSpecs.containsPhoneNumber(userFilterationDto.getPassedUserPhoneNumber()));
        }
        if(userFilterationDto.getPassedUserMembershipId() != null) {
            specs  = specs.and(UserSpecs.hasMembershipId(userFilterationDto.getPassedUserMembershipId()));
        }
        if(userFilterationDto.getPassedUserMembershipType() != null) {
            specs  = specs.and(UserSpecs.hasMembershipType(userFilterationDto.getPassedUserMembershipType()));
        }

        return specs;
    }


}
