package com.transactions.app;

/**
 * Non-JavaFX launcher required for shaded JAR execution.
 * (JavaFX Application subclass cannot be the main-class of a fat JAR.)
 */
public class Launcher {
	public static void main(String[] args) {
		// Fix garbled / blurry text rendering on Linux (LCD sub-pixel AA bug)
		System.setProperty("prism.lcdtext", "false");
		System.setProperty("prism.text", "t2k");
		TransactionsApp.main(args);
	}
}
