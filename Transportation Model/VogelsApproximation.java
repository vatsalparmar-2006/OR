import java.util.*;

public class VogelsApproximation {
    public static void main(String[] args) {
        int[][] cost = {
                { 12, 10, 12, 13 },
                { 7, 11, 8, 14 },
                { 6, 16, 11, 7 }
        };

        int[] supply = { 500, 300, 200 };
        int[] demand = { 180, 150, 350, 320 };

        int[][] allocation = new int[3][4];
        boolean[] rowDone = new boolean[3];
        boolean[] colDone = new boolean[4];

        while (!allDone(supply, demand)) {
            int[] rowPenalty = new int[3];
            int[] colPenalty = new int[4];

            for (int i = 0; i < 3; i++) {
                if (!rowDone[i])
                    rowPenalty[i] = getPenalty(cost[i], colDone);
                else
                    rowPenalty[i] = -1;
            }

            for (int j = 0; j < 4; j++) {
                if (!colDone[j])
                    colPenalty[j] = getPenalty(getColumn(cost, j), rowDone);
                else
                    colPenalty[j] = -1;
            }

            int maxPenalty = -1, isRow = 1, index = -1;

            for (int i = 0; i < 3; i++) {
                if (rowPenalty[i] > maxPenalty) {
                    maxPenalty = rowPenalty[i];
                    isRow = 1;
                    index = i;
                }
            }

            for (int j = 0; j < 4; j++) {
                if (colPenalty[j] > maxPenalty) {
                    maxPenalty = colPenalty[j];
                    isRow = 0;
                    index = j;
                }
            }

            int i = -1, j = -1;
            if (isRow == 1) {
                i = index;
                j = getMinCostIndex(cost[i], colDone);
            } else {
                j = index;
                i = getMinCostIndex(getColumn(cost, j), rowDone);
            }

            int min = Math.min(supply[i], demand[j]);
            allocation[i][j] = min;
            supply[i] -= min;
            demand[j] -= min;

            if (supply[i] == 0)
                rowDone[i] = true;
            if (demand[j] == 0)
                colDone[j] = true;
        }

        int totalCost = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                totalCost += allocation[i][j] * cost[i][j];
            }
        }

        System.out.println("Total Transportation Cost = " + totalCost);
    }

    static int getPenalty(int[] arr, boolean[] done) {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (!done[i]) {
                if (arr[i] < first) {
                    second = first;
                    first = arr[i];
                } else if (arr[i] < second) {
                    second = arr[i];
                }
            }
        }
        return (second == Integer.MAX_VALUE) ? first : (second - first);
    }

    static int[] getColumn(int[][] matrix, int col) {
        int[] column = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][col];
        }
        return column;
    }

    static int getMinCostIndex(int[] costs, boolean[] done) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < costs.length; i++) {
            if (!done[i] && costs[i] < min) {
                min = costs[i];
                index = i;
            }
        }
        return index;
    }

    static boolean allDone(int[] supply, int[] demand) {
        for (int val : supply)
            if (val > 0)
                return false;
        for (int val : demand)
            if (val > 0)
                return false;
        return true;
    }
}
