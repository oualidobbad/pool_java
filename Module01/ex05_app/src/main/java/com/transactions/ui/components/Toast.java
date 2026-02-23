package com.transactions.ui.components;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Lightweight toast-notification system.
 * Call show() with the parent StackPane to overlay a message.
 */
public class Toast {

    public enum Type { SUCCESS, ERROR, INFO }

    public static void show(StackPane parent, String message, Type type) {
        Label toast = new Label(message);
        toast.getStyleClass().addAll("toast", cssClass(type));
        toast.setMaxWidth(480);
        toast.setWrapText(true);
        toast.setMouseTransparent(true);

        StackPane.setAlignment(toast, Pos.BOTTOM_CENTER);
        toast.setTranslateY(-20);
        toast.setOpacity(0);
        parent.getChildren().add(toast);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(250), toast);
        fadeIn.setToValue(1);

        TranslateTransition slideUp = new TranslateTransition(Duration.millis(250), toast);
        slideUp.setFromY(0);
        slideUp.setToY(-20);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), toast);
        fadeOut.setToValue(0);

        SequentialTransition seq = new SequentialTransition(
                new ParallelTransition(fadeIn, slideUp),
                pause,
                fadeOut
        );
        seq.setOnFinished(e -> parent.getChildren().remove(toast));
        seq.play();
    }

    private static String cssClass(Type t) {
        switch (t) {
            case SUCCESS: return "toast-success";
            case ERROR:   return "toast-error";
            default:      return "toast-info";
        }
    }
}
