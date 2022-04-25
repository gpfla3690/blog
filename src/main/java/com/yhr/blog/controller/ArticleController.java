package com.yhr.blog.controller;

import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.article.ArticleSaveForm;
import com.yhr.blog.service.ArticleService;
import com.yhr.blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/articles/write")
    public String showWrite(){
        return "usr/article/write";
    }

    @PostMapping("/articles/write")
    public String doWrite(ArticleSaveForm articleSaveForm, Principal principal, Model model){

        try {
            Member findMember = memberService.findByLoginId(principal.getName());

            articleService.save(
                    articleSaveForm,
                    findMember
            );
        }catch (IllegalStateException e){
            model.addAttribute("err_msg", e.getMessage());
            return "usr/article/write";
        }

        return "redirect:/";

    }

}
