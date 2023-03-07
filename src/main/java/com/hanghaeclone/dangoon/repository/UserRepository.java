package com.hanghaeclone.dangoon.repository;

import com.hanghaeclone.dangoon.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByKakaoId(Long id);
    Optional<User> findByNickName(String nickName);

//    Optional<User> findByEmail(String email);
}
