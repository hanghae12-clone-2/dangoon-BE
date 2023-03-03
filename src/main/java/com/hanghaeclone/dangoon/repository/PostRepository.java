package com.hanghaeclone.dangoon.repository;

import com.hanghaeclone.dangoon.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
