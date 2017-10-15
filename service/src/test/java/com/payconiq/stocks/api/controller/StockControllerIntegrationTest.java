package com.payconiq.stocks.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stocks.api.ServiceApplication;
import com.payconiq.stocks.client.StockCreateRequest;
import com.payconiq.stocks.client.StockPriceUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({ServiceApplication.class})
public class StockControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_return_list() throws Exception {
        mockMvc.perform(get("/stocks/")).andExpect(status().isOk())
                .andExpect(jsonPath("[0]name").value("TEST.A"))
                .andExpect(jsonPath("[1]name").value("TEST.B"));
    }

    @Test
    public void should_return_specific_stock() throws Exception {

        mockMvc.perform(get("/stocks/1")).andExpect(status().isOk())
                .andExpect(jsonPath("name").value("TEST.A"));
    }

    @Test
    public void should_throw_404_if_stock_not_found() throws Exception {
        mockMvc.perform(get("/stocks/341")).andExpect(status().isNotFound());
    }

    @Test
    public void should_update_stock() throws Exception {
        //given
        long stockId = 1L;
        StockPriceUpdateRequest request = new StockPriceUpdateRequest();
        request.setId(stockId);
        request.setCurrentPrice(BigDecimal.ONE);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(request);

        //when
        mockMvc.perform(put("/stocks/1")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void should_throw_exception_with_wrong_location() throws Exception {
        //given
        long stockId = 99L;
        StockPriceUpdateRequest request = new StockPriceUpdateRequest();
        request.setId(stockId);
        request.setCurrentPrice(BigDecimal.ONE);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(request);

        //when
        mockMvc.perform(put("/stocks/1")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_throw_not_found_on_update_request_to_non_existing() throws Exception {
        //given
        long stockId = 499L;
        StockPriceUpdateRequest request = new StockPriceUpdateRequest();
        request.setId(stockId);
        request.setCurrentPrice(BigDecimal.ONE);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(request);

        //when
        mockMvc.perform(put("/stocks/499")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_create_stock() throws Exception {
        //given
        StockCreateRequest request = new StockCreateRequest();
        request.setName("Alfa");
        request.setCurrentPrice(BigDecimal.TEN);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(request);

        //when
        mockMvc.perform(post("/stocks/")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andExpect(jsonPath("name").value("Alfa"));
    }
}