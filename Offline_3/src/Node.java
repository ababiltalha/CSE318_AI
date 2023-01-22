import java.util.ArrayList;

public class Node {
    public static int indexCount = 0;
    public int index;
    public Course course;
    public ArrayList<Integer> saturationDegree;
    public Node(Course course) {
        this.index = indexCount++;
        this.course = course;
        this.saturationDegree = new ArrayList<Integer>();
    }
}
