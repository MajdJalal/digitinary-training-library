package com.digitinary.usermanagement.service;


import com.digitinary.usermanagement.dto.MembershipFilterationDto;
import com.digitinary.usermanagement.entity.Membership;
import com.digitinary.usermanagement.entity.User;
import com.digitinary.usermanagement.exception.AlreadyExistsRecordException;
import com.digitinary.usermanagement.exception.FailedToInsertRecordException;
import com.digitinary.usermanagement.mapper.MembershipMapper;
import com.digitinary.usermanagement.model.MembershipModel;
import com.digitinary.usermanagement.model.UserModel;
import com.digitinary.usermanagement.repository.MembershipRepository;
import com.digitinary.usermanagement.repository.UserRepository;
import com.digitinary.usermanagement.repository.specifications.MembershipSpecs;
import com.digitinary.usermanagement.repository.specifications.UserSpecs;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MembershipService {

    private static final Logger logger = LoggerFactory.getLogger(MembershipService.class);

    private final MembershipRepository membershipRepository;
    private final MembershipMapper membershipMapper;
    private final UserRepository userRepository;

    public MembershipService(MembershipRepository membershipRepository, MembershipMapper membershipMapper, UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.membershipMapper = membershipMapper;
        this.userRepository = userRepository;
    }

    /**
     * @param membershipModel
     * @throws FailedToInsertRecordException if failed to insert a new record
     * @author Majd Alkhawaja
     */
    @Transactional
    public void createMembership(Long userId, MembershipModel membershipModel) {
        Membership membership = membershipMapper.toMembership(membershipModel);
        logger.info(String.valueOf(membership.getId()));
        Optional<User> optionalUser = userRepository.findById(userId);
        try{
            User user = optionalUser.get();
            membershipRepository.save(membership);
            user.getMembership().add(membership);
            logger.info(String.valueOf(membership.getId()));
            userRepository.save(user);
        } catch (Exception e) {
            logger.error(e.getMessage() + " please try again");
            throw new FailedToInsertRecordException("could not insert a new record");
        }

    }

    /**
     * @param membershipId
     * @return MembershipModel
     * @author Majd Alkhawaja
     */
    public MembershipModel getMembership(Long membershipId) {
        Optional<Membership> optionalMembership = membershipRepository.findById(membershipId);
        if(optionalMembership.isEmpty()) throw new AlreadyExistsRecordException("already exists membership");
        return membershipMapper.toMembershipModel(optionalMembership.get());
    }

    /**
     * searching the memberships with the
     * specs and the pageable passed
     * @param membershipFilterationDto
     * @param pageable
     * @return
     */
    public Page<MembershipModel> searchMemberships(MembershipFilterationDto membershipFilterationDto, Pageable pageable) {
        Specification<Membership> specs = buildMembershipSpecification(membershipFilterationDto);
        Page<Membership> memberships = membershipRepository.findAll(specs, pageable);
        Page<MembershipModel> membershipModels = memberships.map(membershipMapper::toMembershipModel);
        return membershipModels;
    }

    /**
     * adds specifications based on the passed filters
     * @param membershipFilterationDto
     * @return
     */
    private Specification<Membership> buildMembershipSpecification(MembershipFilterationDto membershipFilterationDto) {
        Specification<Membership> specs = Specification.where(null);
        if(membershipFilterationDto.getPassedMembershipId() != null) {
            specs  = specs.and(MembershipSpecs.hasId(membershipFilterationDto.getPassedMembershipId()));
        }
        if(membershipFilterationDto.getPassedMembershipType() != null) {
            specs  = specs.and(MembershipSpecs.hasMembershipType(membershipFilterationDto.getPassedMembershipType()));
        }
        if(membershipFilterationDto.getPassedIssueDate() != null) {
            specs  = specs.and(MembershipSpecs.hasIssueDate(membershipFilterationDto.getPassedIssueDate()));
        }
        if(membershipFilterationDto.getPassedExpiryDate() != null) {
            specs  = specs.and(MembershipSpecs.hasExpiryDate(membershipFilterationDto.getPassedExpiryDate()));
        }
        return specs;
    }

}
