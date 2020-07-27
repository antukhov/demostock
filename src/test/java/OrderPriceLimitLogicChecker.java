import com.stock.controller.OrderController;
import com.stock.model.Order;
import com.stock.model.Trade;
import com.stock.util.GlobalContext;
import com.stock.util.MatchTaskScanner;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The Consistency checker for Order Price Limit (with demo dataset)
 */
public class OrderPriceLimitLogicChecker {

    final static GlobalContext globalContext = GlobalContext.getInstance();

    @Test
    public void orderPriceLimitLogicChecker() {

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

        MatchTaskScanner matchTaskScanner = new MatchTaskScanner(null);
        matchTaskScanner.run();

        // According to the rules of priority, the pairs should be:
        // 1 + 6 (ea, 25), 2 + 6 (ea, 25), 2 + 4 (ea, 30), 3 + 4 (ea, 30), 8 + 7 (ea, 10)
        // The rest of quantity for each order: 1: 0; 2: 0; 3: 80; 4: 0; 5: 70; 6: 0; 7: 0, 8: 0
        // Assumption: the matcher process the Orders above only after all of them placed in the Order list of AAPL OrderBook.

        int[][] factTransactionsArr = new int[globalContext.getTradeLedger().getTrades().size()][2];
        int counter = 0;
        // fill the matrix with IDs of processed orders
        for(Trade trade: globalContext.getTradeLedger().getTrades()) {
            factTransactionsArr[counter][0] = trade.getBuyOrder().getOrderId();
            factTransactionsArr[counter][1] = trade.getSellOrder().getOrderId();
            counter++;
        }
        // expected dataset for comparison
        int[][] planTransactionsArr = {{1,6},{2,6},{2,4},{3,4},{8,7}};

        assertTrue(Arrays.deepEquals(planTransactionsArr, factTransactionsArr));
    }
}
