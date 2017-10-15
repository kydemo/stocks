package com.payconiq.stocks.api.service;

import com.payconiq.stocks.api.entity.StockEntity;
import com.payconiq.stocks.api.exception.ItemNotFound;
import com.payconiq.stocks.api.repository.StockRepository;
import com.payconiq.stocks.client.Stock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();
    @Mock
    StockRepository stockRepository;
    @InjectMocks
    StockService stockService;

    @Test
    public void should_return_empty_list() throws Exception {
        when(stockRepository.findAll()).thenReturn(new ArrayList<>());
        List<Stock> stocks = stockService.findAll();
        assertEquals(stocks.size(), 0);
    }

    @Test
    public void should_return_list() throws Exception {
        //given
        ArrayList<StockEntity> list = new ArrayList<>();
        StockEntity stockEntity = new StockEntity();
        stockEntity.setCurrentPrice(BigDecimal.TEN);
        stockEntity.setName("ABCD");
        stockEntity.setId(99L);
        Date lastUpdate = new Date();
        stockEntity.setLastUpdate(lastUpdate);
        list.add(stockEntity);
        when(stockRepository.findAll()).thenReturn(list);

        //when
        List<Stock> stocks = stockService.findAll();

        //then
        assertEquals(stocks.size(), 1);
        Stock stock = stocks.get(0);
        assertEquals(stock.getName(), stock.getName());
        assertEquals(stock.getCurrentPrice(), BigDecimal.TEN);
        assertEquals(stock.getId(), (Long) 99L);
        assertEquals(stock.getLastUpdate(), (Long) lastUpdate.getTime());
    }

    @Test
    public void should_find_and_convert_socket() throws Exception {
        //given
        StockEntity stockEntity = new StockEntity();
        stockEntity.setCurrentPrice(BigDecimal.TEN);
        stockEntity.setName("ABCD");
        stockEntity.setId(99L);
        Date lastUpdate = new Date();
        stockEntity.setLastUpdate(lastUpdate);
        when(stockRepository.findOne(99L)).thenReturn(stockEntity);

        //when
        Stock stock = stockService.findById(99L);

        //then
        assertEquals(stock.getName(), stock.getName());
        assertEquals(stock.getCurrentPrice(), BigDecimal.TEN);
        assertEquals(stock.getId(), (Long) 99L);
        assertEquals(stock.getLastUpdate(), (Long) lastUpdate.getTime());
    }

    @Test
    public void should_throw_404_if_not_found() throws Exception {
        //given
        when(stockRepository.findOne(199L)).thenReturn(null);
        expectedException.expect(ItemNotFound.class);

        //when
        given(stockService.findById(199L)).willThrow(new ItemNotFound());
    }

    @Test
    public void should_throw_404_on_update() throws Exception {
        //given
        when(stockRepository.updateStockPrice(200L, BigDecimal.TEN)).thenReturn(0);
        expectedException.expect(ItemNotFound.class);

        //when
        stockService.updateStockPrice(199L, BigDecimal.TEN);
    }

    @Test
    public void should_update_item() {
        //given
        when(stockRepository.updateStockPrice(200L, BigDecimal.TEN)).thenReturn(1);

        //when
        //should not throw exception
        stockService.updateStockPrice(200L, BigDecimal.TEN);
    }

    @Test
    public void should_pass_request_to_repo() throws Exception {
        //given
        StockEntity updatedStockEnt = new StockEntity();
        ArgumentCaptor<StockEntity> ac = ArgumentCaptor.forClass(StockEntity.class);
        when(stockRepository.save(ac.capture())).thenReturn(updatedStockEnt);

        //when
        Stock stock = stockService.create("beta", BigDecimal.TEN);

        //then
        StockEntity value = ac.getValue();
        assertEquals(value.getName(), "beta");
        assertEquals(value.getCurrentPrice(), BigDecimal.TEN);
    }

}