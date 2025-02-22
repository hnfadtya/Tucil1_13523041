package src;

import java.io.*;
import java.util.*;

public class Solver {
    private int width, height;
    private List<Block> blocks = new ArrayList<>();

    public void loadFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String[] size = br.readLine().split(" ");
        width = Integer.parseInt(size[0]);
        height = Integer.parseInt(size[1]);
        int numBlocks = Integer.parseInt(size[2]);
        
        br.readLine(); // Abaikan baris DEFAULT
        
        for (int i = 0; i < numBlocks; i++) {
            char id = (char) ('A' + i);
            List<String> shapeLines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                shapeLines.add(line);
            }
            
            int[][] shape = new int[shapeLines.size()][shapeLines.get(0).length()];
            for (int r = 0; r < shape.length; r++) {
                for (int c = 0; c < shape[r].length; c++) {
                    shape[r][c] = (shapeLines.get(r).charAt(c) == id) ? 1 : 0;
                }
            }
            blocks.add(new Block(id, shape));
        }
        br.close();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void solve(Board board) {
        board.printBoard(blocks);
    }
}