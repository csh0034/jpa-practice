package study.rawjpa.service;

import study.rawjpa.shop.domain.item.Book;
import study.rawjpa.shop.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.rawjpa.shop.service.ItemService;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired EntityManager em;

    @Test
    public void add_item() {
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
    public void throw_not_enough_stock() {
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