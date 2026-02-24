package com.transactions.ui.components;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Reusable animation helpers.
 */
public class Animations {

	/** Fade-in + slight slide-up for panel switching. */
	public static void fadeSlideIn(Node node) {
		node.setOpacity(0);
		node.setTranslateY(12);

		FadeTransition ft = new FadeTransition(Duration.millis(250), node);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.setInterpolator(Interpolator.EASE_OUT);

		TranslateTransition tt = new TranslateTransition(Duration.millis(250), node);
		tt.setFromY(12);
		tt.setToY(0);
		tt.setInterpolator(Interpolator.EASE_OUT);

		new ParallelTransition(ft, tt).play();
	}

	/** Cross-fade: old fades out while new fades in simultaneously. */
	public static void crossFade(Node oldNode, Node newNode, StackPane parent) {
		newNode.setOpacity(0);
		newNode.setTranslateY(0);
		StackPane.setAlignment(newNode, Pos.TOP_LEFT);
		parent.getChildren().add(newNode);

		FadeTransition fadeOut = new FadeTransition(Duration.millis(100), oldNode);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		fadeOut.setInterpolator(Interpolator.EASE_IN);
		fadeOut.setOnFinished(e -> parent.getChildren().remove(oldNode));

		FadeTransition fadeIn = new FadeTransition(Duration.millis(100), newNode);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
		fadeIn.setInterpolator(Interpolator.EASE_OUT);

		new ParallelTransition(fadeOut, fadeIn).play();
	}

	/** Quick subtle fade. */
	public static void fadeIn(Node node) {
		node.setOpacity(0);
		FadeTransition ft = new FadeTransition(Duration.millis(200), node);
		ft.setToValue(1);
		ft.setInterpolator(Interpolator.EASE_OUT);
		ft.play();
	}
}
