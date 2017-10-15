package com.payconiq.stocks.api.repository;

import com.payconiq.stocks.api.entity.StockEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface StockRepository extends CrudRepository<StockEntity, Long> {

    @Modifying
    @Query("UPDATE StockEntity s SET s.currentPrice = :currentPrice, s.lastUpdate = CURRENT_TIMESTAMP() WHERE s.id = :id")
    int updateStockPrice(@Param("id") Long id,
                         @Param("currentPrice") BigDecimal currentPrice);

}
