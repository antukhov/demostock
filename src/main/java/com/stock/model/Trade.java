package com.stock.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Trade. This class represents all details of trade deal
 * Didn't make the builder here, but in this case it could be helpful
 */
public class Trade {

    private static final AtomicInteger counter = new AtomicInteger(1);
    private final Integer orderId;
    final private Date createdAt;
    final private String symbol;
    final private Order sellOrder;
    final private Order buyOrder;
    private Float tradePrice;
    private Integer tradeQuantity;

    public Trade(String symbol, Order buyOrder, Order sellOrder, Float tradePrice, Integer tradeQuantity) {
        this.orderId = counter.getAndIncrement();
        this.createdAt = new Date();
        this.symbol = symbol;
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
        this.tradePrice = tradePrice;
        this.tradeQuantity = tradeQuantity;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getSymbol() {
        return symbol;
    }

    public Order getSellOrder() {
        return sellOrder;
    }

    public Order getBuyOrder() {
        return buyOrder;
    }

    public Float getTradePrice() {
        return tradePrice;
    }

    public Integer getTradeQuantity() {
        return tradeQuantity;
    }

    @Override
    public String toString() {
        return "Trade{" +
            "orderId=" + orderId +
            ", createdAt=" + createdAt +
            ", symbol='" + symbol + '\'' +
            ", sellOrder=" + sellOrder +
            ", buyOrder=" + buyOrder +
            ", tradePrice=" + tradePrice +
            '}';
    }
}
