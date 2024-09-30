package com.bill.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillResult {

    private String currenurrency;

    private BigDecimal totalAmount;

}
