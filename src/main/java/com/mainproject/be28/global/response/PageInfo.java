package com.mainproject.be28.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public final class PageInfo {
    private final  int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

}