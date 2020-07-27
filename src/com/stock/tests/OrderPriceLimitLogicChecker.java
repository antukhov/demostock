package com.stock.tests;

import com.stock.controller.OrderController;
import com.stock.model.Order;
import com.stock.util.GlobalContext;
import com.stock.util.MatchTaskScanner;

import java.util.Timer;

/**
 * The Consistency checker for Order Price Limit (demo dataset)
 * Didn't use the JUnit as test coverage wasn't the requirement of test task
 */
public class OrderPriceLimitLogicChecker {

    final static GlobalContext globalContext = GlobalContext.getInstance();

    public static void main(String[] args) {
        // id 1
        OrderController.addOrderPriceLimit("AAPL", new Order(Order.Type.BUY, 35.0F, 30, true));
        // id 2
        OrderController.addOrderPriceLimit("AAPL", new Order(Order.Type.BUY, 30.0F, 40, true));
        // id 3
        OrderController.addOrderPriceLimit("AAPL", new Order(Order.Type.BUY, 60.0F, 100, true));

        // id 4
        OrderController.addOrderPriceLimit("AAPL", new Order(Order.Type.SELL, 30.0F, 50, true));
        // id 5
        OrderController.addOrderPriceLimit("AAPL", new Order(Order.Type.SELL, 70.0F, 70, true));
        // id 6
        OrderController.addOrderPriceLimit("AAPL", new Order(Order.Type.SELL, 25.0F, 40, true));

        // id 7
        OrderController.addOrderPriceLimit("GOOG", new Order(Order.Type.SELL, 10.0F, 50, true));
        // id 8
        OrderController.addOrderPriceLimit("GOOG", new Order(Order.Type.BUY, 30.0F, 50, true));

        // According to the rules of priority, the pairs should be:
        // 1 + 6 (ea, 25), 2 + 6 (ea, 25), 2 + 4 (ea, 30), 3 + 4 (ea, 30), 8 + 7 (ea, 10)
        // The rest of quantity for each order: 1: 0; 2: 0; 3: 80; 4: 0; 5: 70; 6: 0; 7: 0, 8: 0
        // Assumption: the matcher process the Orders above only after all of them placed in the Order list of AAPL OrderBook.

        Timer matchTaskExplorerTimer = new Timer();
        matchTaskExplorerTimer.schedule(new MatchTaskScanner(matchTaskExplorerTimer), 0);

        globalContext.setQuitIntercepted(true);
    }
}
