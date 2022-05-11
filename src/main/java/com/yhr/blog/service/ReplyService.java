package com.yhr.blog.service;

import com.yhr.blog.dao.ReplyRepository;
import com.yhr.blog.domain.Article;
import com.yhr.blog.domain.Member;
import com.yhr.blog.domain.Reply;
import com.yhr.blog.dto.reply.ReplySaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public void writeReply(ReplySaveForm replySaveForm, Member findMember, Article findArticle) {

        Reply reply = Reply.createReply(replySaveForm);
        reply.setMember(findMember);
        reply.setArticle(findArticle);

        replyRepository.save(reply);

    }
}
