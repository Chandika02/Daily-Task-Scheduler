package demo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TaskScheduler {

    private ArrayList<Task> tasks = new ArrayList<>();
    private JTextArea taskDisplayArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskScheduler::new);
    }

    public TaskScheduler() {
        JFrame frame = new JFrame("Task Scheduler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        taskDisplayArea = new JTextArea();
        taskDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taskDisplayArea);
        
        JButton addButton = new JButton("Add Task");
        JButton deleteButton = new JButton("Delete Task");
        JButton completeButton = new JButton("Complete Task");
        JButton viewButton = new JButton("View All Tasks");

        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());
        completeButton.addActionListener(e -> completeTask());
        viewButton.addActionListener(e -> displayTasks());

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(completeButton);
        panel.add(viewButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void addTask() {
        String description = JOptionPane.showInputDialog("Enter task description:");
        if (description == null || description.isEmpty()) return;
        
        String dueTime = JOptionPane.showInputDialog("Enter due time (e.g., 14:00):");
        if (dueTime == null || dueTime.isEmpty()) return;

        tasks.add(new Task(description, dueTime));
        displayTasks();
    }

    private void deleteTask() {
        String taskIdStr = JOptionPane.showInputDialog("Enter task number to delete:");
        if (taskIdStr == null || taskIdStr.isEmpty()) return;

        int taskId;
        try {
            taskId = Integer.parseInt(taskIdStr) - 1;
            tasks.remove(taskId);
            displayTasks();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid task number!");
        }
    }

    private void completeTask() {
        String taskIdStr = JOptionPane.showInputDialog("Enter task number to mark as completed:");
        if (taskIdStr == null || taskIdStr.isEmpty()) return;

        int taskId;
        try {
            taskId = Integer.parseInt(taskIdStr) - 1;
            tasks.get(taskId).setCompleted(true);
            displayTasks();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid task number!");
        }
    }

    private void displayTasks() {
        StringBuilder taskDisplay = new StringBuilder("Today's Tasks:\n\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            taskDisplay.append((i + 1) + ". " + task + "\n");
        }
        taskDisplayArea.setText(taskDisplay.toString());
    }

    static class Task {
        private String description;
        private String dueTime;
        private boolean completed;

        public Task(String description, String dueTime) {
            this.description = description;
            this.dueTime = dueTime;
            this.completed = false;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            return description + " (Due: " + dueTime + ")" + (completed ? " [Completed]" : "");
        }
    }
}
