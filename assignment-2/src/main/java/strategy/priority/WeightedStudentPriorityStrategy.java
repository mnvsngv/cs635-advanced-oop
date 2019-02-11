package strategy.priority;

import models.Student;

public class WeightedStudentPriorityStrategy
        implements PriorityStrategy<Student> {

    @Override
    public double getPriority(Student student) {
        double unitWeight = 0.7;
        double gpaWeight = 0.3;

        return (gpaWeight * student.getGpa()/student.MAX_GPA)
                + (unitWeight * student.getUnits()/student.MAX_UNITS);
    }
}
