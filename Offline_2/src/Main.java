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
        VAH1 vah1 = new VAH1();
        LatinSquareSolver latinSquareSolver = new LatinSquareSolver(latinSquare, vah1);
        long startTime = System.currentTimeMillis();
        if(latinSquareSolver.backtrack()) {
            System.out.println(latinSquare);
            System.out.println("#Backtracks = "+latinSquareSolver.getBacktrackCount());
            System.out.println("#Node = "+latinSquareSolver.getNodeCount());
            System.out.println("Runtime = "+(System.currentTimeMillis()-startTime)+"ms");
        }
        else System.out.println("No solution found for backtracking");



        if(solutionCheck(latinSquare.latinSquare)) System.out.println("Solution is correct");
        else System.out.println("Solution is incorrect");


    }

    private static boolean solutionCheck(Variable[][] variables) {
        int N = variables.length;
        boolean[][] rowCheck = new boolean[N][N];
        boolean[][] colCheck = new boolean[N][N];
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                int val = variables[i][j].value-1;
                if (val<0 || rowCheck[i][val] || colCheck[j][val]) return false;
                rowCheck[i][val] = true;
                colCheck[j][val] = true;
            }
        }
        return true;
    }
}
