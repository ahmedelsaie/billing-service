package com.bill.services;

import com.bill.dtos.BillRequest;
import com.bill.dtos.BillResult;
import com.bill.services.discountCalculater.PercentageCalculater;
import com.bill.services.user.AuthenticationService;
import com.bill.services.discountCalculater.MreDiscountCalculater;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
@AllArgsConstructor
public class BillService {

    private final AuthenticationService authenticationService;

    private final StoreService storeService;

    private final PercentageCalculater percentageCalculater;

    private final MreDiscountCalculater mreDiscountCalculater;

    private final CurrencyConverter currencyConverter;


    public BillResult calculateBill(BillRequest billRequest) {
        validateStore(billRequest);
        setTotalAmountInUSD(billRequest);

        BillResult billResult = new BillResult();
        billResult.setCurrenurrency(billRequest.getTargetCurrency().getCurrencyCode());

        BigDecimal totalAmountInUSD = getTotalAfterDiscount(billRequest);
        BigDecimal totalAmount = getTotalAmountInTargetCurrency(totalAmountInUSD, billRequest);

        billResult.setTotalAmount(totalAmount);
        return billResult;
    }

    private BigDecimal getTotalAmountInTargetCurrency(BigDecimal totalAmountInUSD, BillRequest billRequestDto) {
        return currencyConverter.convert(totalAmountInUSD, Currency.getInstance("USD"),
                billRequestDto.getTargetCurrency());
    }

    private void validateStore(BillRequest billRequest) {
        storeService.findById(billRequest.getStoreId());
    }

    private void setTotalAmountInUSD(BillRequest billRequest) {
        billRequest.setTotalAmountInUSD(currencyConverter.convert(billRequest.getTotalAmount(),
                billRequest.getSourceCurrency(),
                Currency.getInstance("USD")));
    }


    private BigDecimal getTotalAfterDiscount(BillRequest billRequestDto) {
        BigDecimal amountAfterPercenatgeDiscount =
                getAmountAfterPercenateageDiscount(billRequestDto);

        return getAmountAfterSecondDiscount(amountAfterPercenatgeDiscount);
    }

    private BigDecimal getAmountAfterSecondDiscount(BigDecimal amount) {
        BigDecimal discount = mreDiscountCalculater.getDiscount(amount);
        return amount.subtract(discount);
    }

    private BigDecimal getAmountAfterPercenateageDiscount(BillRequest billRequestDto) {
        BigDecimal discount = percentageCalculater.
                getDiscountedAmount(billRequestDto);

        return billRequestDto.getTotalAmountInUSD().subtract(discount);
    }
}
