import java.util.*;


public class Maze
{
    public int[][] matrix;
    List<Decider> deciders;
    int iterations;
    boolean finished;

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

        for (int i = 0; i < 60; i++)
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

        for (int i = 0; i < 30; i++)
        {
            Decider d1 = deciders.get(i);
            Decider d2 = deciders.get((int) (Math.random() * 30));
            deciders.set(30 + i, d1.mixDecider(d2));
            // deciders.set(30 + i, deciders.get(i).mixDecider(deciders.get((int) (Math.random() * 30))));
        }

        // mutate
        for (Decider d : deciders)
        {
            if (Math.random() > 0.05)
                d.mutateDecider();
        }
    }

    public static void main(String[] s)
    {
        Maze maze = new Maze();
        maze.printLab();
        maze.iterations = 0;
        while (!maze.finished)
        {
            System.out.println("Running");
            maze.run();
            maze.iterations++;
        }
    }
}
