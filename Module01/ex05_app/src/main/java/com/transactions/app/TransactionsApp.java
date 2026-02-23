package com.transactions.app;

import com.transactions.controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * JavaFX Application entry point.
 */
public class TransactionsApp extends Application {

    @Override
    public void start(Stage stage) {
        MainController controller = new MainController();
        BorderPane root = controller.buildUI();

        Scene scene = new Scene(root, 1060, 680);
        scene.getStylesheets().add(
            getClass().getResource("/styles/theme.css").toExternalForm()
        );

        /* Force default cursor on scene + root */
        scene.setCursor(Cursor.DEFAULT);
        root.setCursor(Cursor.DEFAULT);

        stage.setTitle("TransactPro — Transaction Management System");
        stage.setScene(scene);
        stage.setMinWidth(860);
        stage.setMinHeight(560);

        /*
         * Workaround for JavaFX-on-Linux fullscreen cursor bug:
         * Re-apply cursor after each fullscreen toggle with a small delay
         * so the window manager has time to finish the transition.
         */
        stage.fullScreenProperty().addListener((obs, wasFS, isFS) -> {
            Timer timer = new Timer(true);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        scene.setCursor(Cursor.NONE);
                        scene.setCursor(Cursor.DEFAULT);
                        root.setCursor(Cursor.DEFAULT);
                        root.requestLayout();
                    });
                }
            }, 250);
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
