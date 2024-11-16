public class Decider
{
    public int[][] matrix;

    private int manhattanDistance;
    private int totalDistance;
    public int grading;

    private int[] coordinates;

    public Decider(int[][] m, boolean randomizeDirections)
    {
        matrix = m;
        if (!randomizeDirections)
        {
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
        printMatrix();
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
