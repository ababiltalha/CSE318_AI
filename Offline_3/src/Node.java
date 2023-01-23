import java.util.ArrayList;

public class Node {
    public static int indexCount = 0;
    public int index;
    public Course course;
    public ArrayList<Integer> neighborColors; // to keep a list of unique colors among neighbors, used in DSatur
    public Node(Course course) {
        this.index = indexCount++;
        this.course = course;
        this.neighborColors = new ArrayList<Integer>();
    }
}
