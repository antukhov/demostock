package com.stock.controller;

import com.stock.model.Trade;
import com.stock.model.TradeLedger;
import com.stock.util.GlobalContext;
import com.stock.util.LogProcessing;

public class TradeController {

    private static final TradeLedger tradeLedger = GlobalContext.getInstance().getTradeLedger();

    public static void addTrade(Trade trade) {
        tradeLedger.addTrade(trade);
        LogProcessing.printTrade(trade);
    }
}
