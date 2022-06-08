package com.yhr.blog.controller;

import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.member.MemberModifyForm;
import com.yhr.blog.dto.member.MemberSaveForm;
import com.yhr.blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/join")
    public String showJoin(Model model, MemberSaveForm memberSaveForm){

        model.addAttribute("memberSaveForm", memberSaveForm);
        return "usr/member/join";
    }

    @PostMapping("/members/join")
    public String doJoin(@Validated MemberSaveForm memberSaveForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "usr/member/join";
        }

        memberService.save(memberSaveForm);

        return "redirect:/";
    }


    @GetMapping("/members/login")
    public String showLogin(){
        return "usr/member/login";
    }


    @GetMapping("/members/modify/{loginId}")
    public String showModify(@PathVariable(name = "loginId") String loginId, Principal principal, Model model){

        Member findMember = memberService.findByLoginId(loginId);

        if(!findMember.getLoginId().equals(principal.getName())){
            return "redirect:/";
        }

        model.addAttribute("memberModifyForm", new MemberModifyForm(findMember));
        model.addAttribute("loginId", loginId);

        return "usr/member/modify";
    }

    @PostMapping("/members/modify/{loginId}")
    public String doModify(@PathVariable(name = "loginId") String loginId, @Validated MemberModifyForm memberModifyForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "usr/member/modify";
        }

        memberService.modifyMember(memberModifyForm, loginId);

        return "redirect:/";

    }

    @GetMapping("/members/find/pw")
    public String showFindPw(){
        return "usr/member/findPw";
    }

    @DeleteMapping("/members")
    @ResponseBody
    public boolean doDelete(@RequestBody String loginId, Principal principal){

        if(!loginId.equals(principal.getName())){
            return false;
        }

        memberService.deleteMember(loginId);

        return true;
    }


}
