package com.winterdevcamp.authentication.user;

import com.winterdevcamp.authentication.dto.SignUpReqDto;
import com.winterdevcamp.authentication.dto.ViewUserResDto;
import com.winterdevcamp.authentication.encryption.EncryptionUtil;
import com.winterdevcamp.authentication.token.JwtTokenProvider;
import com.winterdevcamp.authentication.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final EncryptionUtil encryptionUtil;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public void signUp(SignUpReqDto signUpReqDto) {
        try {
            signUpReqDto.encryptPw(encryptionUtil.encryptBySHA245(signUpReqDto.getPassword()));
        }catch (NoSuchAlgorithmException e) {
            log.info(e.toString());
        }

        UserEntity userEntity = UserMapper.INSTANCE.SignUpReqDtoToEntity(signUpReqDto);

        userRepository.save(userEntity);

        log.info("회원가입 " + userEntity.toString());
    }

    public List<ViewUserResDto> viewUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();

        List<ViewUserResDto> users = new ArrayList<>();

        for(UserEntity userEntity : userEntityList) {
            users.add(UserMapper.INSTANCE.toViewUserResDto(userEntity));
        }

        return users;
    }

    @Transactional
    public void updateUserAuthority(String token, Long userId) throws RuntimeException {
        Long tokenProviderUserId = jwtTokenProvider.getUserId(token);

        if(tokenProviderUserId!=userId) {
            throw new RuntimeException("본인 이외의 권한은 설정할 수 없습니다.");
        }

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("없는 회원입니다"));

        userEntity.updateAuthority();
    }

    public void leaveUser(Long userId) throws RuntimeException {
        try {
            userRepository.deleteById(userId);
        }catch (RuntimeException e) {
            throw new RuntimeException("해당 유저가 없습니다.");
        }
    }
}
