package com.gateway.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RootVolume implements Serializable {
    private String type;
    private Integer size;
}
