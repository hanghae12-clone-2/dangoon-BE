package com.hanghaeclone.dangoon.service;


import com.hanghaeclone.dangoon.dto.ResponseDto;
import com.hanghaeclone.dangoon.dto.TemperatureResponseDto;
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
    @Transactional
    public TemperatureResponseDto likeUser(Long postId, User user) {

        Post post = postRepository.findById(postId).orElseThrow( () -> new NullPointerException("게시글 없음"));
        User targetUser = userRepository.findById(post.getUser().getId()).orElseThrow( () -> new NullPointerException("유저 없음"));
        Optional<Temperature> optionalTemperature = temperatureRepository.findByUserAndPost(user, post);

        String checkTemp = checkTemp(optionalTemperature);

        switch (checkTemp) {
            case "없음" -> {
                targetUser.addTempearture();
                Temperature temperature = new Temperature(post, user, "like");
                temperatureRepository.save(temperature);
//                return "좋아요 성공";
            }
            case "좋아요" -> {
                targetUser.cancelAddTempearture();
                temperatureRepository.delete(optionalTemperature.get());
//                return "좋아요 취소";
            }
            case "싫어요" -> {
                targetUser.cancelSubTempearture();
                targetUser.addTempearture();
                Temperature temperature = optionalTemperature.get();
                temperature.updateLike();
//                return "싫어요 취소 후 좋아요";
            }
        }
//        return targetUser.getTemperature();
        return TemperatureResponseDto.of(targetUser.getTemperature());
    }

    @Transactional
    public TemperatureResponseDto hateUser(Long postId, User user) {

        Post post = postRepository.findById(postId).orElseThrow( () -> new NullPointerException("게시글 없음"));
        User targetUser = userRepository.findById(post.getUser().getId()).orElseThrow( () -> new NullPointerException("유저 없음"));
        Optional<Temperature> optionalTemperature = temperatureRepository.findByUserAndPost(user, post);

        String checkTemp = checkTemp(optionalTemperature);

        switch (checkTemp) {
            case "없음" -> {
                targetUser.subTemperature();
                Temperature temperature = new Temperature(post, user, "hate");
                temperatureRepository.save(temperature);
//                return "싫어요 성공";
            }
            case "좋아요" -> {
                targetUser.cancelAddTempearture();
                targetUser.subTemperature();
                Temperature temperature = optionalTemperature.get();
                temperature.updateHate();
//                return "좋아요 취소후 싫어요";
            }
            case "싫어요" -> {
                targetUser.cancelSubTempearture();
                temperatureRepository.delete(optionalTemperature.get());
//                return "싫어요 취소";
            }
        }
//        return String.valueOf(targetUser.getTemperature());
        return TemperatureResponseDto.of(targetUser.getTemperature());
    }


    public String checkTemp(Optional<Temperature> optionalTemperature) {
        if(optionalTemperature.isEmpty()) {
            return "없음";
        }
        if(optionalTemperature.get().getStatus().equals("like")) {
            return "좋아요";
        }else {
            return "싫어요";
        }
    }
}
