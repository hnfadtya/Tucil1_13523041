package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solve {
    public class Matrix {
        // Attributes
        char[][] elements;
        int Row; // Jumlah baris
        int Col; // Jumlah kolom
    
        //Constructor
        public Matrix(int nRow, int nCol) {
            this.Row = nRow;
            this.Col = nCol;
            this.elements = new char[nRow][nCol];
        }
        // public Matrix[] createMatrixArray(int totalBlock) {
        //     Matrix[] dataBlock = new Matrix[totalBlock];
        //     return dataBlock;
        // }
        // Prekondisi idx < Row atau Col
        public char[] getRow(int idx) {return elements[idx];}
        public char[] getCol(int idx) {
            char[] col = new char[Row];
            for (int i =0; i < Row; i++) {
                col[i] = elements[i][idx];
            }
            return col;
        }

        // public int rowCount(){return Row;}
        // public int colCount(){return Col;}
    }

    public Matrix rotateMatrix(Matrix M) { // rotate 90 derajat searah jarum jam
        Matrix rotatedMatrix = new Matrix(M.Col, M.Row);
        for (int i = 0; i < M.Row; i++) {
            char[] rotatedCol = M.getRow(i);   
            for (int j = 0; j < M.Col; j++) {
                rotatedMatrix.elements[j][M.Row - i] = rotatedCol[j];
            }
        }
        return rotatedMatrix;
    }

    public Matrix flipMatrixHorizontal(Matrix M) { // flip horizontal
        Matrix flippedMatrix = new Matrix(M.Row, M.Col);
        for (int i = 0; i < M.Row; i++) {
            char[] flippedCol = M.getRow(i);
            for (int j = 0; j < M.Col; j++) {
                flippedMatrix.elements[i][M.Col - j] = flippedCol[j];
            }
        }
        return flippedMatrix;
    }

    public Matrix flipMatrixVertical(Matrix M) { // flip vertical
        Matrix flippedMatrix = new Matrix(M.Row, M.Col);
        for (int i = 0; i < M.Row; i++) {
            char[] flippedCol = M.getRow(i);
            for (int j = 0; j < M.Col; j++) {
                flippedMatrix.elements[M.Row - i][j] = flippedCol[j];
            }
        }
        return flippedMatrix;
    }
    public Scanner scan;
    public String inputFileName(){
        scan = new Scanner(System.in);
        System.out.println("Masukkan nama file (contoh: a.txt): ");
        String filename = scan.nextLine();
        String path = "..\\test\\" + filename;
        return path;
    }

    public Matrix[] readFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanFile = new Scanner(file);
            int M = scanFile.nextInt();
            int N = scanFile.nextInt();
            int totalBlock = scanFile.nextInt();
            scanFile.nextLine();
            String configuration = scanFile.next();
            Matrix[] dataBlock = new Matrix[totalBlock];
            for (int i = 0; i < totalBlock; i++) {
                String block = scanFile.next();
                int columnLenght = block.length();
                int rowLenght = 1;
                Matrix blockMatrix = new Matrix(rowLenght, columnLenght);
                for (int j = 0; j < columnLenght; j++) {
                    blockMatrix.elements[rowLenght][j] = block.charAt(j);
                }
                scanFile.nextLine();
                String nextBlock = scanFile.next();
                while (nextBlock.charAt(0) == block.charAt(0)) {
                    block = nextBlock;
                    nextBlock = scanFile.next();
                    columnLenght = block.length();
                    rowLenght++;
                    for (int j = 0; j < columnLenght; j++) {
                        blockMatrix.elements[rowLenght][j] = block.charAt(j);
                    }
                }
                dataBlock[i] = blockMatrix;
            }
            scanFile.close();
            return dataBlock;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;
        }
    }    

}
