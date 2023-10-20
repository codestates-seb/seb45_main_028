package com.mainproject.be28.domain.shopping.item.service;

import com.mainproject.be28.domain.shopping.item.dto.ItemDto;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.mapper.ItemMapper;
import com.mainproject.be28.domain.shopping.item.repository.ItemRepository;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @Test
    public void testFindItemWhenItemExistOrNot() {
// given
        Item item = Item.builder()
                .itemId(1L)
                .name("name")
                .brand("brand")
                .price(1000L)
                .color("color")
                .category("category")
                .detail("detail")
                .stock(5)
                .build();

        List< Review> reviews = new ArrayList<>();
        Review review = Review.builder()
                .reviewId(1L)
                .Score(3)
                .content("a")
                .likeCount(3L)
                .item(item)
                .build();
        reviews.add(review);
        reviews.add(review);
        item.setReviews(reviews);

// When
        when(itemRepository.findById(item.getItemId())).thenReturn(Optional.of(item));
        when(itemRepository.findById(item.getItemId()+1)).thenThrow(new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));

// Then
        ItemDto.Response response= itemService.findItem(item.getItemId());
        assertEquals(response, itemMapper.itemToItemResponseDto(item));
        assertThrows(BusinessLogicException.class, () -> itemService.findItem(item.getItemId() + 1));
    }

    @Test
    void verifyExistItemWhenItemExistOrNot() {
        Item item = Item.builder()
                .itemId(1L)
                .name("name")
                .brand("brand")
                .price(1000L)
                .color("color")
                .category("category")
                .detail("detail")
                .stock(5)
                .build();

        List< Review> reviews = new ArrayList<>();
        Review review = Review.builder()
                .reviewId(1L)
                .Score(3)
                .content("a")
                .likeCount(3L)
                .item(item)
                .build();
        reviews.add(review);
        reviews.add(review);
        item.setReviews(reviews);

// When
        when(itemRepository.findById(item.getItemId())).thenReturn(Optional.of(item));
        when(itemRepository.findById(item.getItemId()+1)).thenThrow(new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));

// Then
        Item response= itemService.verifyExistItem(item.getItemId());
        assertEquals(response, item);
        assertThrows(BusinessLogicException.class, () -> itemService.verifyExistItem(item.getItemId() + 1));
    }

    @Test
    void findItems() {
    }

    @Test
    void createItem() {
    }

    @Test
    void updateItem() {
    }

    @Test
    void deleteItem() {
    }

    @Test
    void decreaseItemStock() {
    }
}