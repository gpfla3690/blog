package com.yhr.blog.controller;

import com.yhr.blog.domain.Article;
import com.yhr.blog.domain.Category;
import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.article.ArticleDTO;
import com.yhr.blog.dto.article.ArticleListDTO;
import com.yhr.blog.dto.article.ArticleModifyForm;
import com.yhr.blog.dto.article.ArticleSaveForm;
import com.yhr.blog.service.ArticleService;
import com.yhr.blog.service.CategoryService;
import com.yhr.blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final CategoryService categoryService;

    @GetMapping("/articles/write")
    public String showWrite(Model model){

        model.addAttribute("categoryList", categoryService.findAll());

        return "usr/article/write";
    }

    @PostMapping("/articles/write")
    public String doWrite(ArticleSaveForm articleSaveForm, Principal principal, Model model){

        try {

            Category findCategory = categoryService.findById(articleSaveForm.getCategoryId());

            Member findMember = memberService.findByLoginId(principal.getName());

            articleService.save(
                    articleSaveForm,
                    findCategory,
                    findMember
            );
        }catch (IllegalStateException e){
            model.addAttribute("err_msg", e.getMessage());
            return "usr/article/write";
        }

        return "redirect:/";
    }

    @GetMapping("/articles/modify/{id}")
    public String showModify(@PathVariable(name = "id") Long id, Principal principal, Model model){

        Article findArticle = articleService.findById(id);

        if(!findArticle.getMember().getLoginId().equals(principal.getName())){
            return "redirect:/";
        }

        model.addAttribute("selectedCategory", findArticle.getCategory().getId());
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("articleModifyForm", new ArticleModifyForm(findArticle));
        model.addAttribute("id", id);

        return "usr/article/modify";
    }

    @PostMapping("/articles/modify/{id}")
    public String doModify(@PathVariable(name="id") Long id, ArticleModifyForm articleModifyForm){

        Category findCategory = categoryService.findById(articleModifyForm.getCategoryId());

        articleService.modifyArticle(articleModifyForm, id, findCategory);

        return "redirect:/";

    }

    @GetMapping("/articles")
    public String showList(Model model){
        List<ArticleListDTO> articles = articleService.getArticleList();

        model.addAttribute("articles", articles);

        return "usr/article/list";
    }

    @GetMapping("/articles/{id}")
    public String showDetail(@PathVariable(name = "id") Long id, Model model){

        ArticleDTO findArticle = articleService.getArticle(id);

        model.addAttribute("article", findArticle);

        return "usr/article/detail";
    }

    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable(name = "id") Long id, Principal principal){

        // id번 게시글의 로그인 아이디 / principal
        Article findArticle = articleService.findById(id);

        if(!findArticle.getMember().getLoginId().equals(principal.getName())){
            return "redirect:/";
        }

        articleService.delete(id);

        return "redirect:/articles";

    }





}
