package com.practice.tm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Student {

    public static void addStudent(String name, String email, String course, double fee, double paid, String address, String phone) {
        double due = fee - paid;
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO student(name, email, course, fee, paid, due, address, phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, course);
            ps.setDouble(4, fee);
            ps.setDouble(5, paid);
            ps.setDouble(6, due);
            ps.setString(7, address);
            ps.setString(8, phone);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public static List<String> getAllStudents() {
        List<String> students = new ArrayList<>();
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM student");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                students.add("ID: " + rs.getInt("id") + " | Name: " + rs.getString("name") +
                        " | Email: " + rs.getString("email") + " | Course: " + rs.getString("course") +
                        " | Fee: " + rs.getDouble("fee") + " | Paid: " + rs.getDouble("paid") +
                        " | Due: " + rs.getDouble("due"));
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return students;
    }

    public static void updateStudent(int id, String name, String email, String course, double fee, double paid, String address, String phone) {
        double due = fee - paid;
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE student SET name=?, email=?, course=?, fee=?, paid=?, due=?, address=?, phone=? WHERE id=?")) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, course);
            ps.setDouble(4, fee);
            ps.setDouble(5, paid);
            ps.setDouble(6, due);
            ps.setString(7, address);
            ps.setString(8, phone);
            ps.setInt(9, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public static void deleteStudent(int id) {
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM student WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public static List<String> getDueFeeStudents() {
        List<String> dueStudents = new ArrayList<>();
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM student WHERE due > 0");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dueStudents.add("ID: " + rs.getInt("id") + " | Name: " + rs.getString("name") +
                        " | Due Fee: " + rs.getDouble("due"));
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return dueStudents;
    }
}
