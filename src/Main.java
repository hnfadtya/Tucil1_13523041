package src;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Harap masukkan nama file sebagai argumen.");
            return;
        }

        String filename = args[0];
        Solver solver = new Solver();

        try {
            solver.loadFile(filename);
            Board board = new Board(solver.getWidth(), solver.getHeight());
            solver.getBlocks();
            // solver.solve(board);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file.");
        }
    }
    // Border untuk styling
    public static void border() {
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
    }

    // Membersihkan terminal
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Print title - tambahkan ke setiap pilihan yang ada
    public static void menuTitle(String[] title) {
        System.out.println(Arrays.toString(title));
    }

    // Konfirmasi kembali ke menu
    public static Scanner scan;
    public static void confirmExit() {
        System.out.print("Kembali ke menu ");
        scan = new Scanner(System.in);
        scan.nextLine();
    }
}
