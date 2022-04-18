package com.yhr.blog.service;

import com.yhr.blog.config.Role;
import com.yhr.blog.dao.MemberRepository;
import com.yhr.blog.domain.Member;
import com.yhr.blog.dto.member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberSaveForm memberSaveForm) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Member member = Member.createMember(
                memberSaveForm.getLoginId(),
                bCryptPasswordEncoder.encode(memberSaveForm.getLoginPw()),
                memberSaveForm.getName(),
                memberSaveForm.getNickname(),
                memberSaveForm.getEmail(),
                Role.MEMBER
        );

        memberRepository.save(member);

    }
}
