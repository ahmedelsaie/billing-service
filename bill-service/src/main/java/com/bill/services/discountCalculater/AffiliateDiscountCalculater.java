package com.bill.services.discountCalculater;

import com.bill.dtos.BillRequest;
import com.bill.services.StoreService;
import com.bill.model.CustomerType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AffiliateDiscountCalculater implements DiscountCalculater{

    private final StoreService storeService;

    @Override
    public boolean isApplicable(BillRequest billRequestDto) {
        return storeService.previousExist(billRequestDto.getStoreId(),billRequestDto.getUserId(),
                CustomerType.AFFILIATE);
    }

    @Override
    public BigDecimal getDiscountedValue(BillRequest billRequestDto) {
        return billRequestDto.getTotalAmountInUSD().
                multiply(BigDecimal.valueOf(10)).
                divide(BigDecimal.valueOf(100));
    }

    @Override
    public int rank() {
        return 1;
    }
}
