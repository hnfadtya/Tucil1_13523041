package src;

import java.util.*;

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

    // Warna unik untuk setiap blok
    private String generateColor() {
        int colorCode = 31 + rand.nextInt(6); // ANSI colors (31-36)
        return "\u001B[" + colorCode + "m";
    }
    
    public String getColor() {
        return color;
    }
}
