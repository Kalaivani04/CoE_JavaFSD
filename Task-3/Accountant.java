package com.practice.tm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class Accountant {
	
	   public static boolean login(String email, String password) {
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM accountant WHERE email=? AND password=?")) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return false;
    }

	    public static int addAccountant(String name, String email, String phone, String password) {
	        try (Connection conn = Jdbc.getConnection();
	             PreparedStatement ps = conn.prepareStatement("INSERT INTO accountant(name, email, phone, password) VALUES(?, ?, ?, ?)")) {

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);
	            ps.setString(4, password);

	            return ps.executeUpdate(); 
	        } catch (SQLException e) {
	            System.out.println("Error" + e.getMessage());
	        }
	        return 0; 
	    }


	    public static List<String> getAllAccountants() {
	        List<String> accountants = new ArrayList<>();
	        try (Connection conn = Jdbc.getConnection();
	             PreparedStatement ps = conn.prepareStatement("SELECT * FROM accountant");
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                accountants.add(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getString("email") + " | " + rs.getString("phone"));
	            }
	        } catch (SQLException e) {
	            System.out.println("Error " + e.getMessage());
	        }
	        return accountants;
	    }


	    public static int updateAccountant(int id, String name, String email, String phone, String password) {
	        try (Connection conn = Jdbc.getConnection();
	             PreparedStatement ps = conn.prepareStatement("UPDATE accountant SET name=?, email=?, phone=?, password=? WHERE id=?")) {

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);
	            ps.setString(4, password);
	            ps.setInt(5, id);

	            return ps.executeUpdate(); 
	        } catch (SQLException e) {
	            System.out.println("Error " + e.getMessage());
	        }
	        return 0; 
	    }

	    public static int deleteAccountant(int id) {
	        try (Connection conn = Jdbc.getConnection();
	             PreparedStatement ps = conn.prepareStatement("DELETE FROM accountant WHERE id=?")) {

	            ps.setInt(1, id);
	            return ps.executeUpdate(); 
	        } catch (SQLException e) {
	            System.out.println("Error" + e.getMessage());
	        }
	        return 0;
	    }
	}

