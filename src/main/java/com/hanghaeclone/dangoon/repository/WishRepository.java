package com.hanghaeclone.dangoon.repository;

import com.hanghaeclone.dangoon.entity.Post;
import com.hanghaeclone.dangoon.entity.User;
import com.hanghaeclone.dangoon.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    Optional<Wish> findByUserAndPost(User user, Post post);

}
