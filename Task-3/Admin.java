package com.practice.tm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
	    
	    public static boolean login(String username, String password) {
	        try (Connection conn = Jdbc.getConnection();
	             PreparedStatement ps = conn.prepareStatement("SELECT * FROM admin WHERE username=? AND password=?")) {
	            
	            ps.setString(1, username);
	            ps.setString(2, password);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                return rs.next(); 
	            }
	        } catch (SQLException e) {
	            System.out.println("Login error: " + e.getMessage());
	        }
	        return false; 
	    }
	}
