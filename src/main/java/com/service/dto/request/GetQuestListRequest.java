package com.service.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class GetQuestListRequest implements Serializable {
    private String query;
    private String type;
    private Integer page;
    private Integer size;
}
