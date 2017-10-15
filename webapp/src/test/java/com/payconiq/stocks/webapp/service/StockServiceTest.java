package com.payconiq.stocks.webapp.service;

import com.payconiq.stocks.client.Stock;
import com.payconiq.stocks.client.StockCreateRequest;
import com.payconiq.stocks.client.StockPriceUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {
    @Mock
    RestTemplate restTemplate;

    private StockService stockService;

    @Before
    public void prepare() {
        stockService = new StockService(restTemplate);
    }

    @Test
    public void should_call_proper_endpoint_on_fetch_stocks() throws Exception {
        //given
        Stock[] stocks = new Stock[1];
        stocks[0] = new Stock();
        when(restTemplate.getForObject(
                "/stocks/", Stock[].class)).thenReturn(stocks);
        //when
        List<Stock> stocksList = stockService.fetchStocks();

        //then
        assertTrue(stocksList.get(0) == stocks[0]); //reference check
    }

    @Test
    public void should_call_proper_endpoint_on_update_stocks() throws Exception {
        //given
        long stockId = 99;
        ArgumentCaptor<StockPriceUpdateRequest> captor = ArgumentCaptor.forClass(StockPriceUpdateRequest.class);

        //when
        stockService.updateStock(stockId, 7d);

        //then
        verify(restTemplate).put(matches("/stocks/" + stockId), captor.capture());
        StockPriceUpdateRequest value = captor.getValue();
        assertEquals((Long) stockId, value.getId());
        assertEquals(BigDecimal.valueOf(7d), value.getCurrentPrice());
    }

    @Test
    public void should_call_proper_url_for_fetch() throws Exception {
        //given
        Stock mockStock = new Stock();
        when(restTemplate.getForObject(
                "/stocks/74", Stock.class)).thenReturn(mockStock);
        //when
        Stock stock = stockService.fetchStock(74L);
        //then
        assertTrue(stock == mockStock);

    }

    @Test
    public void should_call_proper_mock() throws Exception {
        //given
        ArgumentCaptor<StockCreateRequest> captor = ArgumentCaptor.forClass(StockCreateRequest.class);
        ResponseEntity mocked = mock(ResponseEntity.class);
        Stock mockedStock = new Stock();
        when(mocked.getBody()).thenReturn(mockedStock);
        when(restTemplate.postForEntity(
                matches("/stocks/"), captor.capture(), Matchers.eq(Stock.class)))
                .thenReturn(mocked);
        //when
        Stock stock = stockService.createStock("Gamma", 10d);
        //then
        assertTrue(stock == mockedStock);
    }

}