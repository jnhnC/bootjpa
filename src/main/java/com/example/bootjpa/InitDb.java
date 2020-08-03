package com.example.bootjpa;

import com.example.bootjpa.domain.Address;
import com.example.bootjpa.domain.Member;
import com.example.bootjpa.domain.Order;
import com.example.bootjpa.domain.OrderItem;
import com.example.bootjpa.domain.item.Book;
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

            Book book1 = new Book();
            book1.setName("bookA");
            book1.setPrice(100000);
            book1.setStockQuantity(100);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("bookB");
            book2.setPrice(120000);
            book2.setStockQuantity(80);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 30000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 12000, 1);

            Order order = Order.createOrder(member, orderItem1, orderItem2) ;
            em.persist(order);

        }

        public void dbInit2() {
            Member member = new Member();
            member.setName("userB");
            member.setAddress(new Address("서울","남부순환로1720-1","651-2"));
            em.persist(member);

            Book book3 = new Book();
            book3.setName("bookC");
            book3.setPrice(110000);
            book3.setStockQuantity(90);
            em.persist(book3);

            Book book4 = new Book();
            book4.setName("bookD");
            book4.setPrice(130000);
            book4.setStockQuantity(110);
            em.persist(book4);

            OrderItem orderItem1 = OrderItem.createOrderItem(book3, 11000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book4, 13000, 1);


            Order order = Order.createOrder(member, orderItem1, orderItem2) ;
            em.persist(order);
        }
    }
}
