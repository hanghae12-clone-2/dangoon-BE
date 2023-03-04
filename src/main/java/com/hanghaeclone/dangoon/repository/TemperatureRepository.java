package com.hanghaeclone.dangoon.repository;

import com.hanghaeclone.dangoon.entity.Post;
import com.hanghaeclone.dangoon.entity.Temperature;
import com.hanghaeclone.dangoon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemperatureRepository extends JpaRepository<Temperature, Long> {

    Optional<Temperature> findByUserAndPostAndStatus(User user, Post post, String status);
    Optional<Temperature> findByUserAndPost(User user, Post post);
}