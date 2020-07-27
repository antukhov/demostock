package com.stock.util;

import com.stock.model.OrderBook;
import com.stock.model.TradeLedger;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The Global context. Singleton for Data Storage and global variables
 */
public final class GlobalContext {

    Set<OrderBook> orderBooks = Collections.synchronizedSet(new HashSet<>());
    TradeLedger tradeLedger = new TradeLedger();

    volatile boolean quitIntercepted = false;
    private static GlobalContext INSTANCE;

    private GlobalContext() {
    }

    public Set<OrderBook> getOrderBooks() {
        return this.orderBooks;
    }

    public TradeLedger getTradeLedger() {
        return this.tradeLedger;
    }

    public boolean isQuitIntercepted() {
        return quitIntercepted;
    }

    public void setQuitIntercepted(boolean quitIntercepted) {
        this.quitIntercepted = quitIntercepted;
    }

    @Override
    protected Object clone() {
        return this;
    }

    public static GlobalContext getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GlobalContext();
        }

        return INSTANCE;
    }
}
