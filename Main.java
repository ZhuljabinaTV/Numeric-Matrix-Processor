package processor;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        boolean isNotBreake = true;

        while (isNotBreake) {
            System.out.print("1. Add matrices\n" +
                    "2. Multiply matrix to a constant\n" +
                    "3. Multiply matrices\n" +
                    "4. Transpose matrix\n" +
                    "5. Calculate a determinant\n" +
                    "6. Inverse matrix\n" +
                    "0. Exit\n" +
                    "Your choice: ");
            switch (scanner.nextInt()) {
                case 0: {
                    isNotBreake = false;
                    break;
                }
                case 1: {
                    Matrix matrix1 = createMatrix("first ", scanner);
                    Matrix matrix2 = createMatrix("second ", scanner);
                    Matrix newMatrix = add2Matrix(matrix1, matrix2);
                    if (newMatrix != null) {
                        System.out.println("The result is: ");
                        newMatrix.print();
                        System.out.println();
                    }
                    break;
                }
                case 2: {
                    Matrix matrix = createMatrix("", scanner);
                    System.out.println("Enter a constant: ");
                    double constant = scanner.nextDouble();
                    Matrix newMatrix = multiplyToConst(matrix, constant);
                    System.out.println("The result is: ");
                    newMatrix.print();
                    System.out.println();
                    break;
                }
                case 3: {
                    Matrix matrix1 = createMatrix("first ", scanner);
                    Matrix matrix2 = createMatrix("second ", scanner);
                    Matrix newMatrix = multiply2Matrices(matrix1, matrix2);
                    if (newMatrix != null) {
                        System.out.println("The result is: ");
                        newMatrix.print();
                        System.out.println();
                    }
                    break;
                }
                case 4: {
                    chooseTranspose(scanner);
                    break;
                }
                case 5: {
                    Matrix matrix = createMatrix("", scanner);
                    System.out.println("The result is: ");
                    double d = getDeterminant(matrix);
                    DecimalFormat format = new DecimalFormat("#.##");
                    String s = format.format(d);
                    System.out.printf("%6s ", s);
                    System.out.println();
                    break;
                }
                case 6: {
                    Matrix matrix = createMatrix("", scanner);
                    Matrix newMatrix = inverse(matrix);
                    if (newMatrix != null) {
                        System.out.println("The result is:");
                        newMatrix.print();
                        System.out.println();
                    }
                }
                default: {
                    break;
                }
            }
        }
    }

    static Matrix add2Matrix(Matrix matrix1, Matrix matrix2) {
        int row = matrix1.getRow();
        int col = matrix1.getCol();
        Matrix newMatrix = new Matrix(row, col);
        if (row != matrix2.getRow() || col != matrix2.getCol()) {
            System.out.println("It's impossible to add these matrices");
        } else {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    newMatrix.add(i, j, matrix1.getIJElement(i, j) + matrix2.getIJElement(i, j));
                }
            }
            return newMatrix;
        }
        return null;
    }

    static Matrix multiplyToConst(Matrix matrix, double constant) {
        Matrix newMatrix = new Matrix(matrix.getRow(), matrix.getCol());
        for (int i = 0; i < matrix.getRow(); i++) {
            for (int j = 0; j < matrix.getCol(); j++) {
                newMatrix.add(i, j,matrix.getIJElement(i, j) * constant);
            }
        }
        return newMatrix;
     }

    static Matrix createMatrix(String s, Scanner scanner) {
        System.out.print("Enter size of " + s + "matrix: ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        System.out.println("Enter " + s + "matrix:");
        Matrix matrix = new Matrix(row, col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix.add(i, j, scanner.nextDouble());
            }
        }
        return matrix;
    }

    static Matrix multiply2Matrices(Matrix matrix1, Matrix matrix2) {
        int col1 = matrix1.getCol();
        if (col1 != matrix2.getRow()) {
            System.out.println("It's impossible to multiply these matrices");
            return null;
        } else {
            int row = matrix1.getRow();
            int col = matrix2.getCol();
            Matrix newMatrix = new Matrix(row, col);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    double k = 0;
                    for (int l = 0; l < col1; l++) {
                        k += matrix1.getIJElement(i, l) * matrix2.getIJElement(l, j);
                    }
                    newMatrix.setIJElement(i, j, k);
                }
            }
            return newMatrix;
         }
    }

    static void chooseTranspose(Scanner scanner) {
        System.out.print("1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line\n" +
                "Your choice: ");
        int n = scanner.nextInt();
        Matrix matrix = createMatrix("", scanner);
        System.out.println("The result is:");
        Matrix newMatrix;
        switch (n) {
            case 1: {
                newMatrix = transposeMain(matrix);
                break;
            }
            case 2: {
                newMatrix = transposeSide(matrix);
                break;
            }
            case 3: {
                newMatrix = transposeVert(matrix);
                break;
            }
            case 4: {
                newMatrix = transposeHorizontal(matrix);
                break;
            }
            default: {
                newMatrix = null;
                break;
            }
        }
        if (newMatrix != null) {
            newMatrix.print();
            System.out.println();
        }
    }

    static Matrix transposeMain(Matrix matrix) {
        int row = matrix.getRow();
        int col = matrix.getCol();
        Matrix newMatrix = new Matrix(col, row);
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                newMatrix.setIJElement(i, j, matrix.getIJElement(j, i));
            }
        }
        return newMatrix;
    }

    static Matrix transposeSide(Matrix matrix) {
        int row = matrix.getRow();
        int col = matrix.getCol();
        Matrix newMatrix = new Matrix(col, row);
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                newMatrix.setIJElement(i, j, matrix.getIJElement(row - 1 - j, col - 1 - i));
            }
        }
        return newMatrix;
    }

    static Matrix transposeVert(Matrix matrix) {
        int row = matrix.getRow();
        int col = matrix.getCol();
        Matrix newMatrix = new Matrix(col, row);
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                newMatrix.setIJElement(i, j, matrix.getIJElement(i, col - 1 - j));
            }
        }
        return newMatrix;
    }

    static Matrix transposeHorizontal(Matrix matrix) {
        int row = matrix.getRow();
        int col = matrix.getCol();
        Matrix newMatrix = new Matrix(col, row);
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                newMatrix.setIJElement(i, j, matrix.getIJElement(row - 1 - i, j));
            }
        }
        return newMatrix;
    }

    static double getDeterminant(Matrix matrix) {
        int row = matrix.getRow();
        if (row == 1) {
            return matrix.getIJElement(0,0);
        }
        if (row == 2) {
            return matrix.getIJElement(0, 0) * matrix.getIJElement(1,1) -
                    matrix.getIJElement(0,1) * matrix.getIJElement(1,0);
        }
        else {
            double sum = 0;
            for (int i = 0; i < row; i++) {
                int k = i % 2 == 0 ? 1 : -1;
                sum += matrix.getIJElement(i, 0) * k * getDeterminant(matrix.getMinor(i, 0));
            }
            return sum;
        }
    }

    static Matrix matrixOfCofactors(Matrix matrix) {
        int row = matrix.getRow();
        int col = matrix.getCol();
        Matrix newMatrix = new Matrix(row, col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                newMatrix.setIJElement(i, j, Math.pow(-1, i + j) * getDeterminant(matrix.getMinor(i, j)));
            }
        }
        return newMatrix;
    }

    static Matrix inverse(Matrix matrix) {
        double determinant = getDeterminant(matrix);
        if (determinant == 0) {
            System.out.println("The determinant equals 0");
            return null;
        } else {
            double d = 1 / determinant;
            Matrix newMatrix = matrixOfCofactors(matrix);
            newMatrix = transposeMain(newMatrix);
            return multiplyToConst(newMatrix, d);
        }
    }
}

class Matrix {
    private final int row;
    private final int col;
    private final double[][] matrix;

    Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        matrix = new double[row][col];
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public double getIJElement(int i, int j) {
        return matrix[i][j];
    }

    public void setIJElement(int i, int j, double value) {
        matrix[i][j] = value;
    }

    void add(int i, int j, double value) {
        if (i > -1 && i < row && j > -1 && j < col) {
            matrix[i][j] = value;
        } else System.out.println("error");
    }

    void print() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                double d = matrix[i][j];
                DecimalFormat format = new DecimalFormat("#.##");
                String s = format.format(d);
                System.out.printf("%6s ", s);
            }
            System.out.println();
        }
    }

    public Matrix getMinor(int i, int j) {
        Matrix newMatrix = new Matrix(row - 1, col - 1);
        int n = 0;
        for (int k = 0; k < row; k++) {
            int m = 0;
            if (k != i) {
                for (int l = 0; l < col; l++) {
                    if (l != j) {
                        newMatrix.setIJElement(n, m, matrix[k][l]);
                        m++;
                    }
                }
                n++;
            }
        }
        return newMatrix;
    }

}