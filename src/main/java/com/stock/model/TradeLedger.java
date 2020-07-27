package com.stock.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Trade Ledger here is thread-safe journal of processed operations
 */
public class TradeLedger {

    private final Collection<Trade> trades = Collections.synchronizedCollection(new LinkedList<>());

    public Collection<Trade> getTrades() {
        return trades;
    }

    public void addTrade(Trade trade) {
        this.trades.add(trade);
    }

}
