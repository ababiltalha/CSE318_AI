public class Node {
    public static int indexCount = 0;
    public int index;
    public Course course;

    public Node(Course course) {
        this.index = indexCount++;
        this.course = course;
    }
}
