package university;

import university.entities.Course;
import university.entities.Student;
import university.entities.Teacher;
import university.enums.Grade;
import university.enums.StudentStatus;
import university.enums.TeacherPosition;
import university.services.CourseService;
import university.services.EnrollmentService;
import university.services.StudentService;
import university.services.TeacherService;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8);    
    private static final StudentService studentService = new StudentService();
    private static final TeacherService teacherService = new TeacherService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();

    public static void main(String[] args) {
  
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));

        seedData();

        while (true) {
            try {
                System.out.println("\n=== ГОЛОВНЕ МЕНЮ УНІВЕРСИТЕТУ ===");
                System.out.println("1. Студенти");
                System.out.println("2. Викладачі");
                System.out.println("3. Курси");
                System.out.println("4. Зарахування та Оцінки");
                System.out.println("5. Звіти / Пошук");
                System.out.println("0. Вихід");
                System.out.print("Оберіть пункт: ");

                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0) {
                    System.out.println("Дякуємо за використання системи!");
                    break;
                }

                switch (choice) {
                    case 1 -> studentMenu();
                    case 2 -> teacherMenu();
                    case 3 -> courseMenu();
                    case 4 -> enrollmentMenu();
                    case 5 -> reportsMenu();
                    default -> System.out.println("Невірний пункт меню!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Введіть число!");
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації даних: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Сталася непередбачувана помилка: " + e.getMessage());
            }
        }
    }

    private static void studentMenu() {
        System.out.println("\n--- МЕНЮ: СТУДЕНТИ ---");
        System.out.println("1. Додати студента");
        System.out.println("2. Показати всіх студентів");
        System.out.println("3. Оновити дані студента");
        System.out.println("4. Видалити студента");
        System.out.println("5. Фільтрація та сортування за ПІБ");
        System.out.print("Дія: ");
        int action = Integer.parseInt(scanner.nextLine());

        switch (action) {
            case 1 -> {
                System.out.print("ПІБ: "); String name = scanner.nextLine();
                System.out.print("Email: "); String email = scanner.nextLine();
                System.out.print("Рік навчання (1-6): "); int year = Integer.parseInt(scanner.nextLine());
                studentService.addStudent(name, email, StudentStatus.Активний, year);
            }
            case 2 -> {
                Student[] all = studentService.getAll();
                if (all.length == 0) System.out.println("Студентів немає.");
                for (Student s : all) System.out.println(s);
            }
            case 3 -> {
                System.out.print("ID студента для оновлення: "); int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Нове ПІБ: "); String name = scanner.nextLine();
                System.out.print("Новий Email: "); String email = scanner.nextLine();
                System.out.print("Новий Рік: "); int year = Integer.parseInt(scanner.nextLine());
                studentService.updateStudent(id, name, email, year);
            }
            case 4 -> {
                System.out.print("ID студента для видалення: "); int id = Integer.parseInt(scanner.nextLine());
                studentService.deleteStudent(id);
            }
            case 5 -> studentService.showFiltered(null, null, true);
        }
    }

    private static void teacherMenu() {
        System.out.println("\n--- МЕНЮ: ВИКЛАДАЧІ ---");
        System.out.println("1. Додати викладача");
        System.out.println("2. Показати всіх");
        System.out.print("Дія: ");
        int action = Integer.parseInt(scanner.nextLine());

        if (action == 1) {
            System.out.print("ПІБ: "); String name = scanner.nextLine();
            System.out.print("Email: "); String email = scanner.nextLine();
            System.out.println("Посада (1 - Ассистент, 2 - Лектор, 3 - Професор): ");
            int posChoice = Integer.parseInt(scanner.nextLine());
            TeacherPosition pos = switch (posChoice) {
                case 2 -> TeacherPosition.Лектор;
                case 3 -> TeacherPosition.Професор;
                default -> TeacherPosition.Ассистент;
            };
            teacherService.addTeacher(name, email, pos);
        } else if (action == 2) {
            for (Teacher t : teacherService.getAll()) System.out.println(t);
        }
    }

    private static void courseMenu() {
        System.out.println("\n--- МЕНЮ: КУРСИ ---");
        System.out.println("1. Створити курс");
        System.out.println("2. Показати всі курси");
        System.out.print("Дія: ");
        int action = Integer.parseInt(scanner.nextLine());

        if (action == 1) {
            System.out.print("Назва курсу: "); String title = scanner.nextLine();
            System.out.print("Кредити: "); int credits = Integer.parseInt(scanner.nextLine());
            System.out.print("ID викладача: "); int tId = Integer.parseInt(scanner.nextLine());
            Teacher t = teacherService.findById(tId);
            courseService.addCourse(title, credits, t);
        } else if (action == 2) {
            for (Course c : courseService.getAll()) System.out.println(c);
        }
    }

    private static void enrollmentMenu() {
        System.out.println("\n--- МЕНЮ: ЗАРАХУВАННЯ ТА ОЦІНКИ ---");
        System.out.println("1. Зарахувати студента на курс");
        System.out.println("2. Виставити оцінку");
        System.out.println("3. Позначити оплату курсу");
        System.out.println("4. Переглянути транскрипт студента (та GPA)");
        System.out.print("Дія: ");
        int action = Integer.parseInt(scanner.nextLine());

        switch (action) {
            case 1 -> {
                System.out.print("ID Студента: "); int sId = Integer.parseInt(scanner.nextLine());
                System.out.print("ID Курсу: "); int cId = Integer.parseInt(scanner.nextLine());
                System.out.print("Семестр (напр. Осінь-2026): "); String sem = scanner.nextLine();
                
                Student s = studentService.findById(sId);
                Course c = courseService.findById(cId);
                if (s == null || c == null) throw new IllegalArgumentException("Студента або курсу не існує!");
                enrollmentService.enroll(s, c, sem);
            }
            case 2 -> {
                System.out.print("ID Студента: "); int sId = Integer.parseInt(scanner.nextLine());
                System.out.print("ID Курсу: "); int cId = Integer.parseInt(scanner.nextLine());
                System.out.print("Оцінка (G1, G2, G3, G4, G5, G6, G7, G8, G9, G10, G11, G12): "); String gradeStr = scanner.nextLine().toUpperCase();
                enrollmentService.setGrade(sId, cId, Grade.valueOf(gradeStr));
            }
            case 3 -> {
                System.out.print("ID Студента: "); int sId = Integer.parseInt(scanner.nextLine());
                System.out.print("ID Курсу: "); int cId = Integer.parseInt(scanner.nextLine());
                enrollmentService.payEnrollment(sId, cId);
            }
            case 4 -> {
                System.out.print("ID Студента: "); int sId = Integer.parseInt(scanner.nextLine());
                Student s = studentService.findById(sId);
                if (s == null) System.out.println("Студента не знайдено!");
                else enrollmentService.printTranscript(s);
            }
        }
    }

    private static void reportsMenu() {
        System.out.println("\n--- МЕНЮ: ЗВІТИ ТА ПОШУК ---");
        System.out.println("1. Пошук студента за ім'ям / Email");
        System.out.println("2. Список боргів з оплати (paid=false)");
        System.out.print("Дія: ");
        int action = Integer.parseInt(scanner.nextLine());

        if (action == 1) {
            System.out.print("Введіть пошуковий запит: ");
            String query = scanner.nextLine().toLowerCase();
            for (Student s : studentService.getAll()) {
                if (s.getName().toLowerCase().contains(query) || s.getEmail().toLowerCase().contains(query)) {
                    System.out.println(s);
                }
            }
        } else if (action == 2) {
            System.out.println("=== НЕОПЛАЧЕНІ КУРСИ ===");
            for (var e : enrollmentService.getAll()) {
                if (!e.isPaid()) {
                    System.out.println(e);
                }
            }
        }
    }

    private static void seedData() {
        teacherService.addTeacher("Олександр Петренко", "petrenko@univ.edu", TeacherPosition.Професор);
        teacherService.addTeacher("Марія Ковальчук", "kovalchuk@univ.edu", TeacherPosition.Лектор);

        studentService.addStudent("Іван Іванов", "ivanov@gmail.com", StudentStatus.Активний, 2);
        studentService.addStudent("Анна Бойко", "anna.b@gmail.com", StudentStatus.Активний, 1);

        Teacher t1 = teacherService.findById(1);
        courseService.addCourse("Об'єктно-орієнтоване програмування", 5, t1);
        courseService.addCourse("Вища математика", 4, t1);
    }
}