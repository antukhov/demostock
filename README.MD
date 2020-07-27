## Test Task: Stock Market Simulator 

Your challenge is to create a stock market simulator.
Clients of the stock market will interact with it using a Java API.

The stock market should have these components:

- **1 x Matching engine**
    
    o "Balances" (like in "balancing the book", i.e. crossing buys and sells) the order books every 1 second.
- **N x Order book(s)**
    
    o Contains all orders (buy and sell) for a certain stock.
    
    o Each order book has a unique code, called symbol.
- **1 x Trade ledger**
    
    o Contains all trades that happen on all order books.
- **1 x Trading gateway**
    
    o Allows a client to trade on the market. Exposes these functions:
    
       ▪ Add order
       
       ▪ Cancel order
       
    o The orders arriving at the trading gateway need to be put into the correct order book, based
       on the symbol.

    o Note: real exchanges can have multiple trading gateways, but here we will have just one.

### Hints

- The client enters orders via the Trading Gateway.
- You will need the following additional classes:

    o **Order** : Represents an order to buy or sell a certain stock, at a certain price and for a certain
       quantity.
       
          ▪ There are many different order types; here you only need to implement limit (price)
             orders.
             
          ▪ The stock is represented by the order book’s symbol. For example, AAPL for Apple.
    
    o **Trade** : Represents the details of two opposite (buy and sell) orders crossing with each other.
- The list of valid symbols (Order Book codes) can be hard-coded, or dynamically created when an order with a new symbol is added, or the client can dynamically be able to create new symbols.
- Matching follows a price/time priority. Orders are ranked according to price (better price having higher priority), and same-price orders are ranked according to the time when they were entered (older orders having higher priority).
- Two orders in an Order Book are matched if the Buy order's price is equal to or higher than the Sell order's price.
- In such a case, when two orders cross, a Trade (also called Fill or Execution) happens – think about what price is used for the trade. Trades are stored in the Trade Ledger.
- Orders can be fully filled (total quantity), or partially filled (only some part of the total quantity).
- Order Books are balanced until there are no more matching orders in them.

### Additional details

- Must be implemented in Java.
- Should compile and run without having to manually download/install anything (Maven or Ant can be used,
    if needed).
- You can use external libraries if you want, but normally you shouldn’t require them for this challenge.
- Focus should be on code quality and design, not on number/detail of features or performance.
- Use common sense when deciding the level of details, no need to complicate the implementation (e.g.
    order’s price and quantity can just be integers – keep it simple).
- The program should run as a single application. You don’t need to use external messaging layers (e.g. MQ).
- A UI is not required, just command-line is sufficient.
- The application should produce some output, with timestamps, showing the events, e.g. the client
    entering orders, trades happening, and so on – you can just have the components print events as they
    happen inside each component. All changes to an order’s state should be shown in the console.

```
Sample output:
```
```
> add GOOG B 100 50

[2020-05-01T12:34:56+00:00] Order with ID 1 added: GOOG Buy 100 @ 50

> add GOOG S 30 50

[2020-05-01T12:34:58+00:00] Order with ID 2 added: GOOG Sell 30 @ 50

[2020-05-01T12:34:59+00:00] New execution with ID 1: GOOG 30 @ 50 (orders 1 and 2)
```

**This output shows adding 2 orders that cross:**

- The first order is Buy 100 GOOG shares at price 50.
- The second order is Sell 30 GOOG shares at price 50.
- The trade happens for 30 shares at price 50.

## Result:

#### How to build / run / test

Change directory to the project root: ```cd /%PROJECT_ROOT%```

Build project: ```mvn package```

Run application: ```mvn exec:java```

Run OrderPriceLimit test: ```mvn -Dtest=OrderPriceLimitLogicChecker test```

#### Author

Alex Antukhov

