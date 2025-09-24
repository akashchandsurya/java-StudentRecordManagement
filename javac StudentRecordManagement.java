import java.util.ArrayList;
import java.util.Scanner;

public class StudentRecordManagement {

    // Inner Student class (could be separate file)
    static class Student {
        private int id;
        private String name;
        private double marks;

        public Student(int id, String name, double marks) {
            this.id = id;
            this.name = name;
            this.marks = marks;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getMarks() { return marks; }

        public void setName(String name) { this.name = name; }
        public void setMarks(double marks) { this.marks = marks; }

        @Override
        public String toString() {
            return String.format("ID: %d | Name: %s | Marks: %.2f", id, name, marks);
        }
    }

    private ArrayList<Student> students = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private int nextId = 1;

    public static void main(String[] args) {
        StudentRecordManagement app = new StudentRecordManagement();
        app.run();
    }

    private void run() {
        int choice = -1;
        System.out.println("Student Record Management System ");
        do {
            printMenu();
            String line = sc.nextLine().trim();
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for choice.");
                continue;
            }

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewAllStudents(); break;
                case 3: viewStudentById(); break;
                case 4: updateStudent(); break;
                case 5: deleteStudent(); break;
                case 6: System.out.println("Exiting.."); break;
                default: System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 6);
        sc.close();
    }

    private void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add student");
        System.out.println("2. View all students");
        System.out.println("3. View student by ID");
        System.out.println("4. Update student by ID");
        System.out.println("5. Delete student by ID");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty. Aborting add.");
            return;
        }

        Double marks = readDouble("Enter marks (0-100): ");
        if (marks == null) return;

        Student s = new Student(nextId++, name, marks);
        students.add(s);
        System.out.println("Student added: " + s);
    }

    private void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to show.");
            return;
        }
        System.out.println("\nAll Students:");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private void viewStudentById() {
        Integer id = readInt("Enter student ID to view: ");
        if (id == null) return;
        Student s = findById(id);
        if (s == null) System.out.println("Student with ID " + id + " not found.");
        else System.out.println("Found: " + s);
    }

    private void updateStudent() {
        Integer id = readInt("Enter student ID to update: ");
        if (id == null) return;
        Student s = findById(id);
        if (s == null) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }

        System.out.println("Current record: " + s);
        System.out.print("Update name? (yes/no): ");
        String yn = sc.nextLine().trim();
        if (yn.equalsIgnoreCase("y")) {
            System.out.print("Enter new name: ");
            String newName = sc.nextLine().trim();
            if (!newName.isEmpty()) s.setName(newName);
            else System.out.println("Name empty, keeping old name.");
        }

        System.out.print("Update marks? (y/n): ");
        yn = sc.nextLine().trim();
        if (yn.equalsIgnoreCase("y")) {
            Double newMarks = readDouble("Enter new marks (0-100): ");
            if (newMarks != null) s.setMarks(newMarks);
        }

        System.out.println("Record updated: " + s);
    }

    private void deleteStudent() {
        Integer id = readInt("Enter student ID to delete: ");
        if (id == null) return;
        Student s = findById(id);
        if (s == null) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }

        System.out.println("Found: " + s);
        System.out.print("Are you sure you want to delete? (yes/no): ");
        String yn = sc.nextLine().trim();
        if (yn.equalsIgnoreCase("y")) {
            students.remove(s);
            System.out.println("Student deleted.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private Student findById(int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    // Helper to read integer
    private Integer readInt(String prompt) {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Operation cancelled.");
            return null;
        }
    }

    // Helper to read double
    private Double readDouble(String prompt) {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        try {
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Operation cancelled.");
            return null;
        }
    }
}
