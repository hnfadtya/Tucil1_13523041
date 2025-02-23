package src;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Gunakan: java src.Main <nama_file_input>");
            return;
        }

        String filename = args[0];
        Solver solver = new Solver();

        try {
            solver.loadFile(filename); // Membaca file dari folder test
            solver.solve(); // Menjalankan solver brute force
            solver.printSolution(); // Menampilkan hasil penyelesaian
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
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
