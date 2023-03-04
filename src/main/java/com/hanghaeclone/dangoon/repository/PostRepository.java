package com.hanghaeclone.dangoon.repository;

import com.hanghaeclone.dangoon.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByLocation(String location, Pageable pageable);
    Page<Post> findAllByTitleContainingOrLocationContaining(String title, String location, Pageable pageable);


}
