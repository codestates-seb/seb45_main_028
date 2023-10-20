package com.mainproject.be28.global.response;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public final class MultiResponseDto<T> extends BaseResponse{
    private final List<T> data;
    private final PageInfo pageInfo;

    public MultiResponseDto(Page<T> page, HttpStatus status) {
        this.status = status.value();
        this.message = status.getReasonPhrase();
        this.data = page.getContent();
        this.pageInfo = new PageInfo(page.getNumber() + 1,
                page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
