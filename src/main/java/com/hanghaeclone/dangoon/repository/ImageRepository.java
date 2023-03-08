package com.hanghaeclone.dangoon.repository;

import com.hanghaeclone.dangoon.entity.Image;
import com.hanghaeclone.dangoon.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findImagesByPostId(Long postId);
}
