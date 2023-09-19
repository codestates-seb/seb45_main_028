package com.mainproject.be28.ServiceTest;
import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.item.dto.ItemDto;
import com.mainproject.be28.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.item.dto.OnlyItemResponseDto;
import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.item.mapper.ItemMapper;
import com.mainproject.be28.item.repository.ItemRepository;
import com.mainproject.be28.item.service.ItemService;
import com.mainproject.be28.itemImage.repository.ItemImageRepository;
import com.mainproject.be28.itemImage.service.ItemImageService;
import com.mainproject.be28.member.service.MemberService;
import com.mainproject.be28.utils.CustomBeanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private CustomBeanUtils<Item> customBeanUtils;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testFindItem() {
        // Arrange
        long itemId = 1L;
        Item mockItem = new Item();
        mockItem.setItemId(itemId);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(mockItem));

        // Act
        Item result = itemService.findItem(itemId);

        // Assert
        assertNotNull(result);
        assertEquals(itemId, result.getItemId());
    }

    @Test
    public void testFindItems() {
        // fixme: Mock 데이터 및 조건 설정
        ItemSearchConditionDto condition = new ItemSearchConditionDto();
        PageRequest pageRequest = PageRequest.of(1,10);
        List<OnlyItemResponseDto> itemList = new ArrayList<>(); // Mock 아이템 리스트 생성

        // itemRepository.searchByCondition(condition, pageRequest)를 Mock으로 대체
        when(itemRepository.searchByCondition(condition, pageRequest)).thenReturn(itemList);

        // 테스트 수행
        Page<OnlyItemResponseDto> result = itemService.findItems(condition);

        //TODO: 결과 검증
    }

    @Test
    public void testCreateItem() throws IOException {
        // todo: CreateItem 테스트 코드 작성
    }

    @Test
    public void testUpdateItem() throws IOException {
        // todo: UpdateItem 테스트 코드 작성
    }

    @Test
    public void testDeleteItem() {
        // todo: DeleteItem 테스트 코드 작성
    }

}
