package com.payconiq.stocks.webapp.controller;

import com.payconiq.stocks.client.Stock;
import com.payconiq.stocks.webapp.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Home page controller
 *
 * @author Kaan Yamanyar
 */
@Controller
public class StocksController {
    private static final Logger logger = LoggerFactory.getLogger(StocksController.class);

    private final StockService stockService;

    public StocksController(StockService stockService) {
        this.stockService = stockService;
    }

    @PreAuthorize("hasAnyRole('VIEWER','UPDATER')")
    @RequestMapping(value = "/stocks", method = RequestMethod.GET)
    public String stocks(ModelMap map) {
        logger.trace("Showing stocks page.");
        map.put("stocks", stockService.fetchStocks());
        return "stocks";
    }

    @PreAuthorize("hasAnyRole('UPDATER')")
    @RequestMapping(value = "/stocks", method = RequestMethod.POST)
    public String createStock(@RequestParam String name, @RequestParam Double price, ModelMap map) {
        logger.trace("Creating new stock named {}", name);
        try {
            //TODO this method can be replaced with AJAX version; and client can add, created STOCK to list.
            stockService.createStock(name, price);
        } catch (Exception e) {
            logger.error("Can not create stock", e);
            map.put("error", "Can not create stock.");
        }
        return stocks(map);
    }

    @PreAuthorize("hasAnyRole('UPDATER')")
    @RequestMapping(value = "/stocks/{stockId}", method = RequestMethod.PUT)
    @ResponseBody
    public Stock updateStock(@PathVariable("stockId") Long stockId,
                             @RequestParam(name = "value") Double value) {
        logger.debug("Updating stock value for {} as {}", stockId, value);
        return stockService.updateStock(stockId, value);
    }
}
