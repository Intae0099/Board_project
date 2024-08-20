package com.board.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final PostRepository postRepository;
    private final PostService postService;

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

    @GetMapping("/list/page/{pagenum}")
    public String pagelist(Model model, @PathVariable Integer pagenum){
        Page<Post> result = postRepository.findPageBy(PageRequest.of(pagenum - 1, 5, Sort.by("id").descending()));
        int nowPage = result.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage+9, result.getTotalPages());

        model.addAttribute("posts", result);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "list.html";
    }

    @GetMapping("/write")
    public String write(){
        return "write.html";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam String title, @RequestParam String contents){
        postService.saveNewPost(title, contents);
        return "redirect:/list";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model){
        Optional<Post> data = postService.findpostById(id);
        if (data.isPresent()){
            model.addAttribute("post", data.get());
            Post temppost = data.get();
            postService.increaseViews(temppost);
            return "detail.html";
        }
        else{
            return "redirect:/list";
        }

    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model){
        Optional<Post> data = postService.findpostById(id);
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
        postService.editPost(id, title, contents, views);
        return "redirect:/list";
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(Long id){
        postRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }
}
