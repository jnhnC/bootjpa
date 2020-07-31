package com.example.bootjpa.responsitory;

import com.example.bootjpa.domain.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 1) 기존 JPA 순수 레포지토리에서 사용하는 방식
//    private final EntityManager em;

//    public List<Order> findAllWithMemberDelivery() {
//        return em.createQuery(
//                "select o from Order o" +
//                        " join fetch o.member m" , Order.class
//        ).getResultList();
//
//    }

    // 2) Springboot-jpa에서 지원해주는 방식
    @Query("select o from Order o left join fetch o.member m ")
    List<Order> findAllWithMemberDelivery() ;


    // 3) Springboot-jpa join fetch 최적화
    @Override
    @EntityGraph(attributePaths = {"member"})
    List<Order> findAll() ;

    // 3-1) Springboot-jpa join fetch 최적화(특정이름만)
    @EntityGraph(attributePaths = {"member"})
    List<Order> findByOrderDate(@Param("orderDate") LocalDateTime orderDate) ;
}
