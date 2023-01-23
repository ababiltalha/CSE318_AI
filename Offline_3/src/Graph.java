import java.util.*;

class Graph {
    public int n; // number of vertices
    public ArrayList<Node> vertices;
    public LinkedList<Node>[] adjacencyList; // list of neighbours of every vertex
    public boolean[] visited;
    public int[] assignedColors; // color assigned to each vertex

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


        while (!doneColoring()) {
            for (int i = 0; i < n; i++) {
                usedColors[i]= false;
            }

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
                    if (assignedColors[index] == -1 && !node.neighborColors.contains(color)) {
                        node.neighborColors.add(color);
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

    boolean checkColoring(){
        for (int i = 0; i < n; i++) {
            for (Node node : adjacencyList[i]) {
                int index = node.index;
                if (assignedColors[index] == assignedColors[i]) return false;
            }
        }
        return true;
    }

    int getTimeSlot(String courseID) {
        int index = Integer.parseInt(courseID);
        return assignedColors[index-1];
    }

}