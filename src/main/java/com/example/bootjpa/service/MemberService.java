package com.example.bootjpa.service;

import com.example.bootjpa.domain.Member;
import com.example.bootjpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 감증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        List<Member> findMembers = memberRepository.findAll();
        return findMembers;
    }

    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }

    public Member findOne(Long id) {
        Member findMember = memberRepository.findById(id).get();
        return findMember;
    }
}
