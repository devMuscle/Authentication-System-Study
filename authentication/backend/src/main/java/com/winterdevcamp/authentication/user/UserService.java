package com.winterdevcamp.authentication.user;

import com.winterdevcamp.authentication.dto.SignUpReqDto;
import com.winterdevcamp.authentication.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void signUp(SignUpReqDto signUpReqDto) {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(signUpReqDto);

        userRepository.save(userEntity);
    }
}
