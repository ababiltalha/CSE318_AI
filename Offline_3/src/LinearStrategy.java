import java.util.ArrayList;

public class LinearStrategy extends PenaltyStrategy {
    @Override
    public double getAveragePenalty(Graph graph, ArrayList<Student> students) {
        double totalPenalty = 0;
        for (Student student : students) {
            for (int i = 0; i < student.numberOfCourses-1; i++) {
                for (int j = i + 1; j < student.numberOfCourses; j++) {
                    String course1 = student.courses.get(i);
                    String course2 = student.courses.get(j);
                    int timeslot1 = graph.getTimeSlot(course1);
                    int timeslot2 = graph.getTimeSlot(course2);
                    int gap = Math.abs(timeslot1 - timeslot2);
                    if (gap <= 5) totalPenalty += 2*(5-gap);
                }
            }
        }
        return totalPenalty / students.size();
    }

    @Override
    public String toString() {
        return "Linear Strategy";
    }
}
