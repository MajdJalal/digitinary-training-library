package com.digitinary.usermanagement.controller;


import com.digitinary.usermanagement.dto.ReservationDto;
import com.digitinary.usermanagement.dto.ResponseDto;
import com.digitinary.usermanagement.dto.UserFilterationDto;
import com.digitinary.usermanagement.model.MembershipModel;
import com.digitinary.usermanagement.model.UserModel;
import com.digitinary.usermanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("library/userManagement/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto> createUser(@RequestBody UserModel userModel) {
        userService.createUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED.toString(),"CREATED"));

    }
    @PostMapping("/reservation")
    public ResponseEntity<ResponseDto> reserveBook(@RequestBody ReservationDto reservationDto) {
        userService.reserveBook(reservationDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.toString(), "OK"));

    }
    @GetMapping("{user-id}")
    public ResponseEntity<UserModel> getUser(@PathVariable("user-id") Long userId) {
         UserModel userModel = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userModel);
    }
    @GetMapping("")
    public ResponseEntity<Page<UserModel>> getAllUsers(Pageable pageable) {
         Page<UserModel> userModelPage = userService.getAllUsers(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userModelPage);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<UserModel>> searchUsers( UserFilterationDto userFilterationDto, Pageable pageable) {
         Page<UserModel> userModelPage = userService.searchUsers(userFilterationDto, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userModelPage);
    }





}
