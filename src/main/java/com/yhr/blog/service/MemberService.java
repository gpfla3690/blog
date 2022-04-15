package com.yhr.blog.service;

import com.yhr.blog.dao.MemberRepository;
import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberSaveForm memberSaveForm) {

        Member member = Member.createMember(
                memberSaveForm.getLoginId(),
                memberSaveForm.getLoginPw(),
                memberSaveForm.getName(),
                memberSaveForm.getNickname(),
                memberSaveForm.getEmail()
        );

        memberRepository.save(member);

    }
}
