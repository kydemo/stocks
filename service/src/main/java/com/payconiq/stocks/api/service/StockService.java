package com.payconiq.stocks.api.service;

import com.payconiq.stocks.api.entity.StockEntity;
import com.payconiq.stocks.api.exception.ItemNotFound;
import com.payconiq.stocks.api.repository.StockRepository;
import com.payconiq.stocks.client.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StockService {
    private static final Logger logger = LoggerFactory.getLogger(StockService.class);
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    private static Stock convert(StockEntity stockEntity) {
        Stock stock = new Stock();
        stock.setId(stockEntity.getId());
        stock.setCurrentPrice(stockEntity.getCurrentPrice());
        Date lastUpdate = stockEntity.getLastUpdate();
        if (lastUpdate != null)
            stock.setLastUpdate(lastUpdate.getTime());
        stock.setName(stockEntity.getName());
        return stock;
    }

    public List<Stock> findAll() {
        Iterable<StockEntity> all = stockRepository.findAll();
        return StreamSupport.stream(all.spliterator(), false)
                .map(StockService::convert).collect(Collectors.toList());
    }

    public Stock findById(Long stockId) {
        StockEntity stockEntity = stockRepository.findOne(stockId);
        if (stockEntity == null) throw new ItemNotFound();
        return convert(stockEntity);
    }

    @Transactional
    public void updateStockPrice(Long id, BigDecimal currentPrice) {
        int rowsEffected = stockRepository.updateStockPrice(id, currentPrice);
        if (rowsEffected == 0) throw new ItemNotFound();
    }

    @Transactional
    public Stock create(String name, BigDecimal currentPrice) {
        StockEntity stockEntity = new StockEntity();
        stockEntity.setCurrentPrice(currentPrice);
        stockEntity.setName(name);
        stockRepository.save(stockEntity);
        return convert(stockEntity);
    }
}
