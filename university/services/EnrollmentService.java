package university.services;

import university.entities.Course;
import university.entities.Enrollment;
import university.entities.Student;
import university.enums.Grade;
import university.util.GPAUtils;

public class EnrollmentService {
    private Enrollment[] enrollments = new Enrollment[200];
    private int count = 0;

    public void enroll(Student student, Course course, String semester) {
        if (count >= enrollments.length) return;
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getStudent().getId() == student.getId() && 
                enrollments[i].getCourse().getId() == course.getId() &&
                enrollments[i].getSemester().equals(semester)) {
                System.out.println("Студент вже зарахований на цей курс у цьому семестрі!");
                return;
            }
        }
        enrollments[count++] = new Enrollment(student, course, semester);
        System.out.println("Студента успішно зараховано на курс!");
    }

    public Enrollment[] getAll() {
        Enrollment[] result = new Enrollment[count];
        System.arraycopy(enrollments, 0, result, 0, count);
        return result;
    }

    public void setGrade(int studentId, int courseId, Grade grade) {
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getStudent().getId() == studentId && enrollments[i].getCourse().getId() == courseId) {
                enrollments[i].setGrade(grade);
                System.out.println("Оцінку виставлено!");
                return;
            }
        }
        System.out.println("Зарахування не знайдено!");
    }

    public void payEnrollment(int studentId, int courseId) {
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getStudent().getId() == studentId && enrollments[i].getCourse().getId() == courseId) {
                enrollments[i].markAsPaid();
                System.out.println("Оплату зафіксовано!");
                return;
            }
        }
        System.out.println("Зарахування не знайдено!");
    }

    public void printTranscript(Student student) {
        System.out.println("\n=== ТРАНСКРИПТ СТУДЕНТА: " + student.getName() + " ===");
        boolean hasCourses = false;
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getStudent().getId() == student.getId()) {
                System.out.printf("- %s (%s семестр): Оцінка: %s | Оплата: %s\n", 
                        enrollments[i].getCourse().getTitle(), 
                        enrollments[i].getSemester(), 
                        enrollments[i].getGrade(),
                        enrollments[i].isPaid() ? "Так" : "Ні");
                hasCourses = true;
            }
        }
        if (!hasCourses) {
            System.out.println("Немає активних зарахувань.");
        } else {
            double gpa = GPAUtils.calculateStudentGPA(enrollments, count, student);
            System.out.printf("Поточний GPA: %.2f\n", gpa);
        }
    }
}