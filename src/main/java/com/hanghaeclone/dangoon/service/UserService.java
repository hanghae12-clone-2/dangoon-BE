package com.hanghaeclone.dangoon.service;

import com.hanghaeclone.dangoon.dto.LoginRequestDto;
import com.hanghaeclone.dangoon.dto.ResponseDto;
import com.hanghaeclone.dangoon.dto.SignupRequestDto;
import com.hanghaeclone.dangoon.dto.UserResponseDto;
import com.hanghaeclone.dangoon.entity.User;
import com.hanghaeclone.dangoon.jwt.JwtUtil;
import com.hanghaeclone.dangoon.repository.UserRepository;
import com.hanghaeclone.dangoon.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signup(@Valid SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickName = signupRequestDto.getNickName();

        Optional<User> foundUsername = userRepository.findByUsername(username);
        if (foundUsername.isPresent()) {
            throw new IllegalArgumentException("이미 가입된 사용지입니다.");
//            return "이미 가입된 사용자입니다.";
        }
        Optional<User> foundNickname = userRepository.findByNickName(nickName);
        if (foundNickname.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
//            return "이미 존재하는 닉네임입니다.";
        }

        User user = new User(username, password, nickName);
        userRepository.save(user);
        return "회원가입 완료";
    }

    public String login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀 번호가 옳지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getNickName()));
        return "로그인 성공";
    }

    public UserResponseDto getUser(UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        return new UserResponseDto(user);
    }
}
