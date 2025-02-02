package com.practice;
import java.util.Arrays;

class MatrixMultiplicationThread extends Thread {
    private int row, col;
    private int[][] matrixA, matrixB, result;

    public MatrixMultiplicationThread(int row, int col, int[][] matrixA, int[][] matrixB, int[][] result) {
        this.row = row;
        this.col = col;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.result = result;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < matrixA[0].length; i++) {  
            sum += matrixA[row][i] * matrixB[i][col];
        }
        result[row][col] = sum;  
    }
}

public class Task10 {

    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) throws InterruptedException {
        int rowsA = matrixA.length, colsA = matrixA[0].length;
        int rowsB = matrixB.length, colsB = matrixB[0].length;

        if (colsA != rowsB) {
            throw new IllegalArgumentException("Matrix multiplication not possible. Columns of A must equal rows of B.");
        }

        int[][] result = new int[rowsA][colsB];
        Thread[][] threads = new Thread[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                threads[i][j] = new MatrixMultiplicationThread(i, j, matrixA, matrixB, result);
                threads[i][j].start();
            }
        }

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                threads[i][j].join();
            }
        }

        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        int[][] matrixA = { {1, 2}, {3, 4} };
        int[][] matrixB = { {2, 0}, {1, 2} };

        int[][] result = multiplyMatrices(matrixA, matrixB);

        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
}
