package com.yhr.blog.controller;

import com.yhr.blog.dto.member.FindPasswordForm;
import com.yhr.blog.service.MailService;
import com.yhr.blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    private final MemberService memberService;

    @ResponseBody
    @PostMapping("/mails/find/pw")
    public boolean getForgetPassword(@RequestBody FindPasswordForm findPasswordForm){

        if(!memberService.isDupleMember(findPasswordForm.getLoginId())){
            System.out.println("없는 아이디 입니다.");
            return false;
        }

        System.out.println(findPasswordForm.getLoginId());
        System.out.println(findPasswordForm.getEmail());

        try {
            mailService.sendMail(findPasswordForm);

        }catch (Exception e){
            e.getMessage();
            return false;
        }

        return true;
    }

}
