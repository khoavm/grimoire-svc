package com.service.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DataList<T> implements Serializable  {
    private Long total;
    private List<T> data;
    private Integer page;
    private Integer size;
}
