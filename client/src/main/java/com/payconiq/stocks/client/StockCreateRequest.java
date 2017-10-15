package com.payconiq.stocks.client;

import java.math.BigDecimal;

public class StockCreateRequest {

    private BigDecimal currentPrice;
    private String name;

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StockCreateRequest{" +
                "currentPrice=" + currentPrice +
                ", name='" + name + '\'' +
                '}';
    }
}
