/*
 * @group 2
 * Name: main class
 * Process: execute the main program and apply the algorithm to the configuration data
 * Date: 28-12-2022
 */
package set_cover_problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Main {
    final static int MAX_COST = 100;
    final static int MIN_COST = 1;

    final static int MAX_M = 20;
    final static int MIN_M = 3;
    public static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        SCPModel model = null; // khởi tạo biến dữ liệu
        GreedySolverAlgorithm greedySolverAlgorithm = new GreedySolverAlgorithm(); // khởi tạo thuật toán
        String choice;
        double alpha = -1;
        boolean modelLoaded = false;

        do {
            displayMenu();
            choice = cin.readLine();
            if (choice.equalsIgnoreCase("M")) {
                model = getModelData(); // đầu vào dữ liệu
                modelLoaded = true;
                resetMethods(greedySolverAlgorithm); // cấu hính lại thuật toán
            } else if (choice.equalsIgnoreCase("P")) {
                if (!modelLoaded) { // check mô hình dữ liệu có lỗi hay không
                    System.out.println("\nERROR: No problem information has been loaded!\n");
                } else { // nếu không lỗi tiến hành
                    System.out.println(model);
                    System.out.format("Minimum coverage (alpha): ");
                    if (alpha == -1)
                        System.out.println("unspecified\n");
                    else
                        System.out.format("%.2f%%\n\n", 100 * alpha);
                }
            } else if (choice.equalsIgnoreCase("A")) {
                greedySolverAlgorithm.reset();
                System.out.println();
                alpha = getDouble("Enter minimum coverage (alpha): ", 0d, 1d);
                setMinCoverage(alpha, greedySolverAlgorithm);
                System.out.println();
            } else if (choice.equalsIgnoreCase("G")) {
                if (!modelLoaded) {
                    System.out.println("\nERROR: No problem information has been loaded!\n");
                } else if (alpha == -1) {
                    System.out.println("\nERROR: No minimum coverage percentage has been specified!\n");
                } else { // tiến hành thực thi thuật toán áp dụng với mô hình dữ liệu
                    greedySolverAlgorithm.setModel(model);// cấu hình dữ liệu đầu vào
                    greedySolverAlgorithm.solve(); // tiến hành áp dụng
                    greedySolverAlgorithm.print();// in dữ liệu
                }

            } else if (!choice.equalsIgnoreCase("Q")) {
                System.out.println("\nERROR: Invalid menu choice!\n");
            }

        } while (!choice.equalsIgnoreCase("Q"));

        System.out.println("\r\nBye!");

    }

    // xử lý toàn bộ quá trình thu thập dữ liệu người dùng
    public static SCPModel getModelData() {
        System.out.println();
        // Nhập số phần tử
        int n = Main.getInteger("Enter number of elements (n): ", 1, Integer.MAX_VALUE);
        // Nhập số bộ
        int m = Main.getInteger("Enter number of sets (m): ", 1, Integer.MAX_VALUE);

        SCPModel scpmodel = new SCPModel();
        for (int i = 1; i <= m; i++) {
            System.out.println("\nSet " + i + " details");
            double cost = (Math.random() * (MAX_COST - MIN_COST)) + MIN_COST;
            int j;
            List<Integer> elements = new ArrayList<>();
            int n_el = (int) (Math.random() * (MAX_M - MIN_M)) + MIN_M;
            int dem = 0;
            while (dem != n_el) {
                // j = Main.getInteger(" Enter an element covered by set " + i + " (0 to stop):
                // ", 0, n);
                j = (int) (Math.random() * (n - 1)) + 1;
                dem++;
                elements.add(j);
            }
            scpmodel.addElementSet(i, cost, elements);
        }
        // System.out.println("\n" + scpmodel);
        return scpmodel;
    }
    // reset lại giải thuật
    public static void resetMethods(GreedySolverAlgorithm greedySolverAlgorithm) {
        greedySolverAlgorithm.reset();
    }

    // đặt mức độ bao phủ min cho giải thuật
    public static void setMinCoverage(double alpha, GreedySolverAlgorithm greedySolverAlgorithm) {
        greedySolverAlgorithm.setMinCoverage(alpha);
    }

    // hiển thị ra màn hình
    public static void displayMenu() {
        System.out.println("   JAVA SET COVER PROBLEM SOLVER");
        System.out.println("M - Enter SCP model data");
        System.out.println("P - Print SCP instance");
        System.out.println("A - Set minimum coverage percentage");
        System.out.println("S - Print SCP instance of Fire Station Build");
        System.out.println("G - Solve SCP with Greedy's algorithm");
        System.out.println("Q - Quit\n");
        System.out.print("Enter choice: ");
    }

    // lấy một số nguyên trong [LB, UB]
    public static int getInteger(String prompt, int LB, int UB) {
        int x = 0;
        boolean valid;
        do {
            valid = true;
            System.out.print(prompt);
            try {
                x = Integer.parseInt(cin.readLine());
            } catch (IOException | NumberFormatException e) {
                valid = false;
            }

            if (valid && (x < LB || x > UB)) {
                valid = false;
            }

            if (!valid) {
                if (UB == Integer.MAX_VALUE && LB == -Integer.MAX_VALUE)
                    System.out.format("ERROR: Input must be an integer in [-infinity, infinity]!\n\n");
                else if (UB == Integer.MAX_VALUE)
                    System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n\n", LB);
                else if (LB == -Integer.MAX_VALUE)
                    System.out.format("ERROR: Input must be an integer in [-infinity, %d]!\n\n", UB);
                else
                    System.out.format("ERROR: Input must be an integer in [%d, %d]!\n\n", LB, UB);
            }
        } while (!valid);
        return x;
    } // kết thúc getInteger

    // lấy một số kiểu double trong [LB, UB]
    public static double getDouble(String prompt, double LB, double UB) {
        double x = 0;
        boolean valid;
        do {
            valid = true;
            System.out.print(prompt);
            try {
                x = Double.parseDouble(cin.readLine());
            } catch (IOException | NumberFormatException e) {
                valid = false;
            }

            if (valid && (x < LB || x > UB)) {
                valid = false;
            }

            if (!valid) {
                if (UB == Double.MAX_VALUE && LB == -Double.MAX_VALUE)
                    System.out.format("ERROR: Input must be a real number in [-infinity, infinity]!\n\n");
                else if (UB == Double.MAX_VALUE)
                    System.out.format("ERROR: Input must be a real number in [%.2f, infinity]!\n\n", LB);
                else if (LB == -Double.MAX_VALUE)
                    System.out.format("ERROR: Input must be a real number in [-infinity, %.2f]!\n\n", UB);
                else
                    System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
            }
        } while (!valid);
        return x;
    } // kết thúc getDouble

}
