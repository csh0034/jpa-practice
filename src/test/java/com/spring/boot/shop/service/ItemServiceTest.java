package com.spring.boot.shop.service;

import com.spring.boot.shop.domain.item.Book;
import com.spring.boot.shop.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired EntityManager em;

    @Test
    public void add_item() throws Exception {
        // GIVEN
        Book book = new Book();
        book.setName("book");

        // WHEN
        itemService.saveItem(book);

        // THEN
        em.flush();
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void throw_not_enough_stock() throws Exception {
        // GIVEN
        Book book = new Book();
        book.setName("book");

        // WHEN
        itemService.saveItem(book);

        // THEN
        em.flush();
        assertThat(book.getId()).isNotNull();
        assertThatExceptionOfType(NotEnoughStockException.class)
                .isThrownBy(() -> book.removeStock(1));
    }
}