package src;

import java.util.*;

public class Board {
    private char[][] grid;

    public Board(int width, int height) {
        grid = new char[height][width];
        for (char[] row : grid) {
            Arrays.fill(row, '.'); // '.' menandakan area kosong
        }
    }

    public int getWidth() {
        return grid[0].length;
    }

    public int getHeight() {
        return grid.length;
    }
    
    public void printBoard(List<Block> blocks) {
        System.out.println("\nSolusi:");
        for (char[] row : grid) {
            for (char cell : row) {
                Block block = blocks.stream().filter(b -> b.getId() == cell).findFirst().orElse(null);
                String color = (block != null) ? block.getColor() : "\u001B[37m"; // Default putih
                System.out.print(color + cell + " " + "\u001B[0m"); // Reset warna
            }
            System.out.println();
        }
    }
}
