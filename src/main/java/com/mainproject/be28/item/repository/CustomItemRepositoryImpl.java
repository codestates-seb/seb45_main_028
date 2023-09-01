package com.mainproject.be28.item.repository;

import com.mainproject.be28.item.dto.OnlyItemResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.mainproject.be28.item.entity.QItem.item;


@RequiredArgsConstructor
@Repository
public class CustomItemRepositoryImpl implements CustomItemRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<OnlyItemResponseDto> searchAll(ItemSearchCondition condition, Pageable pageable){

        return queryFactory
                .select(Projections.bean(OnlyItemResponseDto.class // dto 클래스 및 필드 전달
                        ,item.name,item.price,item.detail,item.status,item.color,item.score ,item.brand ,item.category)
                )
                .from(item)
                .where(
                        equalsCategory(condition.getCategory()), // 동적 쿼리 조건문
                        equalsBrand(condition.getBrand()),
                        equalsColor(condition.getColor()),
//                      betweenPrice(condition.getLowPrice(), condition.getHighPrice()),
                        minimumPrice(condition.getLowPrice()),
                        maximumPrice(condition.getHighPrice())
                )
                .fetch();
    }

    private BooleanExpression equalsCategory(String searchCategory){
        return searchCategory == null ? null : item.category.contains(searchCategory);
    }

    private BooleanExpression equalsBrand(String searchBrand){
        return searchBrand == null ? null : item.brand.contains(searchBrand);
    }
    private BooleanExpression equalsColor(String searchColor){
        return searchColor == null ? null : item.color.contains(searchColor);
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
}
