import java.util.*;

public class Main {
    public static boolean isSolvable(int k, int[][] board){
        int[] tempArray= new int[k*k];
        int i= 0;
        int inversionCount=0;

        // convert the board to an array
        for (int j = 0; j < k; j++) {
            for (int l = 0; l < k; l++) {
                tempArray[i++]=board[j][l];
            }
        }

        // find out the blank cell row number
        int blankRow=-1;
        for (int j = 0; j < k; j++) {
            for (int l = 0; l < k; l++) {
                if(0==board[j][l]) {
                    blankRow=j;
                    break;
                }
            }
        }

        // count total inversions
        for (i = 0; i < k*k; i++) {
            if(tempArray[i]==0) continue;
            for (int j = i; j < k*k; j++) {
                if(tempArray[j]==0) continue;
                if(tempArray[j]<tempArray[i]) inversionCount++;
            }
        }

//        System.out.println(inversionCount);
        if (k%2!=0) {
            // if grid size k is odd, inversion count needs to be even
            if (inversionCount%2==0) return true;
        } else {
            // if grid size k is even,
            // the blank is on an even row counting from the bottom (0 indexed even rows) and number of inversions is odd
            // or, the blank is on an odd row counting from the bottom (0 indexed odd rows) and number of inversions is even
            if (inversionCount%2==0){
                if(blankRow%2!=0) return true;
            } else {
                if(blankRow%2==0) return true;
            }
        }

        return false;
    }

    // print moves to reach the goal board
    private static void printMoves(SearchNode currentNode) {
        if(currentNode==null) return;
        printMoves(currentNode.prevNode);
        System.out.println(currentNode);
    }

    public static void main(String[] args) {
        Scanner scn= new Scanner(System.in);
        int k= scn.nextInt();
        int[][] board= new int[k][k];

        // input sequence
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                String str=scn.next();
                if(str.contains("*"))
                    board[i][j]=0;
                else
                    board[i][j]=Integer.parseInt(str);
            }
        }

        if(isSolvable(k,board)) System.out.println("The given puzzle is solvable");
        else {
            System.out.println("The given puzzle is not solvable");
            return;
        }

        System.out.println("Choose heuristic:" +
                "\n1. Hamming Distance" +
                "\n2. Manhattan Distance");
        int heuristic= scn.nextInt();

        SearchNode initialNode= new SearchNode(k, board, 0, null);

        int exploredNodes=0; // those who have exited the queue
        int expandedNodes=1; // those who have entered the queue, the initial node

        HammingCostComparator hammingComparator=new HammingCostComparator();
        ManhattanCostComparator manhattanComparator=new ManhattanCostComparator();
        PriorityQueue<SearchNode> openList; // open list (min priority queue)

        // priority queue according to heuristic function
        if(heuristic==1) openList= new PriorityQueue<>(hammingComparator);
        else openList= new PriorityQueue<>(manhattanComparator);


        HashSet<SearchNode> closedList= new HashSet<>(); // closed list

        openList.add(initialNode);
        SearchNode currentNode=initialNode;
        while(!openList.isEmpty()){
            // pop from the open list and insert it in the closed list
            currentNode=openList.poll();
            exploredNodes++;
            if (currentNode.goalBoardReached()){
                System.out.println("Goal board reached. Total moves: "+currentNode.cost);
                break;
            }
            closedList.add(currentNode);
            // check if the neighbours are in the closed list, if not add them to the OPEN list
            for (SearchNode node :
                    currentNode.getNeighbourNodes()) {
                if(!closedList.contains(node)) {
                    expandedNodes++;
                    openList.add(node);
                }
            }
        }
        printMoves(currentNode);
        System.out.println("Explored nodes: "+exploredNodes);
        System.out.println("Expanded nodes: "+expandedNodes);


    }


}
