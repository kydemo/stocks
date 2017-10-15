package com.payconiq.stocks.api.controller;

import com.payconiq.stocks.api.service.StockService;
import com.payconiq.stocks.client.Stock;
import com.payconiq.stocks.client.StockCreateRequest;
import com.payconiq.stocks.client.StockPriceUpdateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockControllerTest {
    @Mock
    StockService stockService;

    @InjectMocks
    StockController stockController;

    @Test
    public void should_return_list() {
        //given
        ArrayList<Stock> testList = new ArrayList<>();
        when(stockService.findAll()).thenReturn(testList);

        //when
        List<Stock> stocks = stockController.stocks();

        //then
        assertTrue(stocks == testList); //reference check
    }

    @Test
    public void should_return_specific_stock() {
        //given
        Stock stock = new Stock();
        long stockId = 10L;
        when(stockService.findById(stockId)).thenReturn(stock);

        //when
        Stock fetchedStock = stockController.getStock(stockId);

        //then
        assertTrue(stock == fetchedStock); //reference check
    }

    @Test
    public void should_update_stock() {
        //given
        long stockId = 10L;
        StockPriceUpdateRequest request = new StockPriceUpdateRequest();
        request.setId(stockId);
        request.setCurrentPrice(BigDecimal.ONE);

        //when
        stockController.updateStockPrice(request, request.getId());

        //then
        verify(stockService).updateStockPrice(stockId, BigDecimal.ONE);
    }

    @Test
    public void should_create_stock() {
        //given
        StockCreateRequest request = new StockCreateRequest();
        request.setName("Alfa");
        request.setCurrentPrice(BigDecimal.TEN);
        Stock mockStock = new Stock();
        when(stockService.create("Alfa", BigDecimal.TEN)).thenReturn(mockStock);

        //when
        Stock stock = stockController.create(request);

        //then
        assertTrue(stock == mockStock); //reference check
    }
}