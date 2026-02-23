package com.transactions.ui.components;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Reusable animation helpers.
 */
public class Animations {

    /** Fade-in + slight slide-up for panel switching. */
    public static void fadeSlideIn(Node node) {
        node.setOpacity(0);
        node.setTranslateY(18);

        FadeTransition ft = new FadeTransition(Duration.millis(300), node);
        ft.setFromValue(0);
        ft.setToValue(1);

        TranslateTransition tt = new TranslateTransition(Duration.millis(300), node);
        tt.setFromY(18);
        tt.setToY(0);

        new ParallelTransition(ft, tt).play();
    }

    /** Quick subtle fade. */
    public static void fadeIn(Node node) {
        node.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(200), node);
        ft.setToValue(1);
        ft.play();
    }
}
