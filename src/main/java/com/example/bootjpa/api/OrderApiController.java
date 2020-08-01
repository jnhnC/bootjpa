package com.example.bootjpa.api;

import com.example.bootjpa.domain.Address;
import com.example.bootjpa.domain.Order;
import com.example.bootjpa.responsitory.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    //List 조인 조회시 fetch조인 사용
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        // 1) 순수 JPA를 이용한방법
       // List<Order> orders = orderRepository.findAllWithMemberDelivery();

        // 2)springboot-jpa 를 이용한 방법
        List<Order> orders = orderRepository.findAll();


        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());

        return result;
    }

    @Data
    private class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // Lazy 초기화
            orderDate = order.getOrderDate();
        }

    }
}
