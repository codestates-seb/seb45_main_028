package com.mainproject.be28.item.repository;

import com.mainproject.be28.item.dto.ItemSearchConditionDto;
import com.mainproject.be28.item.dto.OnlyItemResponseDto;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mainproject.be28.item.entity.QItem.item;
import static com.mainproject.be28.review.entity.QReview.review;

@RequiredArgsConstructor
@Repository
@Slf4j
public class CustomItemRepositoryImpl implements CustomItemRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<OnlyItemResponseDto> searchByCondition(ItemSearchConditionDto condition, Pageable pageable){
//todo: 상품정렬 조건 리스트화 (다중조건 정렬)
        return queryFactory
                .select(Projections.bean(OnlyItemResponseDto.class // dto 클래스 및 필드 전달
                       ,item.itemId ,item.name,item.price,item.detail,item.stock, item.color,item.brand ,item.category)
                )
                .from(item)
                .leftJoin(item.reviews, review)
                .where(
                        equalsCategory(condition.getCategory()), // 동적 쿼리 조건문
                        equalsBrand(condition.getBrand()),
                        colorLike(condition.getColor()),
//                      betweenPrice(condition.getLowPrice(), condition.getHighPrice()),
                        minimumPrice(condition.getLowPrice()),
                        maximumPrice(condition.getHighPrice())
                )
                .where(
                        nameLike(condition.getName())
                )
                .groupBy(item)
                .orderBy(sortCondition(condition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression equalsCategory(String searchCategory){
        return searchCategory == null ? null : item.category.toUpperCase().contains(searchCategory.toUpperCase());
    }

    private BooleanExpression equalsBrand(String searchBrand){
        return searchBrand == null ? null : item.brand.toUpperCase().contains(searchBrand.toUpperCase());
    }
    private BooleanExpression colorLike(String searchColor){
        return searchColor == null ? null : item.color.toUpperCase().like("%"+searchColor.toUpperCase()+"%");
    }
//    private BooleanExpression betweenPrice(Long lowPrice, Long highPrice){
//        return (lowPrice==null&&highPrice==null) ? null: item.price.between(lowPrice, highPrice);
//    }
    private BooleanExpression minimumPrice(Long lowPrice){
        return lowPrice==null ? null: item.price.goe(lowPrice);
    }
    private BooleanExpression maximumPrice(Long highPrice){
        return highPrice==null ? null: item.price.loe(highPrice);
    }

    private BooleanExpression nameLike(String searchQuery) {
        return searchQuery == null ? null : item.name.toUpperCase().like("%" + searchQuery.toUpperCase() + "%");
    }

    private OrderSpecifier<?> sortCondition(ItemSearchConditionDto search) {
        Order direction = search.getOrder()!=null&& search.getOrder().equalsIgnoreCase("asc") ? Order.ASC : Order.DESC;
        if (search.getSort()!=null) {
                switch (search.getSort()){
                    case "name": return new OrderSpecifier<>(direction,item.name);
                    case "price": return new OrderSpecifier<>(direction,item.price);
                    case "review": return new OrderSpecifier<>(direction, item.reviews.size());
                    case "score": return new OrderSpecifier<>(direction, review.Score.avg());
                }
            }
        return new OrderSpecifier<>(direction, item.itemId);
    }
}