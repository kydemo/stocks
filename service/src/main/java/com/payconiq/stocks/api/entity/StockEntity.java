package com.payconiq.stocks.api.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Stock entity
 */
@Entity
@Table(name = "stock")
public class StockEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Column(name = "name", length = 16, unique = true)
    private String name;

    @Column
    private BigDecimal currentPrice;

    @Column(insertable = false)
    private Date lastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
