package com.digitinary.usermanagement.controller;

import com.digitinary.usermanagement.dto.MembershipFilterationDto;
import com.digitinary.usermanagement.dto.ResponseDto;
import com.digitinary.usermanagement.model.MembershipModel;
import com.digitinary.usermanagement.service.MembershipService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("library/userManagement/v1/memberships")
public class MembershipController {
    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("{user-id}")
    public ResponseEntity<ResponseDto> createMembership(@PathVariable("user-id") Long userId, @RequestBody MembershipModel membershipModel) {
        membershipService.createMembership(userId, membershipModel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED.toString(),"CREATED"));

    }
    @GetMapping("{membership-id}")
    public ResponseEntity<MembershipModel> getMembership(@PathVariable("membership-id") Long membershipId) {
        MembershipModel membershipModel = membershipService.getMembership(membershipId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(membershipModel);

    }
    @GetMapping("/search")
    public ResponseEntity<Page<MembershipModel>> searchMemberships(MembershipFilterationDto membershipFilterationDto, Pageable pageable) {
        Page<MembershipModel> membershipModel = membershipService.searchMemberships(membershipFilterationDto, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(membershipModel);

    }
}
