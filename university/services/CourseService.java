package university.services;

import university.entities.Course;
import university.entities.Teacher;

public class CourseService {
    private Course[] courses = new Course[100];
    private int count = 0;
    private int nextId = 1;

    public void addCourse(String title, int credits, Teacher teacher) {
        if (count >= courses.length) return;
        courses[count++] = new Course(nextId++, title, credits, teacher);
        System.out.println("Курс створено!");
    }

    public Course[] getAll() {
        Course[] result = new Course[count];
        System.arraycopy(courses, 0, result, 0, count);
        return result;
    }

    public Course findById(int id) {
        for (int i = 0; i < count; i++) {
            if (courses[i].getId() == id) return courses[i];
        }
        return null;
    }

    public void deleteCourse(int id) {
        int index = -1;
        for (int i = 0; i < count; i++) {
            if (courses[i].getId() == id) { index = i; break; }
        }
        if (index == -1) return;
        for (int i = index; i < count - 1; i++) courses[i] = courses[i + 1];
        courses[--count] = null;
        System.out.println("Курс видалено!");
    }
}