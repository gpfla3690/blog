package com.yhr.blog.controller;

import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.category.CategorySaveForm;
import com.yhr.blog.service.CategoryService;
import com.yhr.blog.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final MemberService memberService;

    @GetMapping("/categories/create")
    public String showCreate(){
        return "usr/category/create";
    }

    @PostMapping("/categories/create")
    public String doCreate(CategorySaveForm categorySaveForm, Principal principal){

        Member findMember = memberService.findByLoginId(principal.getName());

        categoryService.save(categorySaveForm, findMember);


        return "redirect:/";
    }

}
