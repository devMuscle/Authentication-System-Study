package com.winterdevcamp.authentication.user;

import com.winterdevcamp.authentication.authority.AdminOnly;
import com.winterdevcamp.authentication.authority.NoAuth;
import com.winterdevcamp.authentication.dto.SignUpReqDto;
import com.winterdevcamp.authentication.dto.ViewUserResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final String tokenHeader = "X-AUTH-TOKEN";

    @NoAuth
    @PostMapping("")
    public void signUp(@RequestBody SignUpReqDto signUpReqDto) {
        userService.signUp(signUpReqDto);
    }

    @AdminOnly
    @GetMapping("")
    public ResponseEntity<List<ViewUserResDto>> viewUsers() {
        return new ResponseEntity<>(userService.viewUsers(), HttpStatus.OK);
    }

    @AdminOnly
    @DeleteMapping("/admin/{userId}")
    public ResponseEntity<Void> forceLeaveUser(@PathVariable("userId") Long userId) {
        try {
            userService.leaveUser(userId);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (RuntimeException e) {
            log.info(e.toString());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}/authority")
    public ResponseEntity<Void> updateUserAuthority(@RequestHeader(tokenHeader) String token, @PathVariable("userId") Long userId) {
        try {
            userService.updateUserAuthority(token, userId);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch(RuntimeException e) {
            log.info(e.toString());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
