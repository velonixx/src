package university.services;

import university.entities.Student;
import university.enums.StudentStatus;
import university.util.GPAUtils;

public class StudentService {
    private Student[] students = new Student[100];
    private int count = 0;
    private int nextId = 1;

    public void addStudent(String name, String email, StudentStatus status, int year) {
        if (count >= students.length) {
            System.out.println("Помилка: база даних студентів заповнена!");
            return;
        }
        students[count++] = new Student(nextId++, name, email, status, year);
        System.out.println("Студента успішно додано!");
    }

    public Student[] getAll() {
        Student[] result = new Student[count];
        System.arraycopy(students, 0, result, 0, count);
        return result;
    }

    public Student findById(int id) {
        for (int i = 0; i < count; i++) {
            if (students[i].getId() == id) return students[i];
        }
        return null;
    }

    public void updateStudent(int id, String name, String email, int year) {
        Student student = findById(id);
        if (student == null) throw new IllegalArgumentException("Студента з ID " + id + " не знайдено!");
        student.setName(name);
        student.setEmail(email);
        student.setYearOfStudy(year);
        System.out.println("Дані студента оновлено!");
    }

    public void deleteStudent(int id) {
        int index = -1;
        for (int i = 0; i < count; i++) {
            if (students[i].getId() == id) { index = i; break; }
        }
        if (index == -1) {
            System.out.println("Студента не знайдено!");
            return;
        }
        for (int i = index; i < count - 1; i++) {
            students[i] = students[i + 1];
        }
        students[--count] = null;
        System.out.println("Студента видалено!");
    }

    public void showFiltered(StudentStatus status, Integer year, boolean sortByName) {
        Student[] temp = getAll();
        if (sortByName) {
            GPAUtils.bubbleSortStudentsByName(temp, temp.length);
        }
        
        int displayed = 0;
        for (Student s : temp) {
            if (status != null && s.getStatus() != status) continue;
            if (year != null && s.getYearOfStudy() != year) continue;
            System.out.println(s);
            displayed++;
        }
        if (displayed == 0) System.out.println("Записів не знайдено за вказаними фільтрами.");
    }
}