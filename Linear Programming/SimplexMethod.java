import java.util.*;

public class SimplexMethod {

    public static void main(String[] args) {

        double[][] tableau = {
            {1, 4, 1, 0, 0, 24},
            {3, 1, 0, 1, 0, 21},
            {1, 1, 0, 0, 1, 9}
        };

        double[] cj = {2, 5, 0, 0, 0, 0};  
        double[] cb = {0, 0, 0};           

        int m = tableau.length;    // rows  
        int n = tableau[0].length;   // columns

        while (true) {
            
            double[] zj = calculateZj(cb, tableau);

            double[] cjMinusZj = calculateCJminusZJ(cj, zj);

            System.out.println("\nCj - Zj Row: " + Arrays.toString(cjMinusZj));

            int keyColumnIndex = getEnteringColumn(cjMinusZj);
            if (cjMinusZj[keyColumnIndex] <= 0) {
                System.out.println("\n Optimal solution.");
                break;
            }

            double[] lastBColumn = findColumn(tableau, n - 1); 
            double[] keyColumn = findColumn(tableau, keyColumnIndex);
            double[] ratio = findRatio(lastBColumn, keyColumn);

            int keyRowIndex = getLeavingRow(ratio);
            double keyElement = tableau[keyRowIndex][keyColumnIndex];

            System.out.println("Pivot Column(index): " + keyColumnIndex + ", Pivot Row(index): " + keyRowIndex);

            performPivot(tableau, keyRowIndex, keyColumnIndex, keyElement);

            cb[keyRowIndex] = cj[keyColumnIndex];

            System.out.println("Updated Tableau:");
            for (double[] row : tableau) {
                System.out.println(Arrays.toString(row));
            }
        }

        double[] solution = extractSolution(tableau);

        System.out.println("\n Final Solution:");
        for (int i = 0; i < solution.length - 1; i++) {
            System.out.println("x" + (i + 1) + " = " + solution[i]);
        }
    }

    public static double[] calculateZj(double[] cb, double[][] tableau) {
        int m = tableau.length;
        int n = tableau[0].length;
        double[] zj = new double[n];

        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                zj[j] += cb[i] * tableau[i][j];
            }
        }
        return zj;
    }

    public static double[] calculateCJminusZJ(double[] cj, double[] zj) {
        double[] cjMinusZj = new double[cj.length];
        for (int i = 0; i < cj.length; i++) {
            cjMinusZj[i] = cj[i] - zj[i];
        }
        return cjMinusZj;
    }

    public static int getEnteringColumn(double[] cjMinusZj) {
        int index = 0;
        double max = cjMinusZj[0];
        for (int i = 1; i < cjMinusZj.length; i++) {
            if (cjMinusZj[i] > max) {
                max = cjMinusZj[i];
                index = i;
            }
        }
        return index;
    }

    public static double[] findColumn(double[][] tableau, int colIndex) {
        double[] column = new double[tableau.length];
        for (int i = 0; i < tableau.length; i++) {
            column[i] = tableau[i][colIndex];
        }
        return column;
    }

    public static double[] findRatio(double[] lastBColumn, double[] keyColumn) {
        double[] ratio = new double[lastBColumn.length];
        for (int i = 0; i < ratio.length; i++) {
            if (keyColumn[i] > 0) {
                ratio[i] = lastBColumn[i] / keyColumn[i];
            } else {
                ratio[i] = Double.POSITIVE_INFINITY;
            }
        }
        return ratio;
    }

    public static int getLeavingRow(double[] ratio) {
        int index = 0;
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < ratio.length; i++) {
            if (ratio[i] < min) {
                min = ratio[i];
                index = i;
            }
        }
        return index;
    }

    public static void performPivot(double[][] tableau, int keyRowIndex, int keyColIndex, double keyElement) {
        int m = tableau.length;
        int n = tableau[0].length;

        // Normalize pivot row
        for (int j = 0; j < n; j++) {
            tableau[keyRowIndex][j] /= keyElement;
        }

        // Update other rows
        for (int i = 0; i < m; i++) {
            if (i != keyRowIndex) {
                double factor = tableau[i][keyColIndex];
                for (int j = 0; j < n; j++) {
                    tableau[i][j] -= factor * tableau[keyRowIndex][j];
                }
            }
        }
    }

    public static double[] extractSolution(double[][] tableau) {
        int m = tableau.length;
        int n = tableau[0].length;
        double[] solution = new double[n - 1];

        for (int i = 0; i < m; i++) {
            int basicVarIndex = -1;
            int count = 0;
            for (int j = 0; j < n - 1; j++) {
                if (tableau[i][j] == 1) {
                    count++;
                    basicVarIndex = j;
                } else if (tableau[i][j] != 0) {
                    count = 2;
                    break;
                }
            }
            if (count == 1 && basicVarIndex != -1) {
                solution[basicVarIndex] = tableau[i][n - 1];
            }
        }
        return solution;
    }
}