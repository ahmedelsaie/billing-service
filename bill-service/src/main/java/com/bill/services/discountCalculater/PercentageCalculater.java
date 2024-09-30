package com.bill.services.discountCalculater;

import com.bill.dtos.BillRequest;
import com.bill.model.ItemCategory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class PercentageCalculater {

    private final List<DiscountCalculater> discountCalculaters;

    public BigDecimal getDiscountedAmount(BillRequest billRequestDto) {
        if(billRequestDto.getItemCategory() == ItemCategory.GROCERIES)
            return BigDecimal.ZERO;

        return discountCalculaters.stream().
                sorted(Comparator.comparingInt(DiscountCalculater::rank)).
                filter(e -> e.isApplicable(billRequestDto)).
                findFirst().
                map(e -> e.getDiscountedValue(billRequestDto)).
                orElse(BigDecimal.ZERO);
    }
}
