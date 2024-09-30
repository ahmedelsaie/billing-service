package com.bill.dtos.store;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateStoreDto {

    @NotEmpty
    private String name;

    private Integer userId;

    private String address;
}
