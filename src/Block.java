package src;

import java.util.Random;

public class Block {
    private char id;
    private int[][] shape;
    private static final Random rand = new Random();
    private final String color;
    
    public Block(char id, int[][] shape) {
        this.id = id;
        this.shape = shape;
        this.color = generateColor();
    }

    public char getId() {
        return id;
    }
    
    public int[][] getShape() {
        return shape;
    }
    
    // Rotasi 90 derajat searah jarum jam
    public Block rotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        return new Block(id, rotated);
    }
    
    // Refleksi horizontal
    public Block reflect() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] reflected = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                reflected[i][cols - 1 - j] = shape[i][j];
            }
        }
        return new Block(id, reflected);
    }
    
    // Warna unik untuk setiap blok
    private String generateColor() {
        int colorCode = 31 + rand.nextInt(6); // ANSI colors (31-36)
        return "\u001B[" + colorCode + "m";
    }
    
    public String getColor() {
        return color;
    }
}
