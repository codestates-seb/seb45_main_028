package com.mainproject.be28.complain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComplain is a Querydsl query type for Complain
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComplain extends EntityPathBase<Complain> {

    private static final long serialVersionUID = -772648487L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComplain complain = new QComplain("complain");

    public final com.mainproject.be28.auditable.QAuditable _super = new com.mainproject.be28.auditable.QAuditable(this);

    public final NumberPath<Long> complainId = createNumber("complainId", Long.class);

    public final EnumPath<Complain.ComplainStatus> complainStatus = createEnum("complainStatus", Complain.ComplainStatus.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.mainproject.be28.item.entity.QItem item;

    public final com.mainproject.be28.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QComplain(String variable) {
        this(Complain.class, forVariable(variable), INITS);
    }

    public QComplain(Path<? extends Complain> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComplain(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComplain(PathMetadata metadata, PathInits inits) {
        this(Complain.class, metadata, inits);
    }

    public QComplain(Class<? extends Complain> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new com.mainproject.be28.item.entity.QItem(forProperty("item")) : null;
        this.member = inits.isInitialized("member") ? new com.mainproject.be28.member.entity.QMember(forProperty("member")) : null;
    }

}

