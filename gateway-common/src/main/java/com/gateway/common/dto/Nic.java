package com.gateway.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Nic implements Serializable {
    private String subNetId;
    private String idAddress;
}
