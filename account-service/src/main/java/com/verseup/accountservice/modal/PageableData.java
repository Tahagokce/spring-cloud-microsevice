package com.verseup.accountservice.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageableData<T> {

    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private T data;
}
