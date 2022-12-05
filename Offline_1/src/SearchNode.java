import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class SearchNode {
    public int k; // size of the board
    public int[][] board;
    public SearchNode prevNode; // previous/parent board
    public int cost; // gCost aka cost to reach this node from initial node

    public int[][] goalBoard; // goal board for the given size


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


    // calculates the selected heuristic function output
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
                    hCost+=abs(((board[i][j]-1)/k)-i)+abs(((board[i][j]-1)%k)-j);
            }
        }
        return hCost;
    }


    // returns the blank cell coordinates
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

    // checks if the current board is the goal board
    boolean goalBoardReached(){
        for (int i = 0; i < this.k; i++) {
            for (int j = 0; j < this.k; j++) {
                if (board[i][j]!=goalBoard[i][j])
                    return false;
            }
        }
        return true;
    }

    // copy the board into another new board
    void copyBoard(int[][] newBoard){
        for (int i = 0; i < this.k; i++) {
            for (int j = 0; j < this.k; j++) {
                newBoard[i][j]=this.board[i][j];
            }
        }
    }

    // basically swaps two entries
    void move(int row1, int column1, int row2, int column2){
        int temp = board[row1][column1];
        board[row1][column1] = board[row2][column2];
        board[row2][column2] = temp;
    }

    // returns a list of the possible neighbour nodes depending on the position of the blank cell
    ArrayList<SearchNode> getNeighbourNodes() {
        ArrayList<SearchNode> neighbourNodes= new ArrayList<>();
        int blankColumn= getBlankColumn();
        int blankRow= getBlankRow();

        // the UP move in the board (not possible if the row is the last row)
        if (blankRow!=this.k-1){
            int[][] newBoard= new int[k][k];
            copyBoard(newBoard);
            SearchNode newNode= new SearchNode(this.k, newBoard, this.cost+1,this);
            newNode.move(blankRow, blankColumn, blankRow+1, blankColumn);
            neighbourNodes.add(newNode);
        }

        // the DOWN move in the board (not possible if the row is the first row)
        if (blankRow!=0){
            int[][] newBoard= new int[k][k];
            copyBoard(newBoard);
            SearchNode newNode= new SearchNode(this.k, newBoard, this.cost+1,this);
            newNode.move(blankRow, blankColumn, blankRow-1, blankColumn);
            neighbourNodes.add(newNode);
        }

        // the LEFT move in the board (not possible if the column is the last column)
        if (blankColumn!=this.k-1){
            int[][] newBoard= new int[k][k];
            copyBoard(newBoard);
            SearchNode newNode= new SearchNode(this.k, newBoard, this.cost+1,this);
            newNode.move(blankRow, blankColumn, blankRow, blankColumn+1);
            neighbourNodes.add(newNode);
        }

        // the RIGHT move in the board (not possible if the column is the first column)
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
        String str="Cost: "+this.cost+"\n";
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                str+=board[i][j]+" ";
            }
            str+="\n";
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchNode that = (SearchNode) o;
        return Arrays.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
