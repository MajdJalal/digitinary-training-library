package com.digitinary.usermanagement.service;


import com.digitinary.usermanagement.entity.Membership;
import com.digitinary.usermanagement.exception.AlreadyExistsRecordException;
import com.digitinary.usermanagement.exception.FailedToInsertRecordException;
import com.digitinary.usermanagement.mapper.MembershipMapper;
import com.digitinary.usermanagement.model.MembershipModel;
import com.digitinary.usermanagement.repository.MembershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MembershipService {

    private static final Logger logger = LoggerFactory.getLogger(MembershipService.class);

    private final MembershipRepository membershipRepository;
    private final MembershipMapper membershipMapper;

    public MembershipService(MembershipRepository membershipRepository, MembershipMapper membershipMapper) {
        this.membershipRepository = membershipRepository;
        this.membershipMapper = membershipMapper;
    }

    /**
     * @param membershipModel
     * @throws FailedToInsertRecordException if failed to insert a new record
     * @author Majd Alkhawaja
     */
    public void createMembership(MembershipModel membershipModel) {
        Membership membership = membershipMapper.toMembership(membershipModel);
        try{
            membershipRepository.save(membership);
        } catch (Exception e) {
            logger.error(e.getMessage() + " please try again");
            throw new FailedToInsertRecordException("could not insert a new membership record");
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
}
