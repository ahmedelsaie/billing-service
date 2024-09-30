package com.bill.services.discountCalculater;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MreDiscountCalculater {

    public BigDecimal getDiscount(BigDecimal input)
    {
        int numberOfHundreds=input.divide(BigDecimal.valueOf(100)).intValue();
        return BigDecimal.valueOf(numberOfHundreds * 5);
    }
}
