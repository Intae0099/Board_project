package com.board.board;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final PostRepository postRepository;

//    private final Logger logger = LoggerFactory.getLogger(getClass());


    @GetMapping("/")
    @ResponseBody
    public String index(){
        return "인덱스 페이지";
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("posts", postRepository.findAll());
        return "list.html";
    }

    @GetMapping("/write")
    public String write(){
        return "write.html";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam String title, @RequestParam String contents){
        //입력 받아서
        //날짜 등록하여 디비에 저장
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        //날짜 자동 결정하는거 찾아보기
        postRepository.save(post);
        return "redirect:/list";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model){
        Optional<Post> data = postRepository.findById(id);
        if (data.isPresent()){
            model.addAttribute("post", data.get());
            Post temppost = data.get();
            temppost.setViews(temppost.getViews() + 1);
            postRepository.save(temppost);
            return "detail.html";
        }
        else{
            return "redirect:/list";
        }

    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model){
        Optional<Post> data = postRepository.findById(id);
        if(data.isPresent()){
            model.addAttribute("data", data.get());
            return "edit.html";
        }
        else{
            return "redirect:/list";
        }

    }

    @PostMapping("/edit")
    public String edit(Long id, String title, String contents, Integer views){
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setContents(contents);
        post.setViews(views);
        postRepository.save(post);
        return "redirect:/list";
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(Long id){
        postRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }
}
