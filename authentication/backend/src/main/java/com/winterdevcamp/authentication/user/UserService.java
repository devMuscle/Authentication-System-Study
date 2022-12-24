package com.winterdevcamp.authentication.user;

import com.winterdevcamp.authentication.dto.SignUpReqDto;
import com.winterdevcamp.authentication.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void signUp(SignUpReqDto signUpReqDto) {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(signUpReqDto);

        userRepository.save(userEntity);

        log.info("회원가입 " + userEntity.toString());
    }
}
