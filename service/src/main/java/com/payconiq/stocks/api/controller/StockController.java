package com.payconiq.stocks.api.controller;

import com.payconiq.stocks.api.entity.StockEntity;
import com.payconiq.stocks.api.exception.IllegalUpdateRequest;
import com.payconiq.stocks.api.service.StockService;
import com.payconiq.stocks.client.Stock;
import com.payconiq.stocks.client.StockCreateRequest;
import com.payconiq.stocks.client.StockPriceUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * All CRUD operations for {@link StockEntity}
 */
@Controller
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * List all stocks. If there are no stocks, an empty list will be returned.
     *
     * @return
     */
    @RequestMapping(value = "/stocks", method = RequestMethod.GET)
    @ResponseBody
    public List<Stock> stocks() {
        logger.debug("Listing all stocks.");
        List<Stock> stocks = stockService.findAll();
        logger.debug("{} stocks found.", stocks.size());
        return stocks;
    }

    /**
     * Returns Stock for given id.
     * If there is not such an Stock; ItemNotFound will be thrown
     * which is mapped to Http Responses 404
     *
     * @param stockId
     * @return
     */
    @RequestMapping(value = "/stocks/{stockId}", method = RequestMethod.GET)
    @ResponseBody
    public Stock getStock(@PathVariable("stockId") Long stockId) {
        logger.debug("Finding stock {}.", stockId);
        Stock stock = stockService.findById(stockId);
        logger.debug("Found stock {} with name.", stockId, stock.getName());
        return stock;
    }

    /**
     * Updates price of given stock id.
     * If there is not such an Stock; ItemNotFound will be thrown
     * which is mapped to Http Responses 404
     *
     * @param updateRequest
     */
    @RequestMapping(value = "/stocks/{stockId}", method = RequestMethod.PUT)
    @ResponseBody
    public void updateStockPrice(@RequestBody StockPriceUpdateRequest updateRequest, @PathVariable("stockId") Long stockId) {
        if (!stockId.equals(updateRequest.getId()))
            throw new IllegalUpdateRequest("Stock Id of request does not match with resource location");
        logger.debug("StockEntity update request arrived {}.", updateRequest);
        stockService.updateStockPrice(updateRequest.getId(), updateRequest.getCurrentPrice());
    }

    /**
     * Creates a new Stock and returns it.
     *
     * @param createRequest
     * @return
     */
    @RequestMapping(value = "/stocks", method = RequestMethod.POST)
    @ResponseBody
    public Stock create(@RequestBody StockCreateRequest createRequest) {
        logger.debug("Creating new stock {}.", createRequest);
        return stockService.create(createRequest.getName(), createRequest.getCurrentPrice());
    }
}
