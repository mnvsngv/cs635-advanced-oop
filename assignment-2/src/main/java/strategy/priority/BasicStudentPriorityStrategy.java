package strategy.priority;

import models.Student;

public class BasicStudentPriorityStrategy
        implements PriorityStrategy<Student> {

    @Override
    public double getPriority(Student student) {
        double unitWeight = 0.7;
        double gpaWeight = 0.3;

        return (unitWeight * student.getUnits()) +
                (gpaWeight * student.getGpa());
    }
}
