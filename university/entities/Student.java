package university.entities;

import university.enums.StudentStatus;

public class Student extends Person {
    private StudentStatus status;
    private int yearOfStudy;

    public Student(int id, String name, String email, StudentStatus status, int yearOfStudy) {
        super(id, name, email);
        if (yearOfStudy < 1 || yearOfStudy > 6) {
            throw new IllegalArgumentException("Рік навчання має бути від 1 до 6!");
        }
        this.status = status;
        this.yearOfStudy = yearOfStudy;
    }

    public StudentStatus getStatus() { return status; }
    public void setStatus(StudentStatus status) { this.status = status; }
    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { 
        if (yearOfStudy < 1 || yearOfStudy > 6) throw new IllegalArgumentException("Некоректний рік навчання!");
        this.yearOfStudy = yearOfStudy; 
    }

    @Override
    public String toString() {
        return String.format("Студент [ID: %d] ПІБ: %s, Email: %s, Статус: %s, Курс: %d", 
                getId(), getName(), getEmail(), status, yearOfStudy);
    }
}