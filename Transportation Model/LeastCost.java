import java.util.*;

public class LeastCost {
    public static void main(String[] args) {
        int[][] cost = { { 12, 10, 12, 13 }, { 7, 11, 8, 14 }, { 6, 16, 11, 7 } };

        int[] supply = { 500, 300, 200 };
        int[] demand = { 180, 150, 350, 320 };

        int totalSupply = 1000;
        int totalDemand = 1000;

        int[][] allocation = new int[3][4];

        while (totalDemand != 0 && totalSupply != 0) {
            int minI = 0;
            int minJ = 0;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < cost.length; i++) {
                for (int j = 0; j < cost[i].length; j++) {
                    if (supply[i] != 0 && min >= cost[i][j] && demand[j] != 0) {
                        if (min == cost[i][j]) {
                            int allocation1 = Math.min(demand[minJ], supply[minI]);
                            int allocation2 = Math.min(demand[j], supply[i]);
                            if (allocation2 > allocation1) {
                                minI = i;
                                minJ = j;
                            }
                        } else {
                            minI = i;
                            minJ = j;

                            min = cost[i][j];
                        }
                    }
                }
            }

            if (supply[minI] > demand[minJ]) {
                allocation[minI][minJ] = demand[minJ];
                supply[minI] -= demand[minJ];
                totalDemand -= demand[minJ];
                totalSupply -= demand[minJ];

                demand[minJ] -= demand[minJ];
            } else {
                allocation[minI][minJ] = supply[minI];
                demand[minJ] -= supply[minI];
                totalSupply -= supply[minI];
                totalDemand -= supply[minI];

                supply[minI] -= supply[minI];
            }

        }

        int finalCost = 0;
        for (int i = 0; i < supply.length; i++) {
            for (int j = 0; j < demand.length; j++) {
                finalCost += allocation[i][j] * cost[i][j];
            }
        }

        System.out.println("Total Transportation Cost : " + finalCost);
    }

    public static int[] findMinPosition(int[][] array) {
        int min = Integer.MAX_VALUE;
        int[] position = { -1, -1 }; // position[0] = row, position[1] = column

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] < min) {
                    min = array[i][j];
                    position[0] = i;
                    position[1] = j;
                }
            }
        }

        return position;
    }
}
