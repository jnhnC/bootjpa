package com.example.bootjpa;

import com.example.bootjpa.domain.Address;
import com.example.bootjpa.domain.Member;
import com.example.bootjpa.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = new Member();
            member.setName("userA");
            member.setAddress(new Address("서울","남부순환로1430","870-1"));
            em.persist(member);

            Order order = Order.createOrder(member) ;
            em.persist(order);

        }

        public void dbInit2() {
            Member member = new Member();
            member.setName("userB");
            member.setAddress(new Address("서울","남부순환로1720-1","651-2"));
            em.persist(member);

            Order order = Order.createOrder(member) ;
            em.persist(order);
        }
    }
}
