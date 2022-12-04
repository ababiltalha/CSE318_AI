import java.util.Scanner;

public class Main {
    public static boolean isSolvable(int k, int[][] board){
        int[] tempArray= new int[k*k];
        int i= 0;
        int inversionCount=0;

        for (int j = 0; j < k; j++) {
            for (int l = 0; l < k; l++) {
                tempArray[i++]=board[j][l];
            }
        }

        int blankRow=-1;
        for (int j = 0; j < k; j++) {
            for (int l = 0; l < k; l++) {
                if(0==board[j][l]) {
                    blankRow=j;
                    break;
                }
            }
        }

        for (i = 0; i < k*k; i++) {
            if(tempArray[i]==0) continue;
            for (int j = i; j < k*k; j++) {
                if(tempArray[j]==0) continue;
                if(tempArray[j]<tempArray[i]) inversionCount++;
            }
        }

        System.out.println(inversionCount);
        if (k%2!=0) {
            if (inversionCount%2==0) return true;
        } else {
            if (inversionCount%2==0){
                if(blankRow%2!=0) return true;
            } else {
                if(blankRow%2==0) return true;
            }
        }

        return false;
    }
    public static void main(String[] args) {
        Scanner scn= new Scanner(System.in);
        int k= scn.nextInt();
        int[][] board= new int[k][k];

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                String str=scn.next();
                if(str.contains("*"))
                    board[i][j]=0;
                else
                    board[i][j]=Integer.parseInt(str);
            }
        }

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }

        if(isSolvable(k,board)) {
            System.out.println("The given puzzle is solvable");
        }
        else System.out.println("The given puzzle is not solvable");
    }


}
