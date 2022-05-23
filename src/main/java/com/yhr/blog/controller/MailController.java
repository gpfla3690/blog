package com.yhr.blog.controller;

import com.yhr.blog.dto.member.FindPasswordForm;
import com.yhr.blog.service.MailService;
import com.yhr.blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    private final MemberService memberService;

    public boolean getForgetPassword(FindPasswordForm findPasswordForm){

        if(!memberService.isDupleMember(findPasswordForm.getLoginId())){
            System.out.println("없는 아이디 입니다.");
            return false;
        }

        return true;
    }

}
