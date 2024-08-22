package com.board.board.Post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void saveNewPost(String title, String contents){
        //입력 받아서
        //날짜 등록하여 디비에 저장
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        //날짜 자동 결정하는거 찾아보기
        postRepository.save(post);
    }

    public Optional<Post> findpostById(Long id){
        return postRepository.findById(id);
    }

    public void increaseViews(Post temppost){

        temppost.setViews(temppost.getViews() + 1);
        postRepository.save(temppost);
    }

    public void editPost(Long id, String title, String contents, Integer views){
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setContents(contents);
        post.setViews(views);
        postRepository.save(post);
    }
    
}
