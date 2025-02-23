package src;

import java.io.*;
import java.util.*;

public class Solver {
    private int width, height;
    private List<Block> blocks = new ArrayList<>();
    private char[][] board;
    private int checkedCases = 0;
    private boolean solutionFound = false; // Tambahkan flag solusi ditemukan
    public static final String RESET = "\u001B[0m";

    /* ============== FILE PROCESSING =================*/
    public void loadFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String[] size = br.readLine().split(" ");
        width = Integer.parseInt(size[0]);
        height = Integer.parseInt(size[1]);
        int numBlocks = Integer.parseInt(size[2]);
        
        br.readLine(); // Abaikan baris DEFAULT
        
        for (int i = 0; i < numBlocks; i++) {
            List<String> shapeLines = new ArrayList<>();
            String line;
            
            while ((line = br.readLine()) != null) {
                shapeLines.add(line);
                if (shapeLines.size() > 1 && line.length() != shapeLines.get(0).length()) {
                    break; // Jika panjang baris berubah, kemungkinan blok baru dimulai (antisipasi kesalahan format)
                }
            }
            System.out.println(shapeLines);
            if (!shapeLines.isEmpty()) {
                addBlock((char) ('A' + i), shapeLines);
            }
        }

        br.close();
    }

    private void addBlock(char id, List<String> shapeLines) {
        int rows = shapeLines.size();
        int cols = getColumnLenght(shapeLines, rows); // harusnya dicari kolom terpanjang
        System.out.println(cols);
        int[][] shape = new int[rows][cols];
        
        for (int r = 0; r < rows; r++) {
            int lineLenght = shapeLines.get(r).length();
            for (int c = 0; c < lineLenght; c++) {
                if (shapeLines.get(r).charAt(c) == id) {shape[r][c] = 1;} else shape[r][c] = 0;
                System.out.println(shape[r][c]);
            }
        }
        // System.out.println(id);
        blocks.add(new Block(id, shape));
    }

    public int getColumnLenght(List<String> shapeLines, int rows) {
        int columnLenght = shapeLines.get(0).length();
        for (int i = 1; i < rows; i++) {
            if (shapeLines.get(i).length() > columnLenght) {
                columnLenght = shapeLines.get(i).length();
            }
        }
        return columnLenght;
    }
    
    /* ============== PROBLEM SOLVING =================*/
    public boolean solve() {
        board = new char[height][width];
        for (char[] row : board) Arrays.fill(row, '.');

        return placeBlock(0);
    }

    private boolean placeBlock(int index) {
        if (solutionFound) return true; 

        if (index == blocks.size()) {
            solutionFound = true;
            return true;
        }

        Block block = blocks.get(index);
        List<int[][]> transformations = block.getAllTransformations();

        for (int[][] shape : transformations) {
            for (int r = 0; r <= height - shape.length; r++) {
                for (int c = 0; c <= width - shape[0].length; c++) {
                    checkedCases++; 
                    if (canPlace(shape, r, c)) {
                        place(shape, r, c, block.getId());
                        if (placeBlock(index + 1)) return true;
                        remove(shape, r, c);
                    }
                }
            }
        }
        return false; // backtrack
    }

    private boolean canPlace(int[][] shape, int r, int c) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1 && board[r + i][c + j] != '.') {
                    return false;
                }
            }
        }
        return true;
    }

    private void place(int[][] shape, int r, int c, char id) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    board[r + i][c + j] = id;
                }
            }
        }
    }

    private void remove(int[][] shape, int r, int c) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    board[r + i][c + j] = '.';
                }
            }
        }
    }

    public void printSolution() {
        System.out.println("Solusi ditemukan:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char cell = board[i][j];
                Block block = blocks.get(cell - 'A');
                String color = block.getColor(cell);
                System.out.print(color + cell + " " + RESET);
            }
            System.out.println();
        }
    }

    public int getCheckedCases() {
        return checkedCases;
    }
}