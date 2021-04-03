package com.spring.boot.shop.service;

import com.spring.boot.shop.domain.Address;
import com.spring.boot.shop.domain.Member;
import com.spring.boot.shop.domain.Order;
import com.spring.boot.shop.domain.OrderStatus;
import com.spring.boot.shop.domain.item.Book;
import com.spring.boot.shop.domain.item.Item;
import com.spring.boot.shop.exception.NotEnoughStockException;
import com.spring.boot.shop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // GIVEN
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        // WHEN
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // THEN
        Order getOrder = orderRepository.findOne(orderId);

        assertThat(OrderStatus.ORDER).as("상품 주문시 상태는 ORDER").isEqualTo(getOrder.getStatus());
        assertThat(1).as("주문한 상품 종류 수가 정확해야 한다.").isEqualTo(getOrder.getOrderItems().size());
        assertThat(10000 * 2).as("주문 가격은 가격 * 수량이다.").isEqualTo(getOrder.getTotalPrice());
        assertThat(8).as("주문 수량만큼 재고가 줄어야 한다.").isEqualTo(item.getStockQuantity());
    }
    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // GIVEN
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        // WHEN
        Executable executable = () -> orderService.order(member.getId(), item.getId(), orderCount);

        // THEN
        assertThrows(NotEnoughStockException.class, executable);

        /*
            Throwable thrown = catchThrowable(() -> orderService.order(member.getId(), item.getId(), orderCount));
            assertThat(thrown).as("NotEnoughStockException 발생").isInstanceOf(NotEnoughStockException.class);

            ThrowingCallable throwingCallable = () -> orderService.order(member.getId(), item.getId(), orderCount);
            assertThatThrownBy(throwingCallable).isInstanceOf(NotEnoughStockException.class);

            ThrowingCallable throwingCallable = () -> orderService.order(member.getId(), item.getId(), orderCount);
            assertThatExceptionOfType(NotEnoughStockException.class).isThrownBy(throwingCallable);

            Executable executable = () -> orderService.order(member.getId(), item.getId(), orderCount)
            assertThrows(NotEnoughStockException.class, executable);
        */
    }

    @Test
    public void 주문취소() {
        //...
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

}