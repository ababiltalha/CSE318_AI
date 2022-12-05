import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class SearchNode {
    public int k;
    public int[][] board;
    public SearchNode prevNode;
    public int cost;

    public int[][] goalBoard;


    SearchNode(int k, int[][] board, int cost, SearchNode prevNode){
        this.k=k;
        this.board=board;
        this.cost=cost;
        this.prevNode=prevNode;
        int count=1;
        this.goalBoard=new int[k][k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                goalBoard[i][j]=count++;
            }
        }
        goalBoard[k-1][k-1]=0;
    }



    int hammingHeuristic(){
        int hCost=0;
        for (int i = 0; i < this.k; i++) {
            for (int j = 0; j < this.k; j++) {
                if ((board[i][j]!=0) && (board[i][j]!=goalBoard[i][j])) {
                    hCost++;
                }
            }
        }
        return hCost;
    }

    int manhattanHeuristic(){
        int hCost=0;
        for (int i = 0; i < this.k; i++) {
            for (int j = 0; j < this.k; j++) {
                if (board[i][j]!=0)
                    hCost+=abs((board[i][j]/k)-i)+abs((board[i][j]%k)-j);
            }
        }
        return hCost;
    }


    int getBlankRow(){
        int blankRow=-1;
        for (int i = 0; i < this.k; i++) {
            for (int j = 0; j < this.k; j++) {
                if (board[i][j]==0)
                    blankRow=i;
            }
        }
        return blankRow;
    }

    int getBlankColumn(){
        int blankColumn=-1;
        for (int i = 0; i < this.k; i++) {
            for (int j = 0; j < this.k; j++) {
                if (board[i][j]==0)
                    blankColumn=j;
            }
        }
        return blankColumn;
    }

    boolean goalBoardReached(){
        for (int i = 0; i < this.k; i++) {
            for (int j = 0; j < this.k; j++) {
                if (board[i][j]!=goalBoard[i][j])
                    return false;
            }
        }
        return true;
    }

    void copyBoard(int[][] newBoard){
        for (int i = 0; i < this.k; i++) {
            for (int j = 0; j < this.k; j++) {
                newBoard[i][j]=this.board[i][j];
            }
        }
    }

    void move(int row1, int column1, int row2, int column2){
        int temp = board[row1][column1];
        board[row1][column1] = board[row2][column2];
        board[row2][column2] = temp;
    }

    ArrayList<SearchNode> getNeighbourNodes() {
        ArrayList<SearchNode> neighbourNodes= new ArrayList<>();
        int blankColumn= getBlankColumn();
        int blankRow= getBlankRow();

        if (blankRow!=this.k-1){
            int[][] newBoard= new int[k][k];
            copyBoard(newBoard);
            SearchNode newNode= new SearchNode(this.k, newBoard, this.cost+1,this);
            newNode.move(blankRow, blankColumn, blankRow+1, blankColumn);
            neighbourNodes.add(newNode);
        }

        if (blankRow!=0){
            int[][] newBoard= new int[k][k];
            copyBoard(newBoard);
            SearchNode newNode= new SearchNode(this.k, newBoard, this.cost+1,this);
            newNode.move(blankRow, blankColumn, blankRow-1, blankColumn);
            neighbourNodes.add(newNode);
        }

        if (blankColumn!=this.k-1){
            int[][] newBoard= new int[k][k];
            copyBoard(newBoard);
            SearchNode newNode= new SearchNode(this.k, newBoard, this.cost+1,this);
            newNode.move(blankRow, blankColumn, blankRow, blankColumn+1);
            neighbourNodes.add(newNode);
        }

        if (blankColumn!=0){
            int[][] newBoard= new int[k][k];
            copyBoard(newBoard);
            SearchNode newNode= new SearchNode(this.k, newBoard, this.cost+1,this);
            newNode.move(blankRow, blankColumn, blankRow, blankColumn-1);
            neighbourNodes.add(newNode);
        }

        return neighbourNodes;
    }

    @Override
    public String toString() {
        String str="Cost:"+this.cost+"\n";
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                str+=board[i][j]+" ";
            }
            str+="\n";
        }
        return str;
    }
}
