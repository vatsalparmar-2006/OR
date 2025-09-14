
public class HungarianMaximization {
    static int n = 4;
    static int[][] qcost = {
            { 240, 300, 225, 300 },
            { 360, 450, 250, 300 },
            { 144, 180, 150, 200 },
            { 240, 300, 225, 180 }
    };

    static int[][] cost = new int[n][n];
    static int[][] copyCost = new int[n][n]; 

    static int[] assignment = new int[n];
    static int[] bestAssignment = new int[n];
    static int maxCost = Integer.MIN_VALUE;

    static boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (assignment[i] == col) return false;
        }
        return true;
    }

    static void assignTasks(int row, int currentCost, int[][] matrix) {
        if (row == n) {
            int originalCost = 0;
            for (int i = 0; i < n; i++) {
                originalCost += qcost[i][assignment[i]];
            }
            if (originalCost > maxCost) {
                maxCost = originalCost;
                System.arraycopy(assignment, 0, bestAssignment, 0, n);
            }
            return;
        }

        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) {
                assignment[row] = col;
                assignTasks(row + 1, currentCost + matrix[row][col], matrix);
            }
        }
    }

    public static void main(String[] args) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (qcost[i][j] > max) max = qcost[i][j];
            }
        }
        System.out.println("Maximum element: " + max);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = max - qcost[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            System.arraycopy(cost[i], 0, copyCost[i], 0, n);
        }

        assignTasks(0, 0, copyCost);

        System.out.println("Maximum Assignment Cost: " + maxCost);
        System.out.print("Task assigned: ");
        for (int task : bestAssignment) {
            System.out.print((task + 1) + " ");
        }
        System.out.println();
    }
}
