package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printHeader();

            System.out.println("[1] Load File");
            System.out.println("[2] Keluar");
            System.out.print("Pilih opsi: ");
            String pilihan = scanner.nextLine();

            if (pilihan.equals("2")) {
                printFooter();
                break;
            } else if (!pilihan.equals("1")) {
                System.out.println("Pilihan tidak valid!\n");
                continue;
            }

            System.out.print("\nMasukkan nama file (dalam folder test/): ");
            String filename = scanner.nextLine().trim();
            File inputFile = new File("test/" + filename);

            if (!inputFile.exists()) {
                System.out.println("Error: File '" + filename + "' tidak ditemukan!\n");
                continue;
            }

            System.out.println("\nMemuat file: " + filename);
            long start = System.currentTimeMillis();

            Solver solver = new Solver();
            try {
                solver.loadFile(inputFile.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Gagal memuat file: " + e.getMessage());
                continue;
            }

            boolean solution = solver.solve();
            long end = System.currentTimeMillis();
            System.out.printf("Waktu pencarian: " + (end - start) + "ms\n\n");
            System.out.println("Banyak kasus yang ditinjau: " + solver.getCheckedCases());

            if (solution && solver.isBoardFull()) {
                System.out.println("\nSolusi ditemukan!");
                solver.printSolution();
                System.out.print("\nApakah anda ingin menyimpan solusi? (ya/tidak): ");
                String saveSolution = scanner.nextLine().trim();
                
                if (saveSolution.equals("ya")) {
                    char[][] board = solver.getBoard();
                    int row = solver.getBoardRow();
                    int col = solver.getBoardCol();
                    saveSolutionToFile(board, filename, row, col);
                } else if (!saveSolution.equals("tidak")) {
                    System.out.println("Pilihan tidak valid!\n");
                    // continue;
                }
            } else {
                System.out.println("\nTidak ada solusi yang ditemukan.");
            }

        }

        scanner.close();
    }
    // Border untuk styling
    public static void border() {
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
    }

    private static void printHeader() {
        System.out.println("======================================");
        System.out.println("        IQ PUZZLER PRO SOLVER         ");
        System.out.println("======================================");
    }

    private static void printFooter() {
        System.out.println("\n======================================");
        System.out.println("        TERIMA KASIH TELAH COBA!      ");
        System.out.println("======================================");
    }

    private static void saveSolutionToFile(char[][] board, String inputFilename, int row, int col) {
        String outputFilename = "test/output/" + inputFilename.replace(".txt", "_solution.txt");
        File outputFile = new File(outputFilename);

        try {
            outputFile.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(outputFile);

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    writer.write(board[i][j] + " ");
                }
                writer.write("\n");
            }

            writer.close();
            System.out.println("Solusi disimpan ke: " + outputFilename);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan solusi: " + e.getMessage());
        }
    }
}
