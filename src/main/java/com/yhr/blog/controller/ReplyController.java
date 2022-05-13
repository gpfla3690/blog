package com.yhr.blog.controller;

import com.yhr.blog.domain.Article;
import com.yhr.blog.domain.Member;
import com.yhr.blog.domain.Reply;
import com.yhr.blog.dto.reply.ReplyModifyForm;
import com.yhr.blog.dto.reply.ReplySaveForm;
import com.yhr.blog.service.ArticleService;
import com.yhr.blog.service.MemberService;
import com.yhr.blog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final MemberService memberService;
    private final ArticleService articleService;

    @PostMapping("/articles/{article_id}/replies")
    public String writeReply(@PathVariable(name = "article_id") Long articleId, ReplySaveForm replySaveForm, Principal principal){

        Member findMember = memberService.findByLoginId(principal.getName());
        Article findArticle = articleService.findById(articleId);

        replyService.writeReply(replySaveForm, findMember, findArticle);
        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/articles/{article_id}/replies/{reply_id}/modify")
    public String modifyReply(@PathVariable(name = "article_id") Long articleId, @PathVariable(name = "reply_id") Long replyId, ReplyModifyForm replyModifyForm, Principal principal){

        Reply findReply = replyService.findById(replyId);
        Article findArticle = articleService.findById(articleId);

        if(!findReply.getMember().getLoginId().equals(principal.getName()) || findReply.getArticle().getId() != findArticle.getId()){
            throw new IllegalStateException("올바르지 않은 접근입니다.");
        }

        replyService.modifyReply(replyModifyForm, findReply);

        return "redirect:/articles/" + articleId;
    }

    @GetMapping("/articles/{article_id}/replies/{reply_id}/delete")
    public String deleteReply(@PathVariable(name = "article_id") Long articleId, @PathVariable(name = "reply_id") Long replyId, Principal principal){

        Article findArticle = articleService.findById(articleId);
        Reply findReply = replyService.findById(replyId);

        if(!findReply.getMember().getLoginId().equals(principal.getName())){
            throw new IllegalStateException("올바른 접근이 아닙니다.");
        }

        Member findMember = memberService.findByLoginId(principal.getName());

        replyService.deleteReply(findArticle, findMember, findReply);

        return "redirect:/articles/" + articleId;
    }



}
