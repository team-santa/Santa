package com.developer.santa.dto;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import java.util.List;

public class MultiResponseDto {
    private List<?> data;

    @Nullable
    private PageInfo pageInfo;

    public MultiResponseDto(List<?> data, Page page) {
        this.data = data;
        this.pageInfo = new PageInfo(page.getNumber() +1, page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
