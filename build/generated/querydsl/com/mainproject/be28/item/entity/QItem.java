package com.mainproject.be28.item.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = -1717095379L;

    public static final QItem item = new QItem("item");

    public final StringPath brand = createString("brand");

    public final StringPath category = createString("category");

    public final StringPath color = createString("color");

    public final StringPath detail = createString("detail");

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final ListPath<com.mainproject.be28.review.entity.Review, com.mainproject.be28.review.entity.QReview> reviews = this.<com.mainproject.be28.review.entity.Review, com.mainproject.be28.review.entity.QReview>createList("reviews", com.mainproject.be28.review.entity.Review.class, com.mainproject.be28.review.entity.QReview.class, PathInits.DIRECT2);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final StringPath status = createString("status");

    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata metadata) {
        super(Item.class, metadata);
    }

}

