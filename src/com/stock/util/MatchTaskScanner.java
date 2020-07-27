package com.stock.util;
import com.stock.controller.OrderController;
import com.stock.controller.TradeController;
import com.stock.model.Order;
import com.stock.model.OrderBook;
import com.stock.model.Trade;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Match task explorer.
 */
public class MatchTaskScanner extends TimerTask {

    private final Set<OrderBook> orderBooks = GlobalContext.getInstance().getOrderBooks();
    private final Timer timer;

    public MatchTaskScanner(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        if(GlobalContext.getInstance().isQuitIntercepted()) {
            timer.cancel();
            System.out.println("Application has been finished");
            Thread.currentThread().interrupt();
        }

        orderBooks.forEach(orderBook -> {
            if(orderBook.getOrders().size() > 1) {
                Optional<Trade> trade;
                do {
                    trade = getFullyCrossedOrdersPriceDescTimeAsc(orderBook.getOrders(), orderBook.getSymbol());
                } while (trade.isPresent());
            }
        });
    }

    /**
     * Get fully-crossed orders.
     * The basic strategy: trade created only if quantities are equals and price not less/more than required
     * */
    private Optional<Trade> getFullyCrossedOrdersPriceDescTimeAsc(ConcurrentLinkedQueue<Order> orderQueue, String symbol) {
        for (Order currentOrder : orderQueue) {
            if (currentOrder.getOrderType().equals(Order.Type.BUY)
                    && currentOrder.getOrderStrategy().equals(Order.Strategy.LIMIT_ORDER)) {
                Optional<Order> matchOrder = orderQueue
                    .stream()
                    // to find best trade for BUY order: keep only SELL orders with <= price and any quantity
                    .filter(tradeOrder -> tradeOrder.getOrderType().equals(Order.Type.SELL)
                        && tradeOrder.getPrice() <= currentOrder.getPrice()
                    )
                    // sort the orders by price ASC and by date/time DESC and get the best one
                    .max(Comparator.comparing(Order::getPrice).reversed().thenComparing(Order::getCreatedAt));
                // if crossed SELL order has been found
                if (matchOrder.isPresent()) {
                    Trade newTrade = generateNewTrade(currentOrder, matchOrder.get(), symbol);
                    TradeController.addTrade(newTrade);
                    // quit from the method after processing is completed
                    return Optional.of(newTrade);
                }
            }
        }
        return Optional.empty();
    }

    /*
    * Generate New Trade and process the Orders participated in that. Should be wrapped into transaction
    * */
    private Trade generateNewTrade(Order sourceOrder, Order targetOrder, String symbol) {
        Float tradePrice = Math.min(sourceOrder.getPrice(), targetOrder.getPrice());
        Integer tradeQuantity;
        if(sourceOrder.getQuantity() < targetOrder.getQuantity()) {
            tradeQuantity = targetOrder.getQuantity() - sourceOrder.getQuantity();
            targetOrder.setQuantity(tradeQuantity);
            OrderController.removeOrder(symbol, sourceOrder, false);
        } else if(sourceOrder.getQuantity() > targetOrder.getQuantity()) {
            tradeQuantity = sourceOrder.getQuantity() - targetOrder.getQuantity();
            sourceOrder.setQuantity(targetOrder.getQuantity() - sourceOrder.getQuantity());
            OrderController.removeOrder(symbol, targetOrder, false);
        } else {
            tradeQuantity = sourceOrder.getQuantity();
            OrderController.removeOrder(symbol, sourceOrder, false);
            OrderController.removeOrder(symbol, targetOrder, false);
        }
        return new Trade(symbol, sourceOrder, targetOrder, tradePrice, tradeQuantity);
    }
}
