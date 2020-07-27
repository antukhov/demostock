package com.stock.util;

import com.stock.model.Order;
import com.stock.model.OrderBook;
import com.stock.model.Trade;
import com.sun.xml.internal.ws.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogProcessing {

    public static void printAppendOrder(String symbol, Order order) {
        System.out.println(
            "[" + LogProcessing.getFormattedDateTime(order.getCreatedAt()) + "] " +
            "Order with ID " + order.getOrderId() + " added: " +
            symbol + " " + StringUtils.capitalize(order.getOrderType().name().toLowerCase()) + " " +
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
