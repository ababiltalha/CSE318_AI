import java.util.*;

class Graph {
    private int n;
    private ArrayList<Node> vertices;
    private LinkedList<Node>[] adjacencyList;
    private boolean[] visited;
    private int[] assignedColors;

    Graph(ArrayList<Node> vertices) {
        this.n= vertices.size();
        this.vertices= vertices;
        adjacencyList= new LinkedList[n];
        visited= new boolean[n];
        assignedColors= new int[n];

        for (int i = 0; i < n; i++) {
            adjacencyList[i]= new LinkedList<Node>();
            visited[i]= false;
            assignedColors[i]= -1;
        }
    }

    void connect(Node from, Node to) {
        adjacencyList[from.index].add(to);
        adjacencyList[to.index].add(from);
    }

    void printAdjacencyList() {
        for (int i = 0; i < n; i++) {
            System.out.print(vertices.get(i).course.courseID + " : ");
            for (int j = 0; j < adjacencyList[i].size(); j++) {
                System.out.print(adjacencyList[i].get(j).course.courseID + " ");
            }
            System.out.println();
        }
    }

    int colorGraph() {
        for (int i = 0; i < n; i++) {
            assignedColors[i]= -1;
        }
        assignedColors[0]= 0;
        int totalAssignedColors = 1;

        boolean usedColors[]= new boolean[n];
        for (int i = 0; i < n; i++) {
            usedColors[i]= false;
        }

        for (int i = 1; i < n; i++) {
            for (Node node : adjacencyList[i]) {
                int index = node.index;
                if (assignedColors[index] != -1) usedColors[assignedColors[index]] = true;
            }
            int color;
            for (color = 0; color < n; color++) {
                if (!usedColors[color]) break;
            }

            assignedColors[i]=color;
            totalAssignedColors= Math.max(totalAssignedColors, color+1);
            for (int j = 0; j < n; j++) {
                usedColors[j]= false;
            }
        }
        return totalAssignedColors;
    }

    ArrayList<Node> DFS(Node start) {
        for (int i = 0; i < n; i++) {
            visited[i]= false;
        }
        Stack<Node> stack= new Stack<>();
        stack.push(start);
        ArrayList<Node> traversal= new ArrayList<Node>();

        while (!stack.empty()) {
            Node current= stack.pop();
            visited[current.index]= true;
            traversal.add(current);
//            System.out.print(current+" ");

            Iterator<Node> i= adjacencyList[current.index].listIterator();
            while (i.hasNext()) {
                Node next= i.next();
                if (!visited[next.index]) {
                    stack.push(next);
                }
            }
        }
//        System.out.println("\nDFS");
        return traversal;
    }

    ArrayList<Node> BFS(Node start){
        for (int i = 0; i < n; i++) {
            visited[i]= false;
        }
        Queue<Node> queue= new LinkedList<>();
        queue.add(start);
        ArrayList<Node> traversal= new ArrayList<Node>();


        while(!queue.isEmpty()){
            Node current= queue.poll();
            visited[current.index]= true;
            traversal.add(current);
//            System.out.print(current+" ");

            Iterator<Node> i = adjacencyList[current.index].listIterator();
            while (i.hasNext()) {
                Node next = i.next();
                if (!visited[next.index]) {
                    visited[next.index] = true;
                    queue.add(next);
                }
            }
        }
//        System.out.println("\nBFS");
        return traversal;
    }
}