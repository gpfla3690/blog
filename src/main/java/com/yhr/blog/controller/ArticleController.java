package com.yhr.blog.controller;

import com.yhr.blog.domain.Article;
import com.yhr.blog.domain.Category;
import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.article.ArticleDTO;
import com.yhr.blog.dto.article.ArticleListDTO;
import com.yhr.blog.dto.article.ArticleModifyForm;
import com.yhr.blog.dto.article.ArticleSaveForm;
import com.yhr.blog.dto.reply.ReplyListDTO;
import com.yhr.blog.service.ArticleService;
import com.yhr.blog.service.CategoryService;
import com.yhr.blog.service.MemberService;
import com.yhr.blog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.hibernate.id.BulkInsertionCapableIdentifierGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final ReplyService replyService;

    @GetMapping("/articles/write")
    public String showWrite(Model model, ArticleSaveForm articleSaveForm){

        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("articleSaveForm", articleSaveForm);

        return "usr/article/write";
    }

    @PostMapping("/articles/write")
    public String doWrite(@Validated ArticleSaveForm articleSaveForm, BindingResult bindingResult, Principal principal, Model model) throws UnsupportedEncodingException {

        if(bindingResult.hasErrors()){
            model.addAttribute("categoryList", categoryService.findAll());
            return "usr/article/write";
        }

        try {

            Category findCategory = categoryService.findById(articleSaveForm.getCategoryId());

            String categoryName = URLEncoder.encode(findCategory.getName(), "UTF-8");

            Member findMember = memberService.findByLoginId(principal.getName());

            articleService.save(
                    articleSaveForm,
                    findCategory,
                    findMember
            );

            return "redirect:/b/" + principal.getName() + "?category=" + categoryName;

        }catch (IllegalStateException e){
            model.addAttribute("err_msg", e.getMessage());
            return "usr/article/write";
        }
    }

    @GetMapping("/articles/modify/{id}")
    public String showModify(@PathVariable(name = "id") int id, Principal principal, Model model){

//        Article findArticle = articleService.findById(id);

        Member findMember = memberService.findByLoginId(principal.getName());
        Article findArticle = findMember.getArticles().get(id);


        if(!findArticle.getMember().getLoginId().equals(principal.getName())){
            return "redirect:/";
        }

        if(findArticle.getCategory() != null){
            findArticle.getCategory().getId();
        }else{
            model.addAttribute("selectedCategory", null);
        }

        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("articleModifyForm", new ArticleModifyForm(findArticle));
        model.addAttribute("id", id);

        return "usr/article/modify";
    }

    @PostMapping("/articles/modify/{id}")
    public String doModify(@PathVariable(name="id") Long id, @Validated ArticleModifyForm articleModifyForm, BindingResult bindingResult, Principal principal) throws UnsupportedEncodingException {

        if(bindingResult.hasErrors()){
            return "usr/article/modify";
        }

        Member findMember = memberService.findByLoginId(principal.getName());

        Category findCategory = categoryService.findById(articleModifyForm.getCategoryId());

        String categoryName = URLEncoder.encode(findCategory.getName(), "UTF-8");

        articleService.modifyArticle(articleModifyForm, id, findCategory);

        return "redirect:/b/" + principal.getName() + "?category=" + categoryName;

    }

    @GetMapping("/articles")
    public String showList(
            @RequestParam(name = "searchKeyword", defaultValue = "") String searchKeyword,
            @RequestParam(name = "page", defaultValue = "1") int currentPage,
            @RequestParam(name = "categoryId", defaultValue = "0") Long categoryId,
            Model model){

        Map<String, Object> articleListInfo = articleService.searchArticle(categoryId, currentPage, searchKeyword);

        int articleListCount = (int) Math.ceil((int)articleListInfo.get("articleListCount") / (double)10);

        model.addAttribute("searchArticle", articleListInfo.get("articleDTOList"));
        model.addAttribute("page", currentPage);
        model.addAttribute("maxSize", articleListCount);

        return "usr/article/list";
    }

    @GetMapping("/articles/{id}")
    public String showDetail(@PathVariable(name = "id") int id, Model model, Principal principal){

//        ArticleDTO findArticle = articleService.getArticle(id);
//
//        List<ReplyListDTO> replyList = replyService.getDtoList(findArticle.getId());
//
//        model.addAttribute("article", findArticle);
//        model.addAttribute("replyList", replyList);

        Member findMember = memberService.findByLoginId(principal.getName());
        Article findArticle = findMember.getArticles().get(id - 1);

        model.addAttribute("articleId", id);
        model.addAttribute("article", new ArticleDTO(findArticle));
        model.addAttribute("replyList", replyService.getDtoList((long) id));

        return "usr/article/detail";
    }

    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable(name = "id") Long id, Principal principal){

        // id??? ???????????? ????????? ????????? / principal
        Article findArticle = articleService.findById(id);

        if(!findArticle.getMember().getLoginId().equals(principal.getName())){
            return "redirect:/";
        }

        articleService.delete(id);

        return "redirect:/articles";

    }





}
