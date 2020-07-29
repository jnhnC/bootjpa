package com.example.bootjpa;

import com.example.bootjpa.domain.Address;
import com.example.bootjpa.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void dbInit1(){
            Member member  = new Member();
            member.setName("userA");
            member.setAddress(new Address("서울","남부순환로","184-7"));
            em.persist(member);

        }

        public void dbInit2(){
            Member member  = new Member();
            member.setName("userB");
            member.setAddress(new Address("영통","박지성삼거리","231-1"));
            em.persist(member);

        }
    }

}
