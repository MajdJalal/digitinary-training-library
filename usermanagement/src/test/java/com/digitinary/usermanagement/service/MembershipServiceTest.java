package com.digitinary.usermanagement.service;

import com.digitinary.usermanagement.entity.Membership;
import com.digitinary.usermanagement.entity.User;
import com.digitinary.usermanagement.exception.AlreadyExistsRecordException;
import com.digitinary.usermanagement.mapper.MembershipMapper;
import com.digitinary.usermanagement.model.MembershipModel;
import com.digitinary.usermanagement.model.UserModel;
import com.digitinary.usermanagement.repository.MembershipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MembershipServiceTest {

    @Mock
    private MembershipRepository membershipRepository;
    @Mock
    private MembershipMapper membershipMapper;
    @InjectMocks
    private MembershipService membershipService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void should_save_membership() {
        MembershipModel membershipModel = MembershipModel.builder().build();
        Membership membership = Membership.builder().build();
        when(membershipMapper.toMembership(membershipModel)).thenReturn(membership);
        membershipService.createMembership(membershipModel);
        verify(membershipRepository, times(1)).save(membership);
    }
    @Test
    void should_throw_exception_if_save_membership_didnot_work() {
        MembershipModel membershipModel = MembershipModel.builder().build();
        Membership membership = Membership.builder().build();
        when(membershipMapper.toMembership(membershipModel)).thenReturn(membership);
        when(membershipRepository.save(membership)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> membershipService.createMembership(membershipModel));
    }

    @Test
    void should_successfully_get_membership() {
        Long membershipId = 1L;
        Membership membership = new Membership();
        MembershipModel membershipModel = MembershipModel.builder().build();

        when(membershipRepository.findById(membershipId)).thenReturn(Optional.of(membership));
        when(membershipMapper.toMembershipModel(membership)).thenReturn(membershipModel);

        MembershipModel result = membershipService.getMembership(membershipId);

        assertNotNull(result);
        assertEquals(membershipModel, result);

        verify(membershipRepository, times(1)).findById(membershipId);
        verify(membershipMapper, times(1)).toMembershipModel(membership);
    }
    @Test
    void testGetMembership_MembershipNotFound() {
        Long membershipId = 1L;
        when(membershipRepository.findById(membershipId)).thenReturn(Optional.empty());

        AlreadyExistsRecordException exception = assertThrows(AlreadyExistsRecordException.class, () -> {
            membershipService.getMembership(membershipId);
        });

        assertEquals("already exists membership", exception.getMessage());

        verify(membershipRepository, times(1)).findById(membershipId);
    }


}