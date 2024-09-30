package com.bill.dtos.store;

import lombok.Data;

import java.util.Date;

@Data
public class StoreResponseDto {

    private String id;

    private String name;

    private Integer userId;

    private Date createdAt;

    private Date updatedAt;
}
