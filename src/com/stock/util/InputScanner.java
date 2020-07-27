package com.stock.util;

import com.stock.controller.OrderController;
import com.stock.model.Order;

import java.util.List;
import java.util.Scanner;

public class InputScanner extends Thread {

    final static String ERROR_MSG = "Unsupported command";

    final static GlobalContext globalContext = GlobalContext.getInstance();

    /**
     * Input / new lines scanner. The entry CLI point of processing
     *
     */
    @Override
    public void run() {
        System.out.println("Please type your command below this line: ");
        Scanner inboundInput = new Scanner(System.in);
        while (inboundInput.hasNext() && !globalContext.isQuitIntercepted()) {
            String line = inboundInput.nextLine();
            if (InputProcessing.validateInput(line)) {
                processCommand(line.trim());
            } else if(line.equals("verbose")) {
                LogProcessing.printVerbose();
            } else if(line.equals("quit")) {
                break;
            } else {
                System.out.println(ERROR_MSG);
            }
        }

        globalContext.setQuitIntercepted(true);
        try {
            inboundInput.close();
        } catch (Exception ignored) {}
        Thread.currentThread().interrupt();
    }

    /**
     * Process the method, controller selection
     *
     * @param command - input that ready to deserialize
     */
    private static void processCommand(String command) {
        List<String> variableList = InputProcessing.getListFromString(command);

        String symbol = variableList.get(1).toUpperCase();
        Order.Type orderType = variableList.get(2).equals("B") ? Order.Type.BUY : Order.Type.SELL;
        Integer quantity = new Integer(variableList.get(3));
        Float price = new Float(variableList.get(4));

        switch (variableList.get(0).toLowerCase()) {
            case "add":
                OrderController
                    .addOrderPriceLimit(symbol, new Order(orderType, price, quantity, true));
                break;
            case "cancel":
                OrderController
                    .removeOrder(symbol, new Order(orderType, price, quantity, false), false);
                break;
        }

    }
}
