package org.rmerezha;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        int rowsFirst = firstMatrix.length;
        int colsFirst = firstMatrix[0].length;
        int colsSecond = secondMatrix[0].length;

        double[][] result = new double[rowsFirst][colsSecond];

        for (int i = 0; i < rowsFirst; i++) {
            for (int j = 0; j < colsSecond; j++) {
                for (int k = 0; k < colsFirst; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return result;
    }



    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void findPathsLength2(double[][] matrix) {
        int n = matrix.length;
        double[][] A2 = multiplyMatrices(matrix, matrix);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (A2[i][j] > 0) {
                    for (int k = 0; k < n; k++) {
                        if ((matrix[i][k] > 0 && matrix[k][j] > 0) && !((i == k) && (i == j))) {
                            System.out.println("Path of length 2: " + (i + 1) + " -> " + (k + 1) + " -> " + (j + 1));
                        }
                    }
                }
            }
        }
    }

    public static void findPathsLength3(double[][] matrix) {
        int n = matrix.length;
        double[][] A2 = multiplyMatrices(matrix, matrix);
        double[][] A3 = multiplyMatrices(A2, matrix);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (A3[i][j] > 0) {
                    for (int k = 0; k < n; k++) {
                        if (A2[i][k] > 0 && matrix[k][j] > 0) {
                            for (int l = 0; l < n; l++) {
                                if (matrix[i][l] > 0 && matrix[l][k] > 0 && matrix[k][j] > 0) {
                                    if (!((i == l) && (i == k) && (i == j))) {
                                        System.out.println("Path of length 3: " + (i + 1) + " -> " + (l + 1) + " -> " + (k + 1) + " -> " + (j + 1));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static void findAndPrintReachabilityMatrix(double[][] matrix) {
        int n = matrix.length;
        double[][] reach = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                reach[i][j] = (i == j || matrix[i][j] != 0) ? 1 : 0;
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    reach[i][j] = (reach[i][j] != 0) || (reach[i][k] != 0 && reach[k][j] != 0) ? 1 : 0;
                }
            }
        }


        printMatrix(reach);
    }

    public static void findAndPrintStrongConnectivityMatrix(double[][] matrix) {
        int n = matrix.length;
        double[][] reachabilityMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                reachabilityMatrix[i][j] = (i == j || matrix[i][j] != 0) ? 1 : 0;
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    reachabilityMatrix[i][j] = (reachabilityMatrix[i][j] != 0) || (reachabilityMatrix[i][k] != 0 && reachabilityMatrix[k][j] != 0) ? 1 : 0;
                }
            }
        }
        double[][] strongConnectivityMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                strongConnectivityMatrix[i][j] = (reachabilityMatrix[i][j] != 0 && reachabilityMatrix[j][i] != 0) ? 1 : 0;
            }
        }

        printMatrix(strongConnectivityMatrix);
    }

    public static double[][] findCondensationGraph(double[][] matrix) {
        int n = matrix.length;
        double[][] reachabilityMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                reachabilityMatrix[i][j] = (i == j || matrix[i][j] != 0) ? 1 : 0;
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    reachabilityMatrix[i][j] = (reachabilityMatrix[i][j] != 0) || (reachabilityMatrix[i][k] != 0 && reachabilityMatrix[k][j] != 0) ? 1 : 0;
                }
            }
        }

        List<Set<Integer>> components = new ArrayList<>();
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                Set<Integer> component = new HashSet<>();
                for (int j = 0; j < n; j++) {
                    if (reachabilityMatrix[i][j] == 1 && reachabilityMatrix[j][i] == 1) {
                        component.add(j);
                        visited[j] = true;
                    }
                }
                components.add(component);
            }
        }

        int numComponents = components.size();
        double[][] condensationMatrix = new double[numComponents][numComponents];

        for (int i = 0; i < numComponents; i++) {
            for (int j = 0; j < numComponents; j++) {
                if (i != j && hasEdgeBetweenComponents(matrix, components.get(i), components.get(j))) {
                    condensationMatrix[i][j] = 1;
                }
            }
        }
        printMatrix(condensationMatrix);
        return condensationMatrix;
    }

    private static boolean hasEdgeBetweenComponents(double[][] matrix, Set<Integer> component1, Set<Integer> component2) {
        for (int u : component1) {
            for (int v : component2) {
                if (matrix[u][v] != 0) {
                    return true;
                }
            }
        }
        return false;
    }

}
