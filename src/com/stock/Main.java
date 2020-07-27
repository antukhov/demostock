package com.stock;

import com.stock.util.InputScanner;
import com.stock.util.MatchTaskScanner;

import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        new Thread(new InputScanner()).start();
        Timer matchTaskExplorerTimer = new Timer();
        matchTaskExplorerTimer.schedule(new MatchTaskScanner(matchTaskExplorerTimer), 0, 5000);
    }
}
