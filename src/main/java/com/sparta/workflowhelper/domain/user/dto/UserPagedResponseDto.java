package com.sparta.workflowhelper.domain.user.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPagedResponseDto<T> {

    private int totalPages;
    private long totalElements;
    private int size;
    private int pageNumber;
    private List<T> content;

}
