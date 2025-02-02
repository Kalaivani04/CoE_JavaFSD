package com.practice;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class UserManager implements Serializable {
	private List<User> users = new ArrayList<>();
	public class User implements Serializable
	{
		String name;
		String email;
		User(String name,String email)
		{
			this.name = name;
			this.email = email;
		}
		public String getName()
		{
			return name;
		}
		public String getEmail()
		{
			return email;
		}
	}
	public void addUser(String name,String email)
	{
		User user = new User(name,email);
		users.add(user);
		System.out.println("User added");
	}
	public void saveUsersToFile(String filename)
	{
		try {
			FileOutputStream fout = new FileOutputStream(filename);
			ObjectOutputStream out=new ObjectOutputStream(fout);
			out.writeObject(users);
			out.flush();
			out.close();
			System.out.println("User saved");
		}
		catch(Exception e)
		{
			System.out.println("Error"+e.getMessage());
		}
	}
	public void readUsersFromFile(String filename)
	{
		try {
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(filename));
		users=(List<User>) ois.readObject();
		for (User user : users) {
            System.out.println("Name: " + user.getName() + ", Email: " + user.getEmail());
        }
		}
		catch(Exception e)
		{
			System.out.println("Error"+e.getMessage());
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		UserManager userManager = new UserManager();
		System.out.println("Enter name: ");
		String name = sc.nextLine();
		System.out.println("Enter email: ");
		String email = sc.nextLine();
		userManager.addUser(name,email);
		String filename = "users1.txt";
		userManager.saveUsersToFile(filename);
		userManager.readUsersFromFile(filename);
	}

}
