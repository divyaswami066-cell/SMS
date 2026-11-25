import java.util.ArrayList;
import java.util.Scanner;

public class Student {
    private int id;
    private String name;
    private String department;
    private int[] marks;

    public Student(int id, String name, String department, int[] marks) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int calculateTotal() {
        int total = 0;

        for (int mark : marks) {
            total = total + mark;
        }

        return total;
    }

    public double calculateAverage() {
        return calculateTotal() / (double) marks.length;
    }

    public String calculateGrade() {
        double average = calculateAverage();

        if (average >= 90) {
            return "A+";
        } else if (average >= 80) {
            return "A";
        } else if (average >= 70) {
            return "B";
        } else if (average >= 60) {
            return "C";
        } else if (average >= 50) {
            return "D";
        } else {
            return "Fail";
        }
    }

    public String calculateResult() {
        for (int mark : marks) {
            if (mark < 35) {
                return "FAIL";
            }
        }

        return "PASS";
    }

    public void displayDetails() {
        System.out.println("\nStudent ID   : " + id);
        System.out.println("Name         : " + name);
        System.out.println("Department   : " + department);

        for (int i = 0; i < marks.length; i++) {
            System.out.println("Subject " + (i + 1) + " Marks : " + marks[i]);

            if (marks[i] >= 35) {
                System.out.println("Subject " + (i + 1) + " Result: PASS");
            } else {
                System.out.println("Subject " + (i + 1) + " Result: FAIL");
            }
        }

        System.out.println("Total Marks : " + calculateTotal());
        System.out.println("Average     : " + calculateAverage());
        System.out.println("Grade       : " + calculateGrade());
        System.out.println("Result      : " + calculateResult());
    }

    public void updateMarks(int[] newMarks) {
        marks = newMarks;
    }

    public static int readMark(Scanner sc, int subjectNumber) {
        int mark;

        while (true) {
            System.out.print("Enter Subject " + subjectNumber + " Marks (0-100): ");

            if (sc.hasNextInt()) {
                mark = sc.nextInt();

                if (mark >= 0 && mark <= 100) {
                    return mark;
                } else {
                    System.out.println("Marks must be between 0 and 100.");
                }
            } else {
                System.out.println("Please enter a whole number.");
                sc.next();
            }
        }
    }

    public static int[] readMarks(Scanner sc) {
        int[] marks = new int[5];

        for (int i = 0; i < marks.length; i++) {
            marks[i] = readMark(sc, i + 1);
        }

        return marks;
    }

    public static Student findStudent(ArrayList<Student> students, int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student Marks");
            System.out.println("5. Delete Student");
            System.out.println("6. Display Class Topper");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Please enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter Student ID: ");
                int id = sc.nextInt();

                if (findStudent(students, id) != null) {
                    System.out.println("This ID already exists.");
                } else {
                    sc.nextLine();

                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String department = sc.nextLine();

                    int[] marks = readMarks(sc);
                    Student student = new Student(id, name, department, marks);

                    students.add(student);
                    System.out.println("Student added successfully.");
                }

            } else if (choice == 2) {
                if (students.isEmpty()) {
                    System.out.println("No students found.");
                } else {
                    for (Student student : students) {
                        student.displayDetails();
                        System.out.println("-----------------------------");
                    }
                }

            } else if (choice == 3) {
                System.out.print("Enter Student ID to search: ");
                int id = sc.nextInt();

                Student student = findStudent(students, id);

                if (student == null) {
                    System.out.println("Student not found.");
                } else {
                    student.displayDetails();
                }

            } else if (choice == 4) {
                System.out.print("Enter Student ID to update: ");
                int id = sc.nextInt();

                Student student = findStudent(students, id);

                if (student == null) {
                    System.out.println("Student not found.");
                } else {
                    System.out.println("Enter the new marks:");
                    int[] newMarks = readMarks(sc);
                    student.updateMarks(newMarks);
                    System.out.println("Marks updated successfully.");
                }

            } else if (choice == 5) {
                System.out.print("Enter Student ID to delete: ");
                int id = sc.nextInt();

                Student student = findStudent(students, id);

                if (student == null) {
                    System.out.println("Student not found.");
                } else {
                    students.remove(student);
                    System.out.println("Student deleted successfully.");
                }

            } else if (choice == 6) {
                if (students.isEmpty()) {
                    System.out.println("No students found.");
                } else {
                    Student topper = students.get(0);

                    for (Student student : students) {
                        if (student.calculateAverage() > topper.calculateAverage()) {
                            topper = student;
                        }
                    }

                    System.out.println("\n===== CLASS TOPPER =====");
                    topper.displayDetails();
                }

            } else if (choice == 7) {
                System.out.println("Exiting Student Management System.");

            } else {
                System.out.println("Invalid choice. Please select 1 to 7.");
            }

        } while (choice != 7);

        sc.close();
    }
}