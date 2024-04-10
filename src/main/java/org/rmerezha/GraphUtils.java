package org.rmerezha;

public final class GraphUtils {

    private GraphUtils() {
    }

    public static int[] calculateVertexDegrees(double[][] matrix, boolean isOriented) {
        return calculateVertexDegrees(matrix, isOriented, true);
    }


    private static int[] calculateVertexDegrees(double[][] matrix, boolean isOriented, boolean printInConsole) {
        return isOriented ? calculateVertexDegreesDirected(matrix, printInConsole) : calculateVertexDegreesUndirected(matrix, printInConsole);
    }

    private static int[] calculateVertexDegreesDirected(double[][] matrix, boolean printInConsole) {
        int size = matrix.length;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            int counter = 0;
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 1) counter++;
                if (matrix[j][i] == 1) counter++;
            }
            res[i] = counter;
            if (printInConsole) {
                System.out.println("cтепень вершини " + (i + 1) + " = " + counter);
            }
        }
        return res;
    }

    private static int[] calculateVertexDegreesUndirected(double[][] matrix, boolean printInConsole) {
        int size = matrix.length;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            boolean flag = false;
            int counter = 0;
            for (int j = 0; j < size; j++) {
                if (i == j && matrix[i][j] == 1) {
                    flag = true;
                } else {
                    if (matrix[i][j] == 1) counter++;
                    if (matrix[j][i] == 1) counter++;
                }
            }
            counter /= 2;
            if (flag) {
                counter += 2;
            }
            res[i] = counter;
            if (printInConsole) {
                System.out.println("cтепень вершини " + (i + 1) + " = " + counter);
            }
        }
        return res;
    }

    public static void calculateHalfDegrees(double[][] matrix) {
        int size = matrix.length;

        for (int i = 0; i < size; i++) {
            int counter1 = 0;
            int counter2 = 0;
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 1) counter1++;
                if (matrix[j][i] == 1) counter2++;
            }
            System.out.printf("напiвстепень виходу вершини %d = %d; ", i + 1, counter1);
            System.out.printf("напiвстепень заходу вершини %d = %d; \n", i + 1, counter2);
        }
    }


    public static boolean isRegularGraph(double[][] matrix, boolean isOriented) {
        int[] ints = calculateVertexDegrees(matrix, isOriented, false);
        int i = ints[0];
        for (int j = 1; j < ints.length; j++) {
            if (i != ints[j]) {
                System.out.println("Граф не однорідний (регулярний)");
                return false;
            }
        }
        System.out.printf("Граф однорідний (регулярний) %d-го степеня \n", i);
        return true;
    }

    public static void findHangingAndIsolatedVertices(double[][] matrix, boolean isOriented) {
        int[] ints = calculateVertexDegrees(matrix, isOriented, false);
        boolean flag = false;
        for (int i : ints) {
            if (i == 0) {
                flag = true;
                System.out.printf("%d iзольована вершина \n", i);
            } else if (i == 1) {
                flag = true;
                System.out.printf("%d висяча вершина", i);
            }
        }

        if (!flag) {
            System.out.println("Граф не має iзольованих та висячих вершин");
        }

    }


}
