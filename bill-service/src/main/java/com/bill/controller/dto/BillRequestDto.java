package com.bill.controller.dto;

import com.bill.model.ItemCategory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BillRequestDto {

    private String sourceCurrency;

    private String targetCurrency;

    private ItemCategory itemCategory;

    private BigDecimal totalAmount;

    private long storeId;
}
