package com.payconiq.stocks.client;

import java.math.BigDecimal;

public class StockPriceUpdateRequest {
    private Long id;
    private BigDecimal currentPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return "StockPriceUpdateRequest{" +
                "id=" + id +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
