package university.util;

import university.entities.Enrollment;
import university.entities.Student;
import university.enums.Grade;

public class GPAUtils {

    public static double calculateStudentGPA(Enrollment[] enrollments, int count, Student student) {
        double totalPoints = 0;
        int totalCredits = 0;
        boolean hasGrades = false;

        for (int i = 0; i < count; i++) {
            if (enrollments[i].getStudent().getId() == student.getId() && enrollments[i].getGrade() != Grade.NA) {
                int credits = enrollments[i].getCourse().getCredits();
                totalPoints += enrollments[i].getGrade().getPoints() * credits;
                totalCredits += credits;
                hasGrades = true;
            }
        }
        return hasGrades ? totalPoints / totalCredits : 0.0;
    }

    public static void bubbleSortStudentsByName(Student[] students, int count) {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (students[j].getName().compareToIgnoreCase(students[j + 1].getName()) > 0) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }
}