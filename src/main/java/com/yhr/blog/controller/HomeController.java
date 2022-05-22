package com.yhr.blog.controller;

import com.yhr.blog.dto.article.ArticleListDTO;
import com.yhr.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String showIndex(Model model){

        List<ArticleListDTO> articles = articleService.getArticleList();
        Long articleCount = articleService.articleCount();

        model.addAttribute("articleCount", articleCount);
        model.addAttribute("articles", articles);

        return "index";
    }

}
