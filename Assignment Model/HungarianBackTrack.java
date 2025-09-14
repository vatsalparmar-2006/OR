public class HungarianBackTrack {
    static int n = 4;
    static int[][] cost = {
        {15, 13, 14, 17},
        {11, 12, 15, 13},
        {13, 12, 10, 11},
        {15, 17, 14, 16}
    };

    static int[] assignment = new int[n];     
    static int[] bestAssignment = new int[n]; 
    static int minCost = Integer.MAX_VALUE;

    static boolean isSafe(int row, int col) {
        
        for (int i = 0; i < row; i++) {
            if (assignment[i] == col) return false;
        }
        return true;
    }

    static void assignTasks(int row, int currentCost) {
        if (row == n) { 
            if (currentCost < minCost) {
                minCost = currentCost;
                System.arraycopy(assignment, 0, bestAssignment, 0, n);
            }
            return;
        }

        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) { 
                assignment[row] = col;
                assignTasks(row + 1, currentCost + cost[row][col]);
            }
        }
    }

    public static void main(String[] args) {
        assignTasks(0, 0);

        System.out.println("Minimum Assignment Cost: " + minCost);
        System.out.print("Task assigned: ");
        for (int task : bestAssignment) {
            System.out.print((task + 1) + " ");
        }
        System.out.println();
    }
}