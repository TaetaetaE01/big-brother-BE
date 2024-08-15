package com.example.bigbrotherbe.domain.member.component;

import com.example.bigbrotherbe.domain.member.dto.request.SignUpDto;
import com.example.bigbrotherbe.domain.member.entity.Member;
import com.example.bigbrotherbe.domain.member.repository.MemberRepository;
import com.example.bigbrotherbe.global.exception.BusinessException;
import com.example.bigbrotherbe.global.exception.enums.ErrorCode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLoader {
    private final MemberRepository memberRepository;


    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Member getMember(Long id) {
     return  memberRepository.findById(id).orElseThrow(
            () -> new BusinessException(ErrorCode.FAIL_LOAD_MEMBER)
        );
    }

    public Member findByUserName(String username) {
        return memberRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 사용자 이름입니다."));
    }

    public Member findByMemberId(String memberId) {
        return memberRepository.findById(Long.valueOf(memberId)).orElseThrow(() -> new IllegalArgumentException("잘못된 사용자 아이디입니다."));
    }

    public Member findByMemberEmail(String memberEmail) {
        return memberRepository.findByEmail(memberEmail).orElseThrow(() -> new BusinessException(ErrorCode.NO_EXIST_EMAIL));

    }
    public Optional<Member> findByMemberEmailForCheck(String memberEmail) {
        return memberRepository.findByEmail(memberEmail);
    }

    public List<Member> getAllMember() {
        return memberRepository.findAll();
    }

    public Member signUp(SignUpDto signUpDto, String encodePassword) {
        return saveMember(signUpDto.toEntity(signUpDto,encodePassword));
    }
}