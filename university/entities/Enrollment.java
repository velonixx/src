package university.entities;

import university.enums.Grade;
import university.interfaces.Payable;

public class Enrollment implements Payable {
    private Student student;
    private Course course;
    private String semester;
    private Grade grade;
    private boolean paid;

    public Enrollment(Student student, Course course, String semester) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Студент та курс не можуть бути null!");
        }
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.grade = Grade.NA;
        this.paid = false;
    }

    @Override
    public void markAsPaid() { this.paid = true; }
    @Override
    public boolean isPaid() { return paid; }

    // Getters and Setters
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public String getSemester() { return semester; }
    public Grade getGrade() { return grade; }
    public void setGrade(Grade grade) { this.grade = grade; }

    @Override
    public String toString() {
        return String.format("Зарахування -> Студент: %s | Курс: \"%s\" | Семестр: %s | Оцінка: %s | Оплата: %s",
                student.getName(), course.getTitle(), semester, grade, (paid ? "Оплачено" : "Х"));
    }
}