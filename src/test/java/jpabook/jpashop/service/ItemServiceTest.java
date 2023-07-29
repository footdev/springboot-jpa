package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughQuantityException;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    private final ItemService itemService;

    @Autowired
    public ItemServiceTest(ItemService itemService) {
        this.itemService = itemService;
    }

    @Test
    public void 상품_저장하기() {

        //given
        Item item = new Book();

        //when
        itemService.saveItem(item);

        //then
        assertThat(itemService.findItems().size())
                .isGreaterThan(0);
    }

    @Test
    public void 상품_재고_증가_확인() {

        //given
        Item item = new Album();
        itemService.saveItem(item);

        Item saveAlbum = itemService.findItems().get(0);

        //when
        saveAlbum.addStock(100);
        itemService.saveItem(saveAlbum);

        //then
        assertThat(itemService.findItems().get(0).getStockQuantity())
                .isEqualTo(100);
    }

    @Test
    public void 상품_재고가_0_이하로_내려가면_예외_발생() {

        //given
        Item item = new Album();
        itemService.saveItem(item);

        Item saveAlbum = itemService.findItems().get(0);

        //when
        saveAlbum.addStock(100);
        itemService.saveItem(saveAlbum);

        //then
        assertThatThrownBy(() -> itemService.findItems()
                .get(0)
                .removeStock(101))
                .isInstanceOf(NotEnoughQuantityException.class);
    }
}
