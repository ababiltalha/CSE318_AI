import java.util.*;

class Graph {
    public int n;
    public ArrayList<Node> vertices;
    public LinkedList<Node>[] adjacencyList;
    public boolean[] visited;
    public int[] assignedColors;

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
            System.out.print(vertices.get(i).course.courseID + " : " + adjacencyList[i].size() + " : ");
            for (int j = 0; j < adjacencyList[i].size(); j++) {
                System.out.print(adjacencyList[i].get(j).course.courseID + " ");
            }
            System.out.println();
        }
    }

    int colorGraph(ConstructiveHeuristic constructiveHeuristic) {
        for (int i = 0; i < n; i++) {
            assignedColors[i]= -1;
        }
        assignedColors[constructiveHeuristic.getNextNode(this).index]= 0;
        int totalAssignedColors = 1;

        boolean usedColors[]= new boolean[n];
        for (int i = 0; i < n; i++) {
            usedColors[i]= false;
        }

        while (!doneColoring()) {
            Node nextNode = constructiveHeuristic.getNextNode(this);
            if(nextNode == null) {
                printUncolored();
                break;
            }
            int i = nextNode.index;
//            printColors();
            for (Node node : adjacencyList[i]) {
                int index = node.index;
                if (assignedColors[index] != -1) usedColors[assignedColors[index]] = true;
            }
            int color;
            for (color = 0; color < n; color++) {
                if (!usedColors[color]) break;
            }

            // assign a color (timeslot) to the node
            assignedColors[i]=color;
            // if the heuristic is DSatur, update the saturation degree of the adjacent nodes
            if (constructiveHeuristic.toString().equals("DSatur")) {
                for (Node node : adjacencyList[i]) {
                    int index = node.index;
                    if (assignedColors[index] == -1 && !node.saturationDegree.contains(color)) {
                        node.saturationDegree.add(color);
                    }
                }
            }
//            System.out.println("Coloring " + vertices.get(i).course.courseID + " with color " + color);
            totalAssignedColors= Math.max(totalAssignedColors, color+1);
            for (int j = 0; j < n; j++) {
                usedColors[j]= false;
            }
        }
        return totalAssignedColors;
    }

    void printColors() {
        for (int i = 0; i < n; i++) {
            System.out.println(vertices.get(i).course.courseID + " : " + assignedColors[i]);
        }
    }

    void printUncolored() {
        for (int i = 0; i < n; i++) {
            if(assignedColors[i] == -1) {
                System.out.println(vertices.get(i).course.courseID);
            }
        }
    }

    boolean doneColoring() {
        for (int i = 0; i < n; i++) {
            if (assignedColors[i] == -1) return false;
        }
        return true;
    }

    int getTimeSlot(String courseID) {
        int index = Integer.parseInt(courseID);
        return assignedColors[index-1];
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