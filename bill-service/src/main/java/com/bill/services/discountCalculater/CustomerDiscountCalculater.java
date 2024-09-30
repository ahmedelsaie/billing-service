package com.bill.services.discountCalculater;

import com.bill.dtos.BillRequest;
import com.bill.services.StoreService;
import com.bill.model.CustomerType;
import com.bill.model.StoreUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerDiscountCalculater implements DiscountCalculater {

    private final StoreService storeService;

    @Override
    public boolean isApplicable(BillRequest billRequestDto) {
        return isCustomerSince2Years(billRequestDto);
    }

    @Override
    public BigDecimal getDiscountedValue(BillRequest billRequestDto) {
        return billRequestDto.getTotalAmountInUSD().
                multiply(BigDecimal.valueOf(5)).
                divide(BigDecimal.valueOf(100));
    }

    @Override
    public int rank() {
        return 2;
    }

    private boolean isCustomerSince2Years(BillRequest billRequestDto) {
        Optional<StoreUser> registeration = storeService.getRegisteration(billRequestDto.getStoreId(), billRequestDto.getUserId(),
                CustomerType.CUSTOMER);

        return registeration.
                filter(storeUser ->
                        isDateBefore2Years(storeUser.getCreatedAt().toInstant())).
                isPresent();
    }

    private boolean isDateBefore2Years(Instant instant) {
        Instant now = Instant.now();
        now = now.minus(2, ChronoUnit.YEARS);
        return instant.isBefore(now);
    }
}
