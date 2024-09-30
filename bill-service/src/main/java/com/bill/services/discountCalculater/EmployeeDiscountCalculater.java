package com.bill.services.discountCalculater;

import com.bill.dtos.BillRequest;
import com.bill.services.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class EmployeeDiscountCalculater implements DiscountCalculater{

    private final StoreService storeService;

    @Override
    public boolean isApplicable(BillRequest billRequestDto) {
        return storeService.dostorehasemployee(billRequestDto.getStoreId(),
                billRequestDto.getUserId());
    }

    @Override
    public BigDecimal getDiscountedValue(BillRequest billRequestDto) {
        return billRequestDto.getTotalAmountInUSD().
                multiply(BigDecimal.valueOf(30)).
                divide(BigDecimal.valueOf(100));
    }

    @Override
    public int rank() {
        return 0;
    }
}
