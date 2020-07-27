package com.stock.controller;

import com.stock.model.Order;
import com.stock.model.OrderBook;
import com.stock.util.GlobalContext;
import com.stock.util.LogProcessing;

import java.util.Set;

public class OrderController {

    private static final Set<OrderBook> orderBooks = GlobalContext.getInstance().getOrderBooks();

    public static void addOrderPriceLimit(String symbol, Order order) {

        if(orderBooks.stream().anyMatch(orderBook -> orderBook.equals(symbol))) {
            orderBooks.stream().filter(x -> x.equals(symbol)).findAny().ifPresent(y -> y.getOrders().add(order));
        } else {
            orderBooks.add(new OrderBook(symbol.toUpperCase(), order));
        }

        LogProcessing.printAppendOrder(symbol, order);
    }

    public static void removeOrder(String symbol, Order order, boolean notify) {

        if(orderBooks.stream().anyMatch(orderBook -> orderBook.equals(symbol))) {
            orderBooks
                .stream()
                .filter(orderBook -> orderBook.equals(symbol))
                .findFirst().ifPresent(orderBook -> orderBook.getOrders()
                .remove(order));
        } else {
            System.out.println("Order " + order.getOrderId() + " doesn't exist");
        }

        if(notify) {
            for (OrderBook orderBook : orderBooks) {
                System.out.println(orderBook.getSymbol());
                orderBook.getOrders().forEach(System.out::println);
            }
        }

    }

}