package com.example.labirintojavafx;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;


public class Maze extends Application
{
    public int[][] matrix;
    List<Decider> deciders;
    int decidersAmount;
    int iterations;
    boolean finished;
    boolean readyToUpdate;

    public Maze()
    {
        matrix = new int[][] {
                {0, 0, 0, -1, 0},
                {0, -1, -1, -1, 0},
                {0, 0, 0, 0, 0},
                {0, -1, 0, -1, 0},
                {0, -1, 0, 0, 0},
        };

        deciders = new ArrayList<Decider>();

        decidersAmount = 10;

        for (int i = 0; i < decidersAmount; i++)
        {
            deciders.add(new Decider(matrix, true));
        }
    }

/*    private void generateRandomLab()
    {
        int[] randomNumbers = new int[100];
        for (int i = 0; i < 100; i++)
        {
            randomNumbers[i] = i;
            matrix[i / 10][i % 10] = 0;
        }
        shuffleArray(randomNumbers);
        for (int i = 0; i < blackSquaresAmount; i++)
        {
            matrix[randomNumbers[i] / 10][randomNumbers[i] % 10] = -1;
        }
    }
 */

    static void shuffleArray(int[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public void printLab()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    private void run()
    {
        // running deciders
        for (Decider d : deciders)
        {
            if (d.runDecider())
            {
                System.out.println("Finished with " + iterations + " iterations");
                finished = true;
                return;
            }
        }

        // training deciders
        Collections.sort(deciders, Comparator.comparing(d -> d.grading));
        deciders.get(0).printMatrix();

        for (int i = 0; i < decidersAmount/2; i++)
        {
            Decider d1 = deciders.get(i);
            Decider d2 = deciders.get((int) (Math.random() * decidersAmount/2));
            deciders.set(decidersAmount/2 + i, d1.mixDecider(d2));
            // deciders.set(30 + i, deciders.get(i).mixDecider(deciders.get((int) (Math.random() * 30))));
        }

        // mutate
        for (Decider d : deciders)
        {
            if (Math.random() > 0.05)
                d.mutateDecider();
        }

    }
    public void start(Stage stage) throws IOException {
        GridPane gridPane = new GridPane();
        Maze maze = new Maze();

        // Populate the GridPane with Text nodes
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 5; col++) {
                gridPane.add(deciders.get(row * 5 + col).gridPane, col, row);
            }
        }

        // Set up the Scene and Stage
        Scene scene = new Scene(gridPane, 300, 300);

        scene.setOnKeyPressed(event -> {
            readyToUpdate = true;
        });

        stage.setTitle("5x5 Grid with Text");
        stage.setScene(scene);
        stage.show();

       /* // Use Timeline to update periodically
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (!maze.finished && readyToUpdate) {
                System.out.println("Running");
                maze.run();
                maze.iterations++;
                readyToUpdate = false;
                // Update the UI based on the maze state here
            }
        }));*/

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!maze.finished && readyToUpdate) {
                    System.out.println("Running");
                    maze.run();
                    maze.iterations++;
                    readyToUpdate = false;

                    for (Node node : gridPane.getChildren()) {
                        gridPane.getChildren().remove(node);
                    }
                    for (int row = 0; row < 2; row++) {
                        for (int col = 0; col < 5; col++) {
                            gridPane.add(deciders.get(row * 5 + col).gridPane, col, row);
                        }
                    }
                    // Update the UI based on the maze state here
                }
            }
        };

        gameLoop.start();
        stage.show();

        //timeline.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        //timeline.play();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
