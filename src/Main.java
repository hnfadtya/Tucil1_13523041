package src;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int isLaunch = 0;
        int pilihan;
        clearScreen();
        System.out.println("""
            null""");
        System.out.print("\n\n");
        while (true) { 
            if (isLaunch == 1) clearScreen();
            isLaunch = 1;
            System.out.println("Selamat datang di program IQ Puzzler Pro Solver dengan Algoritma Brute Force");
            System.out.println("1. Pecahkan soal IQ Puzzler Pro");
            System.out.println("2. Keluar\n");
            scan = new Scanner(System.in);
            pilihan = scan.nextInt();
            if (pilihan == 1) {
                clearScreen();
                if (args.length < 1) {
                    System.out.println("Gunakan: java src.Main <input_file>");
                    return;
                }
                String filename = args[0];
                Solver solver = new Solver();
        
                try {
                    solver.loadFile(filename);
                    long start = System.currentTimeMillis();
                    boolean solutionFound = solver.solve();
                    long end = System.currentTimeMillis();
                    if (solutionFound) {
                        solver.printSolution();
                        System.out.println("Waktu pencarian: " + (end - start) + " ms");
                        System.out.println("Banyak kasus yang ditinjau: " + solver.getCheckedCases());
                    } else {
                        System.out.println("Tidak ada solusi yang ditemukan");
                    }
                } catch (IOException e) {
                    System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
                }    
                confirmExit();
            } else if (pilihan == 2) {
                clearScreen();
                System.out.println("Terima kasih telah menggunakan program ini");
                System.exit(0);
            } else {
                System.out.println("Pilihan tidak valid");
                confirmExit();
            }
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

    // Konfirmasi kembali ke menu
    public static Scanner scan;
    public static void confirmExit() {
        System.out.print("Kembali ke menu ");
        scan = new Scanner(System.in);
        scan.nextLine();
    }
}
