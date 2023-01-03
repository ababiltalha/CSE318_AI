import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File input = new File("data/data-new/d-10-01.txt");
        Scanner scn = null;
        try {
            scn = new Scanner(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int N = scn.nextInt();
        int[][] inputSquare = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                inputSquare[i][j] = scn.nextInt();
            }
        }

        LatinSquare latinSquare = new LatinSquare(N, inputSquare);
        // ask for input which heuristic to use
        System.out.println("Choose a heuristic: ");
        System.out.println("1. Smallest domain");
        System.out.println("2. Maximum degree to unassigned variables");
        System.out.println("3. (1), but with tie-breaking by (2)");
        System.out.println("4. Minimum of (1)/(2)");
        System.out.println("5. Random seeded");
        scn = new Scanner(System.in);
        int choice = scn.nextInt();

        LatinSquareSolver latinSquareSolver = null;
        if (choice == 1) latinSquareSolver = new LatinSquareSolver(latinSquare, new VAH1());
        else if (choice == 2) latinSquareSolver = new LatinSquareSolver(latinSquare, new VAH2());
        else if (choice == 3) latinSquareSolver = new LatinSquareSolver(latinSquare, new VAH3());
        else if (choice == 4) latinSquareSolver = new LatinSquareSolver(latinSquare, new VAH4());
        else latinSquareSolver = new LatinSquareSolver(latinSquare, new VAH5());

        boolean forwardChecking = false;
        // ask for input whether to use forward checking or not
        System.out.println("Use forward checking? (y/n)");
        String forwardCheckingInput = scn.next();
        if (forwardCheckingInput.equalsIgnoreCase("y")) forwardChecking = true;
        long startTime = System.currentTimeMillis();
        // simple backtrack but domain contraction (?)
        if (!forwardChecking) {
            if (latinSquareSolver.backtrack()) {
                long runtime = System.currentTimeMillis() - startTime;
                System.out.println(latinSquare);
                System.out.println("#Backtracks = " + latinSquareSolver.getBacktrackCount());
                System.out.println("#Node = " + latinSquareSolver.getNodeCount());
                System.out.println("Runtime = " + runtime + "ms");
            } else System.out.println("No solution found for backtracking");
        }
        // backtrack with forward checking
        else {
            if (latinSquareSolver.backtrackWithForwardChecking()) {
                long runtime = System.currentTimeMillis() - startTime;
                System.out.println(latinSquare);
                System.out.println("With forward checking");
                System.out.println("#Backtracks = " + latinSquareSolver.getBacktrackCount());
                System.out.println("#Node = " + latinSquareSolver.getNodeCount());
                System.out.println("Runtime = " + runtime + "ms");
            } else System.out.println("No solution found for backtracking with forward checking");
        }
        if(solutionCheck(latinSquare.latinSquare)) System.out.println("Solution is correct");
        else System.out.println("Solution is incorrect");


    }

    // function for checking if the solution is correct
    private static boolean solutionCheck(Variable[][] latinSquare) {
        int N = latinSquare.length;
        boolean[][] rowCheck = new boolean[N][N];
        boolean[][] colCheck = new boolean[N][N];
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                int val = latinSquare[i][j].value-1;
                if (val<0 || rowCheck[i][val] || colCheck[j][val]) return false;
                rowCheck[i][val] = true;
                colCheck[j][val] = true;
            }
        }
        return true;
    }
}
