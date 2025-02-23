package src;

import java.util.*;

public class Block {
    private char id;
    private int[][] shape;
    public static final String RESET = "\u001B[0m";
    public static final String[] COLORS = {
        "\u001B[31m", 
        "\u001B[32m", 
        "\u001B[33m", 
        "\u001B[34m", 
        "\u001B[35m", 
        "\u001B[36m", 
        "\u001B[91m",  
        "\u001B[92m",  
        "\u001B[93m",  
        "\u001B[94m",  
        "\u001B[95m",  
        "\u001B[96m",  
        "\u001B[90m",  
        "\u001B[37m", 
        "\u001B[97m",  
        "\u001B[41m",  
        "\u001B[42m",  
        "\u001B[43m",  
        "\u001B[45m",  
        "\u001B[46m",  
        "\u001B[44m",  
        "\u001B[100m", 
        "\u001B[101m", 
        "\u001B[102m", 
        "\u001B[103m", 
        "\u001B[104m"  
    };

    public Block(char id, int[][] shape) {
        this.id = id;
        this.shape = shape;
    }

    public char getId() {
        return id;
    }
    
    public int[][] getShape() {
        return shape;
    }

    public String getColor(char id) {
        if(id >= 'A' && id <= 'Z') {
            return COLORS[id - 'A'];
        }
        return RESET;
    }

    public List<int[][]> getAllTransformations() {
        List<int[][]> transformations = new ArrayList<>();
        transformations.add(shape);
        transformations.add(rotateBlock(shape));
        transformations.add(rotateBlock(transformations.get(1)));
        transformations.add(rotateBlock(transformations.get(2)));

        int[][] flipped = reflectBlock(shape);
        transformations.add(flipped);
        transformations.add(rotateBlock(flipped));
        transformations.add(rotateBlock(transformations.get(5)));
        transformations.add(rotateBlock(transformations.get(6)));

        return transformations;
    }

    private int[][] rotateBlock(int[][] shape) {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        return rotated;
    }

    private int[][] reflectBlock(int[][] shape) {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] reflected = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                reflected[i][cols - 1 - j] = shape[i][j];
            }
        }
        return reflected;
    }

}
