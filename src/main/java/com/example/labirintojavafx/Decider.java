package com.example.labirintojavafx;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Decider
{
    public int[][] matrix;

    private int manhattanDistance;
    private int totalDistance;
    public int grading;

    private int[] coordinates;

    public GridPane gridPane;
    Text[][] texts;

    public Decider(int[][] m, boolean randomizeDirections)
    {
        matrix = m;
        gridPane = new GridPane();

        if (!randomizeDirections)
        {
            createGridPane();
            return;
        }
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (matrix[i][j] != -1)
                {
                    matrix[i][j] = (int) (Math.random() * 4);
                }
            }
        }
        createGridPane();
        printMatrix();
    }

    private void createGridPane()
    {
        texts = new Text[5][5];
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                Text text;
                switch (matrix[i][j])
                {
                    case -1:
                        text = new Text(" "); // Label as Row and Column
                        break;
                    case 0:
                        text = new Text("⬅"); // Label as Row and Column
                        break;
                    case 1:
                        text = new Text("⬇"); // Label as Row and Column
                        break;
                    case 2:
                        text = new Text("➡"); // Label as Row and Column
                        break;
                    case 3:
                        text = new Text("⬆"); // Label as Row and Column
                        break;
                    default:
                        text = new Text(" ");
                }
                text.setFont(new Font(32));
                texts[i][j] = text;
                gridPane.add(text, i, j); // Add text to GridPane at (col, row)
            }
        }
    }

    public void updateGridPane()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                Text text = texts[i][j];
                switch (matrix[i][j])
                {
                    case -1:
                        text.setText(" "); // Label as Row and Column
                        break;
                    case 0:
                        text.setText("⬅"); // Label as Row and Column
                        break;
                    case 1:
                        text.setText("⬇"); // Label as Row and Column
                        break;
                    case 2:
                        text.setText("➡"); // Label as Row and Column
                        break;
                    case 3:
                        text.setText("⬆"); // Label as Row and Column
                        break;
                    default:
                        text.setText(" ");
                }
                System.out.println("updating grid pane");
                //text.setFont(new Font(32));
                //gridPane.add(text, i, j); // Add text to GridPane at (col, row)
            }
        }
    }

    public boolean runDecider()
    {
        coordinates = new int[2];

        for (totalDistance = 0; totalDistance < 25; totalDistance++)
        {
            updateCoordinates();
            if (!validCoordinates())
            {
                finishRun();
                return false;
            }
            else if (endAchieved())
            {
                return true;
            }
        }
        finishRun();
        return false;
    }

    public Decider mixDecider(Decider d)
    {
        int[][] m = new int[5][5];

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (Math.random() > 0.5)
                {
                    m[i][j] = matrix[i][j];
                }
                else
                {
                    m[i][j] = d.matrix[i][j];
                }
            }
        }
        return new Decider(m, false);
    }

    public void mutateDecider()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(matrix[i][j] != -1 && Math.random() > 0.05)
                {
                    matrix[i][j] = (int) (Math.random() * 4);
                }
            }
        }
    }

    private void updateCoordinates()
    {
        int direction = matrix[coordinates[0]][coordinates[1]];
        switch (direction)
        {
            case 0:
                coordinates[1] -= 1;
            case 1:
                coordinates[0] += 1;
            case 2:
                coordinates[1] += 1;
            case 3:
                coordinates[0] -= 1;
        }
    }

    private boolean validCoordinates()
    {
        if (coordinates[0] <= 0 || coordinates[0] >= 5 ||
                coordinates[1] <= 0 || coordinates[1] >= 5)
        {
            return false;
        }
        return true;
    }

    private boolean endAchieved()
    {
        if(coordinates[0] == 4 && coordinates[1] == 4)
        {
            return true;
        }
        return false;
    }

    private void finishRun()
    {
        manhattanDistance = coordinates[0] + coordinates[1];
        if (totalDistance == 25)
        {
            totalDistance = 0;
        }
        grading = manhattanDistance + totalDistance;
        updateGridPane();
    }

    public void printMatrix()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (matrix[i][j] == -1)
                    System.out.print(" ");
                else
                    System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

}
