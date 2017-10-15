package com.payconiq.stocks.webapp.service;

import com.payconiq.stocks.client.Stock;
import com.payconiq.stocks.client.StockCreateRequest;
import com.payconiq.stocks.client.StockPriceUpdateRequest;
import com.payconiq.stocks.webapp.exception.ItemNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class StockService {
    private static final Logger logger = LoggerFactory.getLogger(StockService.class);
    private final RestTemplate restTemplate;

    public StockService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Stock> fetchStocks() {
        Stock[] stocks = restTemplate.getForObject(
                "/stocks/", Stock[].class);
        return Arrays.asList(stocks);
    }

    public Stock updateStock(Long stockId, double value) {
        StockPriceUpdateRequest updateRequest = new StockPriceUpdateRequest();
        updateRequest.setCurrentPrice(BigDecimal.valueOf(value));
        updateRequest.setId(stockId);
        try {
            restTemplate.put("/stocks/" + stockId, updateRequest);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new ItemNotFound("stock " + stockId + " not found.");
            else throw exception;
        }
        return fetchStock(stockId);
    }

    public Stock fetchStock(Long stockId) {
        try {
            return restTemplate.getForObject(
                    "/stocks/" + stockId, Stock.class);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new ItemNotFound("stock " + stockId + " not found.");
            else throw exception;
        }
    }

    public Stock createStock(String name, Double price) {
        StockCreateRequest createRequest = new StockCreateRequest();
        createRequest.setCurrentPrice(BigDecimal.valueOf(price));
        createRequest.setName(name);
        ResponseEntity<Stock> stockResponseEntity = restTemplate.postForEntity("/stocks/", createRequest, Stock.class);
        return
                stockResponseEntity.getBody();
    }
}
