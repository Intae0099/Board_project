package com.board.board.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findPageBy(Pageable page);
    Page<Post> findAllByTitleContains(String title, Pageable page);
}
