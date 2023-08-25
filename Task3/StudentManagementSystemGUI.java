import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Student implements Serializable {
    private int rollNo;
    private String name;
    private int age;
    private String branch;
    private String grade;

    public Student(int rollNo, String name, int age, String branch, String grade) {
        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
        this.branch = branch;
        this.grade = grade;
    }

    public int getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBranch() {
        return branch;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

public class StudentManagementSystemGUI {
    private List<Student> students = new ArrayList<>();
    private JFrame frame;
    private JTextArea displayArea;

    private int currentRollNo = 1;

    public StudentManagementSystemGUI() {
        frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        displayArea = new JTextArea(15, 40);
        displayArea.setEditable(false);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter student name:");
                String ageStr = JOptionPane.showInputDialog(frame, "Enter student age:");
                int age = Integer.parseInt(ageStr);
                String branch = JOptionPane.showInputDialog(frame, "Enter student branch:");
                String grade = JOptionPane.showInputDialog(frame, "Enter student grade:");

                Student student = new Student(currentRollNo, name, age, branch, grade);
                students.add(student);
                currentRollNo++;

                updateDisplay();
            }
        });

        JButton saveButton = new JButton("Save to File");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

        JButton loadButton = new JButton("Load from File");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });

        JButton searchButton = new JButton("Search by Name");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchName = JOptionPane.showInputDialog(frame, "Enter student name to search:");
                searchStudentByName(searchName);
            }
        });

        JButton editButton = new JButton("Edit Student");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String editName = JOptionPane.showInputDialog(frame, "Enter student name to edit:");
                editStudent(editName);
            }
        });

        JButton deleteButton = new JButton("Delete Student");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deleteName = JOptionPane.showInputDialog(frame, "Enter student name to delete:");
                deleteStudent(deleteName);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(new JScrollPane(displayArea), BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student List:\n");
        for (Student student : students) {
            sb.append("Roll No: ").append(student.getRollNo()).append(", Name: ").append(student.getName())
                    .append(", Age: ").append(student.getAge()).append(", Branch: ").append(student.getBranch())
                    .append(", Grade: ").append(student.getGrade()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void saveToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            outputStream.writeObject(students);
            JOptionPane.showMessageDialog(frame, "Data saved to file.");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error saving data to file.");
        }
    }

    private void loadFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("students.dat"))) {
            students = (List<Student>) inputStream.readObject();
            JOptionPane.showMessageDialog(frame, "Data loaded from file.");
            updateDisplay();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading data from file.");
        }
    }

    private void searchStudentByName(String searchName) {
        StringBuilder sb = new StringBuilder();
        sb.append("Search Results:\n");
        boolean found = false;
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(searchName)) {
                sb.append("Roll No: ").append(student.getRollNo()).append(", Name: ").append(student.getName())
                        .append(", Age: ").append(student.getAge()).append(", Branch: ").append(student.getBranch())
                        .append(", Grade: ").append(student.getGrade()).append("\n");
                found = true;
            }
        }
        if (!found) {
            sb.append("No student found with the name '").append(searchName).append("'");
        }
        displayArea.setText(sb.toString());
    }

    private void editStudent(String editName) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(editName)) {
                String newName = JOptionPane.showInputDialog(frame, "Enter new name:");
                String newAgeStr = JOptionPane.showInputDialog(frame, "Enter new age:");
                int newAge = Integer.parseInt(newAgeStr);
                String newBranch = JOptionPane.showInputDialog(frame, "Enter new branch:");
                String newGrade = JOptionPane.showInputDialog(frame, "Enter new grade:");

                student.setName(newName);
                student.setAge(newAge);
                student.setBranch(newBranch);
                student.setGrade(newGrade);

                updateDisplay();
                JOptionPane.showMessageDialog(frame, "Student information updated.");
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Student not found.");
    }

    private void deleteStudent(String deleteName) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getName().equalsIgnoreCase(deleteName)) {
                iterator.remove();
                updateDisplay();
                JOptionPane.showMessageDialog(frame, "Student deleted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Student not found.");
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StudentManagementSystemGUI system = new StudentManagementSystemGUI();
                system.show();
            }
        });
    }
}
