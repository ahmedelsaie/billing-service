package com.bill.services.discountCalculater;

import com.bill.dtos.BillRequest;

import java.math.BigDecimal;

public interface DiscountCalculater {

    public boolean isApplicable(BillRequest billRequestDto);

    public BigDecimal getDiscountedValue(BillRequest billRequestDto);

    public int rank();
}
