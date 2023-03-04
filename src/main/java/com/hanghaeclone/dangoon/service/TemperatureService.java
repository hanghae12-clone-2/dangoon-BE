package com.hanghaeclone.dangoon.service;


import com.hanghaeclone.dangoon.entity.Post;
import com.hanghaeclone.dangoon.entity.Temperature;
import com.hanghaeclone.dangoon.repository.TemperatureRepository;
import com.hanghaeclone.dangoon.entity.User;
import com.hanghaeclone.dangoon.repository.PostRepository;
import com.hanghaeclone.dangoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemperatureService {

    private final TemperatureRepository temperatureRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

//
//    @Transactional
//    public String likeUser(Long postId, String type, User user) {
//    }
}
