public class SearchNode {
    public int k;
    public int[][] board;
    public SearchNode prevNode;
    public int cost;


    SearchNode(int k, int[][] board, int cost, SearchNode prevNode){
        this.k=k;
        this.board=board;
        this.cost=cost;
        this.prevNode=prevNode;
    }





}
