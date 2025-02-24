package src;

import java.io.*;
import java.util.*;

public class Solver {
    private int width, height, blockCount;
    private List<Block> blocks = new ArrayList<>();
    private char[][] board;
    private int checkedCases = 0;
    private boolean solutionFound = false; 
    public static final String RESET = "\u001B[0m";

    /* ============== FILE PROCESSING =================*/
    public void loadFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String[] size = br.readLine().split(" ");

        try {
            width = Integer.parseInt(size[0]);
            height = Integer.parseInt(size[1]);
            blockCount = Integer.parseInt(size[2]);             
        } catch (Exception e) {
            IOException ex = new IOException("Ukuran papan atau jumlah blok tidak valid");
            throw ex;
        }

        br.readLine(); // Abaikan baris DEFAULT

        List<String> shapeLines = new ArrayList<>();
        char blockId = ' ';
        char prevChar = ' '; 
        String line;

        while ((line = br.readLine()) != null) { // defaultnya program berhenti membaca blok ketika jumlahnya sudah sesuai dengan numBlocks
            line = line.trim();
            if (line.isEmpty()) continue; // baris kosong
            
            if (!isLineValid(line, getCharFromString(line))) {
                IOException e = new IOException("Blok tidak valid");
                throw e;
            }
            if (prevChar != ' ' && line.charAt(0) != prevChar) {
                addBlock(blockId, shapeLines); // menyimpan blok sebelumnya
                shapeLines.clear();
            }
    
            if (shapeLines.isEmpty()) {
                blockId = getCharFromString(line);
            }

            shapeLines.add(line);
            prevChar = getCharFromString(line); 
        }
        
        // Simpan blok terakhir dalam file jika masih ada data
        if (!shapeLines.isEmpty()) {
            addBlock(blockId, shapeLines);
        }
        
        if (blocks.size() != blockCount) {
            IOException e = new IOException("Jumlah blok tidak sesuai");
            throw e;
        }
        
        br.close();
    }

    private void addBlock(char id, List<String> shapeLines) {
        int rows = shapeLines.size();
        int cols = getColumnLenght(shapeLines, rows); 
        int[][] shape = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            int lineLenght = shapeLines.get(r).length();
            for (int c = 0; c < lineLenght; c++) {
                if (shapeLines.get(r).charAt(c) == id) {shape[r][c] = 1;} else shape[r][c] = 0;
            }
        }
        // System.out.println(id);
        blocks.add(new Block(id, shape));
    }

    private int getColumnLenght(List<String> shapeLines, int rows) {
        int columnLenght = shapeLines.get(0).length();
        for (int i = 1; i < rows; i++) {
            if (shapeLines.get(i).length() > columnLenght) {
                columnLenght = shapeLines.get(i).length();
            }
        }
        return columnLenght;
    }
    
    private char getCharFromString(String string) {
        int index = 0;
        while (index < string.length()){
            if (string.charAt(index) != ' ') {
                return string.charAt(index);
            }
        }
        return ' ';
    }

    private boolean isLineValid(String line, char id) {
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c != ' ' && c != id) {
                return false;
            } 
        }
        return true;
    }

    /* ============== PROBLEM SOLVING =================*/
    public boolean solve() {
        board = new char[height][width];
        for (char[] row : board) Arrays.fill(row, '.');

        return placeBlock(0);
    }

    private boolean placeBlock(int index) {
        if (solutionFound) return true; 

        if (index == blockCount) { // basis rekursi
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
                        remove(shape, r, c); // backtrack jika tidak bisa ditempatkan
                    }
                }
            }
        }
        return false;
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
    
    /* ============== BOARD =================*/
    public char[][] getBoard() {
        return board;
    }

    public int getBoardCol() {
        return width;
    }

    public int getBoardRow() {
        return height;
    }

    public char getCell(int r, int c) {
        return board[r][c];
    }

    public boolean isBoardFull() {
        for (int i = 0; i < getBoardCol(); i++) {
            for (int j = 0; j < getBoardRow(); j++) {
                char cell = getCell(i, j);
                if (cell == '.') return false;
            }
        }
        return true;
    }
}