import java.util.*;

public class Main {
    static class VirtualClassroomManager {
        private final Map<String, Classroom> classrooms = new HashMap<>();
        
        public void addClassroom(String name) {
            if (classrooms.containsKey(name)) {
                System.out.println("Classroom " + name + " already exists.");
            } else {
                classrooms.put(name, new Classroom(name));
                System.out.println("Classroom " + name + " has been created.");
            }
        }

        public void addStudent(String studentId, String className) {
            Classroom classroom = classrooms.get(className);
            if (classroom == null) {
                System.out.println("Classroom " + className + " does not exist.");
                return;
            }
            classroom.addStudent(studentId);
            System.out.println("Student " + studentId + " has been enrolled in " + className + ".");
        }

        public void scheduleAssignment(String className, String assignmentDetails) {
            Classroom classroom = classrooms.get(className);
            if (classroom == null) {
                System.out.println("Classroom " + className + " does not exist.");
                return;
            }
            classroom.scheduleAssignment(assignmentDetails);
            System.out.println("Assignment for " + className + " has been scheduled.");
        }

        public void submitAssignment(String studentId, String className, String assignmentDetails) {
            Classroom classroom = classrooms.get(className);
            if (classroom == null) {
                System.out.println("Classroom " + className + " does not exist.");
                return;
            }
            classroom.submitAssignment(studentId, assignmentDetails);
            System.out.println("Assignment submitted by Student " + studentId + " in " + className + ".");
        }
    }

    static class Classroom {
        private final String name;
        private final Set<String> students = new HashSet<>();
        private final List<String> assignments = new ArrayList<>();
        private final Map<String, Set<String>> submissions = new HashMap<>();

        public Classroom(String name) {
            this.name = name;
        }

        public void addStudent(String studentId) {
            students.add(studentId);
            submissions.putIfAbsent(studentId, new HashSet<>());
        }

        public void scheduleAssignment(String assignmentDetails) {
            assignments.add(assignmentDetails);
        }

        public void submitAssignment(String studentId, String assignmentDetails) {
            if (!students.contains(studentId)) {
                System.out.println("Student " + studentId + " is not enrolled in this class.");
                return;
            }
            submissions.get(studentId).add(assignmentDetails);
        }
    }

    public static void main(String[] args) {
        VirtualClassroomManager manager = new VirtualClassroomManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command:");
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 3);
            String command = parts[0];

            try {
                switch (command) {
                    case "add_classroom":
                        if (parts.length < 2) throw new IllegalArgumentException("Class name is required.");
                        manager.addClassroom(parts[1]);
                        break;
                    case "add_student":
                        if (parts.length < 3) throw new IllegalArgumentException("Student ID and class name are required.");
                        String[] studentParts = parts[1].split(" ", 2);
                        manager.addStudent(studentParts[0], studentParts[1]);
                        break;
                    case "schedule_assignment":
                        if (parts.length < 3) throw new IllegalArgumentException("Class name and assignment details are required.");
                        manager.scheduleAssignment(parts[1], parts[2]);
                        break;
                    case "submit_assignment":
                        if (parts.length < 3) throw new IllegalArgumentException("Student ID, class name, and assignment details are required.");
                        String[] submitParts = parts[1].split(" ", 2);
                        manager.submitAssignment(submitParts[0], submitParts[1], parts[2]);
                        break;
                    default:
                        System.out.println("Unknown command: " + command);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

