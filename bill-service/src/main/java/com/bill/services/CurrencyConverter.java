package com.bill.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
@AllArgsConstructor
public class CurrencyConverter {

    private final RemoteCurrencyConverter remoteCurrencyConverter;

    public BigDecimal convert(BigDecimal amount, Currency source, Currency target) {
        return amount.multiply(convert(source, target));
    }

    public BigDecimal convert(Currency source, Currency target) {

        BigDecimal firstRate = remoteCurrencyConverter.getConvertRateFromUSD(source);
        BigDecimal secRate = remoteCurrencyConverter.getConvertRateFromUSD(target);

        System.out.println("the first rate is " + secRate.divide(firstRate, 2));

        return secRate.divide(firstRate, 2);
    }
}
