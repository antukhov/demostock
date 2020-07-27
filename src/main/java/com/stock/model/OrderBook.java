package com.stock.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The type Order book. Contains queue of Orders with pass-through ID
 */
public class OrderBook implements Serializable {

    private final String symbol;

    private final ConcurrentLinkedQueue<Order> orders;

    public OrderBook(String symbol, Order firstOrder) {
        this.symbol = symbol;
        this.orders = new ConcurrentLinkedQueue<Order>() {{ add(firstOrder); }};
    }

    public ConcurrentLinkedQueue<Order> getOrders() {
        return orders;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderBook orderBook = (OrderBook) o;
        return Objects.equals(symbol, orderBook.symbol);
    }

    public boolean equals(String o) {
        if (o == null) return false;
        return Objects.equals(symbol, o);
    }

    @Override
    public int hashCode() {
        return symbol != null ? symbol.hashCode() : 0;
    }

}
