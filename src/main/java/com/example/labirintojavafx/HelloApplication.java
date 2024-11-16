package com.example.labirintojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        GridPane gridPane = new GridPane();

        // Populate the GridPane with Text nodes
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 5; col++) {
                Text text = new Text("⬅"); // Label as Row and Column
                text.setFont(new Font(32));
                gridPane.add(text, col, row); // Add text to GridPane at (col, row)
            }
        }

        // Set up the Scene and Stage
        Scene scene = new Scene(gridPane, 300, 300);
        stage.setTitle("5x5 Grid with Text");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}