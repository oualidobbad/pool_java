package com.transactions.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Quick-builder for small stat cards (e.g. "Total Users: 5").
 */
public class StatCard {

    public static VBox create(String label, String value) {
        Label valLbl = new Label(value);
        valLbl.getStyleClass().add("stat-value");

        Label nameLbl = new Label(label.toUpperCase());
        nameLbl.getStyleClass().add("stat-label");

        VBox box = new VBox(4, valLbl, nameLbl);
        box.getStyleClass().add("stat-card");
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPrefWidth(180);
        return box;
    }
}
