package com.payconiq.stocks.webapp.controller;

import com.payconiq.stocks.client.Stock;
import com.payconiq.stocks.webapp.service.StockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StocksControllerTest {

    @Mock
    StockService stockService;

    @InjectMocks
    StocksController stockController;

    @Test
    public void should_returns_stocks() throws Exception {
        //given
        ModelMap map = new ModelMap();
        ArrayList<Stock> stocks = new ArrayList<>();
        when(stockService.fetchStocks()).thenReturn(stocks);

        //when
        stockController.stocks(map);

        //then
        assertEquals(map.get("stocks"), stocks);
    }

    @Test
    public void should_create_stock() throws Exception {
        //given
        ModelMap map = new ModelMap();
        ArrayList<Stock> stocks = new ArrayList<>();
        when(stockService.fetchStocks()).thenReturn(stocks);

        //when
        stockController.createStock("BETA", 10d, map);

        //then
        verify(stockService).createStock("BETA", 10d);
        assertEquals(map.get("stocks"), stocks);
    }

}