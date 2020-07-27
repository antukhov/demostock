package com.stock.model;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {

    private static final AtomicInteger counter = new AtomicInteger(1);

    public enum Type {
        BUY,
        SELL
    }

    public enum Strategy {
        LIMIT_ORDER
    }

    private final Integer orderId;

    private Date createdAt;

    private Strategy orderStrategy;

    private Type orderType;

    private Float price;

    private Integer quantity;

    public Order(Type orderType, Float price, Integer quantity, boolean persistent) {
        this.orderId = persistent ? counter.getAndIncrement() : 0;
        this.createdAt = new Date();
        this.orderStrategy = Strategy.LIMIT_ORDER;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
    }

    public Type getOrderType() {
        return orderType;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Strategy getOrderStrategy() {
        return orderStrategy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderType == order.orderType &&
                Objects.equals(price, order.price) &&
                Objects.equals(quantity, order.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderType, price, quantity);
    }

    @Override
    public String toString() {
        return "Order {" +
                "orderId=" + orderId +
                ", orderCreatedAt=" + createdAt +
                ", orderStrategy=" + orderStrategy +
                ", orderType=" + orderType +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
