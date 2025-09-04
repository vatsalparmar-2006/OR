import java.util.Scanner;

public class NorthWestCorner {
    public static void main(String[] args) {

        int[][] cost = {
                { 12, 10, 12, 13 },
                { 7, 11, 8, 14 },
                { 6, 16, 11, 7 }
        };

        int[] supply = { 500, 300, 200 };
        int[] demand = { 180, 150, 350, 320 };

        int[][] allocation = new int[3][4];

        for (int i = 0; i < supply.length; i++) {
            for (int j = 0; j < demand.length; j++) {
                if (supply[i] > demand[j]) {
                    allocation[i][j] = demand[j];
                    supply[i] -= demand[j];
                    demand[j] -= demand[j];
                } else {
                    allocation[i][j] = supply[i];
                    demand[j] -= supply[i];
                    supply[i] -= supply[i];
                    break;
                }
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
}