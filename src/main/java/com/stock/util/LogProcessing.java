package com.stock.util;

import com.stock.model.Order;
import com.stock.model.OrderBook;
import com.stock.model.Trade;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogProcessing {

    public static void printAppendOrder(String symbol, Order order) {
        String capitalizedSymbol = order.getOrderType().name().substring(0, 1).toUpperCase()
            + order.getOrderType().name().substring(1).toLowerCase();
        System.out.println(
            "[" + LogProcessing.getFormattedDateTime(order.getCreatedAt()) + "] " +
            "Order with ID " + order.getOrderId() + " added: " +
            symbol + " " + capitalizedSymbol + " " +
            order.getQuantity() + " @ " + order.getPrice()
        );
    }

    public static void printTrade(Trade trade) {
        System.out.println(
            "[" + LogProcessing.getFormattedDateTime(trade.getCreatedAt()) + "] " +
            "New execution with ID " + trade.getOrderId() + ": " +
            trade.getSymbol() + " " + trade.getTradeQuantity() + " @ " + trade.getTradePrice() +
            " (orders " + trade.getBuyOrder().getOrderId() + " and " + trade.getSellOrder().getOrderId() + ")"
        );
    }

    public static void printVerbose() {
        System.out.println("Trade Ledger: ");
        GlobalContext.getInstance().getTradeLedger().getTrades().forEach(System.out::println);
        System.out.println("Order Books: ");
        for (OrderBook orderBook : GlobalContext.getInstance().getOrderBooks()) {
            System.out.println(orderBook.getSymbol());
            orderBook.getOrders().forEach(System.out::println);
        }
    }

    public static String getFormattedDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return simpleDateFormat.format(date);
    }
}
