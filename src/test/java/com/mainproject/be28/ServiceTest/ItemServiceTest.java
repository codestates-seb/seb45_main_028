package com.mainproject.be28.ServiceTest;
import com.mainproject.be28.domain.member.service.Layer1.MemberService;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.entity.ItemImage;
import com.mainproject.be28.domain.shopping.item.mapper.ItemMapper;
import com.mainproject.be28.domain.shopping.item.repository.ItemImageRepository;
import com.mainproject.be28.domain.shopping.item.repository.ItemRepository;
import com.mainproject.be28.domain.shopping.item.service.ItemImageService;
import com.mainproject.be28.domain.shopping.item.service.ItemService;

import com.mainproject.be28.domain.shopping.review.entity.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private ItemImageService itemImageService;

    @Mock
    private ItemImageRepository itemImageRepository;


    @Mock
    private MemberService memberService;

    @Mock
    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void verifyExistItem() {
//        // Arrange
//        long itemId = 1L;
//        List<Review> review = new ArrayList<>();
//        List<ItemImage> image = new ArrayList<>();
//        Item mockItem = Item.builder()
//                .itemId(itemId)
//                .brand("brand")
//                .category("category")
//                .color("color")
//                .detail("detail")
//                .name("name")
//                .price(1L)
//                .stock(1)
//                .reviews(review)
//                .Images(image)
//                .reviewCount(1)
//                .score(1.1)
//                .build();
//        itemRepository.save(mockItem);
//
//        when(itemRepository.findById(itemId)).thenReturn(Optional.of(mockItem));
//
//        // Act
//        Optional<Item> result = itemRepository.findById(itemId);
//        Item test = itemService.verifyExistItem(itemId);
//
//        // Assert
//        assertEquals(Optional.of(result), test);
    }

    @Test
    void findItem() {
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
}
