package com.mainproject.be28.config;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.*;

import javax.persistence.*;

@Configuration
public class QueryDslConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
