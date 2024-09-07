package com.board.board.comment;

import com.board.board.Post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    public void writeComment(String content, Long parentId) {
        Comment data = new Comment();
        data.setContent(content);
        data.setParentId(parentId);
        commentRepository.save(data);
    }

    public void editComment(Long id, String content, Long parentId){
        Comment data = new Comment();
        data.setId(id);
        data.setContent(content);
        data.setParentId(parentId);
        commentRepository.save(data);
    }
}
