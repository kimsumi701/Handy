package com.Portfolio.Handy.service;

import com.Portfolio.Handy.dto.MemberDto;
import com.Portfolio.Handy.entity.Member;
import com.Portfolio.Handy.entity.MemberDetails;
import com.Portfolio.Handy.exception.ErrorCode;
import com.Portfolio.Handy.exception.HospitalReviewAppException;
import com.Portfolio.Handy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    @Autowired
    private final MemberRepository memberRepository;
    //  회원가입
    public MemberDto join(MemberDto memberDto) {
        memberRepository.findByUserId(memberDto.getUserId())
                .ifPresent( member1 -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, String.format("userId : %s",member1.getUserId()));
                });

        Member join = memberRepository.save(Member.createMember(memberDto));
        return MemberDto.createMemberDto(memberRepository.save(join));
    }

    //  이메일 중복 체크
    public boolean emailDuplicateCheck(String email) {
        try {
            if (findByEmail(email).getEmail() != "") {
                return false;
            }
        } catch (IllegalStateException e) {
            return true;
        }

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId).orElse(null);
        if (member == null) {
            throw new UsernameNotFoundException(userId);
        }
        member.setRole("ROLE_USER");
        return new MemberDetails(member);
    }

    //  현재 로그인중인 유저 정보
    public Member currentMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = ((UserDetails) principal);
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();
        Member member = memberRepository.findByUserId(username).orElse(null);
        return member;
    }

    //  유저 정보 수정
    public Member updateMember(MemberDto memberDto) {
        Member target = memberRepository.findById(memberDto.getId())
                .orElseThrow(() -> new IllegalStateException("유저 정보 수정에 실패했습니다."));

        target.patch(memberDto);

        memberRepository.updateMember(
                target.getId(),
                target.getNickName(),
                target.getEmail(),
                target.getPassword()
        );

        Member update = memberRepository.findById(memberDto.getId()).orElse(null);

        return update;
    }

    //  유저 이메일 확인
    public Member findByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("등록된 이메일이 없습니다."));

        if (!member.getEmail().equals(email)) {
            throw new IllegalStateException("등록된 이메일이 없습니다.");
        }

        return member;
    }

    //  유저 아이디로 유저 찾기
    public Member findByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("가입된 계정이 아닙니다."));

        return member;
    }

    public Member updatePassword(Long id, String password) {
        Member target = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("잘못된 접근입니다."));

        target.setPassword(password);
        Member update = memberRepository.save(target);

        return update;
    }
}
