package com.payconiq.stocks.webapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * All properties (configurations) for application can be reached from this component.
 *
 * @author Kaan Yamanyar
 */
@Component
@ConfigurationProperties(prefix = "payconiq.web.properties")
public class ApplicationProperties {
    private String stocksTopicName;
    private String apiUrl;

    public String getStocksTopicName() {
        return stocksTopicName;
    }

    public void setStocksTopicName(String stocksTopicName) {
        this.stocksTopicName = stocksTopicName;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
