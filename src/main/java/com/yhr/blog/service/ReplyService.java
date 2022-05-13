package com.yhr.blog.service;

import com.yhr.blog.dao.ReplyRepository;
import com.yhr.blog.domain.Article;
import com.yhr.blog.domain.Member;
import com.yhr.blog.domain.Reply;
import com.yhr.blog.dto.reply.ReplyListDTO;
import com.yhr.blog.dto.reply.ReplyModifyForm;
import com.yhr.blog.dto.reply.ReplySaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Reply findById(Long id){

        Optional<Reply> findReply = replyRepository.findById(id);

        return findReply.orElseThrow(
                () -> {
                    throw new NoSuchElementException("해당 댓글은 존재하지 않습니다.");
                }
        );

    }


    @Transactional
    public void modifyReply(ReplyModifyForm replyModifyForm, Reply findReply) {
        findReply.modifyReply(replyModifyForm);
    }

    @Transactional
    public void deleteReply(Article findArticle, Member findMember, Reply findReply) {

        findArticle.getReplies().remove(findReply);
        findMember.getReplies().remove(findReply);

        replyRepository.delete(findReply);


    }

    public List<Reply> getAll(){
        return replyRepository.findAll();
    }

    public List<ReplyListDTO> getDtoList() {

        List<Reply> replyList = getAll();

        List<ReplyListDTO> replyListDTOList = new ArrayList<>();

        for(Reply reply : replyList){
            replyListDTOList.add(new ReplyListDTO(reply));
        }

        return replyListDTOList;


    }
}
