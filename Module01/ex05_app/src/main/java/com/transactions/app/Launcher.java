package com.transactions.app;

/**
 * Non-JavaFX launcher required for shaded JAR execution.
 * (JavaFX Application subclass cannot be the main-class of a fat JAR.)
 */
public class Launcher {
    public static void main(String[] args) {
        TransactionsApp.main(args);
    }
}
