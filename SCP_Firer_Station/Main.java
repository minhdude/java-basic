
/*
 * @group 2
 * Name: main class
 * Process: execute the main program and apply the algorithm to the configuration data
 * Date: 28-12-2022
*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.io.*;

public class Main {

    final static int MAX_COST = 100;
    final static int MIN_COST = 1;

    final static int MAX_M = 20;
    final static int MIN_M = 3;
    public static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

    
    public static void main(String[] args) throws IOException {
        SCPModel scpmodel = new SCPModel();
        scpmodel = getModelData();
        scpmodel.output();

        float time_contraint = 8; 
        GreedySolver gd = new GreedySolver() ;
        gd.setTime_contraint(time_contraint);
        gd.set_model(scpmodel);
        gd.set_compTime(0);
        gd.solve();
        
      

    }

    public static int getInteger(String prompt, int LB, int UB) {
        int x = 0;
        boolean valid;
        do {
            valid = true;
            System.out.print(prompt);
            try {
                x = Integer.parseInt(cin.readLine());
            } catch (IOException e) {
                valid = false;
            } catch (NumberFormatException e) {
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

    public static SCPModel getModelData() {
        System.out.println();
        // Nhập số phần tử
        int n = Main.getInteger("Enter number of City: ", 1, Integer.MAX_VALUE);
        // Nhập số bộ
        int m = n;

        SCPModel scpmodel = new SCPModel();
        for (int i = 0; i<m; i++) {
            System.out.println("\nSet " + i + " details");

            double cost = (Math.random() * (MAX_COST - MIN_COST)) + MIN_COST;
            double j = 0;
            ArrayList<Double> elements = new ArrayList<Double>();

            int dem = 0;
            while (dem != n) {
                if (dem == i) {
                    j = 0;
                } else {
                    j = (int) (Math.random() * (20 - 4)) + 4;
                }
                dem++;
                elements.add(j);
            }
           scpmodel.addCityToModel(i, cost, elements);

        }

        // System.out.println("\n" + scpmodel);
        return scpmodel;
    }

}
