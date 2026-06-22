package university.services;

import university.entities.Teacher;
import university.enums.TeacherPosition;

public class TeacherService {
    private Teacher[] teachers = new Teacher[100];
    private int count = 0;
    private int nextId = 1;

    public void addTeacher(String name, String email, TeacherPosition position) {
        if (count >= teachers.length) return;
        teachers[count++] = new Teacher(nextId++, name, email, position);
        System.out.println("Викладача додано!");
    }

    public Teacher[] getAll() {
        Teacher[] result = new Teacher[count];
        System.arraycopy(teachers, 0, result, 0, count);
        return result;
    }

    public Teacher findById(int id) {
        for (int i = 0; i < count; i++) {
            if (teachers[i].getId() == id) return teachers[i];
        }
        return null;
    }

    public void updateTeacher(int id, String name, String email, TeacherPosition pos) {
        Teacher t = findById(id);
        if (t == null) throw new IllegalArgumentException("Викладача не знайдено!");
        t.setName(name);
        t.setEmail(email);
        t.setPosition(pos);
        System.out.println("Дані викладача оновлено!");
    }

    public void deleteTeacher(int id) {
        int index = -1;
        for (int i = 0; i < count; i++) {
            if (teachers[i].getId() == id) { index = i; break; }
        }
        if (index == -1) return;
        for (int i = index; i < count - 1; i++) teachers[i] = teachers[i + 1];
        teachers[--count] = null;
        System.out.println("Викладача видалено!");
    }
}