package com.service.dto.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RewardDto implements Serializable {
    private Integer gold;
    private Integer exp;
}
