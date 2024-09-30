package com.bill.dtos;

import com.bill.model.ItemCategory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Setter
@Getter
public class BillRequest {

    private Currency sourceCurrency;

    private Currency targetCurrency;

    private ItemCategory itemCategory;

    private BigDecimal totalAmount;

    private long storeId;

    private Integer userId;

    private BigDecimal totalAmountInUSD;

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = Currency.getInstance(sourceCurrency);
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = Currency.getInstance(targetCurrency);
    }

}
