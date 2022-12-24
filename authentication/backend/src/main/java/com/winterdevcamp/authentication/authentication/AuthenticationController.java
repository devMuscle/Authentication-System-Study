package com.winterdevcamp.authentication.authentication;

import com.winterdevcamp.authentication.dto.AuthenticationReqDto;
import com.winterdevcamp.authentication.dto.TokenDto;
import com.winterdevcamp.authentication.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("")
    public ResponseEntity<TokenDto> login(@RequestBody AuthenticationReqDto authenticationReqDto) {

        try {
            TokenDto tokenDto = authenticationService.authenticate(authenticationReqDto);

            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }catch (Exception e) {
            log.info(e.toString());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
