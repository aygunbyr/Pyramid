import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner; // Import the Scanner class to read text files

public class Pyramid {

    private int[][] matrix;
    private static int rows, cols;
    private ArrayList < Integer > sums;

    /**
     * Default constructor that initializes an empty integer matrix
     * @param rows Number of rows
     * @param cols Number of columns
     */
    public Pyramid(int _rows, int _cols) {
        // Create new integer matrix
        matrix = new int[rows][cols];
        rows = _rows;
        cols = _cols;

        // Create array list for sums
        sums = new ArrayList < Integer > ();


        // Fill matrix with minus 1s so it is empty
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = -1;
            }
        }
    }

    public static void main(String[] args) {
        Pyramid p = new Pyramid("filename.txt", " ");
        p.print();
        ArrayList < Integer > path = new ArrayList < Integer > ();
        int y = getMiddleY();
        p.traverse(0, y, 0, path);
        //ot2.print_sums();
        //System.out.println("\nMax = " + p.getMax());
    }

    public static int getMiddleY() {
        return cols / 2;
    }



    /**
     * Constructor that reads file and creates integer matrix with given data
     * @param filename File name
     * @param seperator Seperator to split string into values
     */
    public Pyramid(String filename, String seperator) {

        // Get number of lines in file
        Path path = Paths.get(filename);

        long lines = 0;
        try {
            lines = Files.lines(path).count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Determine rows and cols by number of lines
        rows = (int) lines;
        cols = (int) lines;

        // Create matrix
        matrix = new int[rows][cols];

        // Create array list for sums
        sums = new ArrayList < Integer > ();

        // Fill matrix with minus 1s so it is empty
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = -1;
            }
        }

        // Read file and fill matrix with given data
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            int i = 0;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arrOfStr = data.split(seperator);

                int j = 0;
                int mostleft = (cols - (i + 1)) / 2;
                for (j = mostleft; j < mostleft + i + 1; j++) {
                    matrix[i][j] = Integer.parseInt(arrOfStr[j - mostleft]);
                }
                i++;
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * Simple print method for integer matrix
     */
    public void print() {
        for (int i = 0; i < rows; i++) {
            if (i % 2 == 1)
                System.out.print("  ");
            for (int j = 0; j < cols; j++) {

                if (matrix[i][j] != -1)
                    System.out.print(matrix[i][j] + " ");
                else
                    System.out.print("    ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Check if a number is prime or not
     * @param number Number to check
     * @return Status of being number prime
     */
    private boolean isPrime(int number) {
        boolean result = true;

        // Zero and one are not prime
        if (number == 0 || number == 1)
            result = false;

        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0)
                result = false;
        }
        return result;
    }

    /**
     * Traverses in integer matrix downward and digonally, calculates sum of numbers
     * @param x Position of x
     * @param y Position of y
     * @param total Sum of numbers
     */
    public void traverse(int x, int y, int total, ArrayList < Integer > _path) {
        // For path
        ArrayList < Integer > path = new ArrayList < Integer > (_path);
        boolean addedToPath = false;

        // For childs
        int leftY, rightY;
        if (x % 2 == 0)
            leftY = y - 1;
        else
            leftY = y;
        rightY = leftY + 1;

        // Base case: we reach to the end
        if (x == rows - 1) {
            sums.add(total + matrix[x][y]);
            path.add(matrix[x][y]);
            int max = getMax();
            if (total + matrix[x][y] == max) {
                System.out.println("\n\nNew Maximum of Sums = " + max);
                System.out.print("Path followed = ");
                for (int i = 0; i < path.size(); i++) {
                    System.out.print(path.get(i) + " ");
                }
            }
        }
        // Base case: No non-prime child left
        else if ((leftY >= 0 && isPrime(matrix[x + 1][leftY])) && (rightY < leftY + x + 2 && isPrime(matrix[x + 1][rightY]))) {
            sums.add(total + matrix[x][y]);
            path.add(matrix[x][y]);
            int max = getMax();
            if (total + matrix[x][y] == max) {
                System.out.println("\n\nNew Maximum of Sums = " + max);
                System.out.print("Path followed = ");
                for (int i = 0; i < path.size(); i++) {
                    System.out.print(path.get(i) + " ");
                }
            }
        } else {
            // It goes downward left diagonal
            if (leftY >= 0)
                if (!isPrime(matrix[x + 1][leftY])) {
                    if (!addedToPath) {
                        path.add(matrix[x][y]);
                        addedToPath = true;
                    }
                    traverse(x + 1, leftY, total + matrix[x][y], path);
                }

            if (rightY < leftY + x + 2)
                if (!isPrime(matrix[x + 1][rightY])) {
                    if (!addedToPath) {
                        path.add(matrix[x][y]);
                        addedToPath = true;
                    }
                    traverse(x + 1, rightY, total + matrix[x][y], path);
                }
        }
    }

    /**
     * Returns maximum integer value in a collection.
     * @return Maximum value
     */
    public int getMax() {
        Integer max = Collections.max(sums);
        return max;
    }

}