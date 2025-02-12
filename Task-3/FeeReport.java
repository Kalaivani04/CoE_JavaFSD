package com.practice.tm;
import java.util.List;
import java.util.Scanner;

public class FeeReport {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Fee Report System");
            System.out.println("1. Admin Login\n2. Accountant Login\n3. Exit");
            System.out.print("Enter choice: ");
            int userType = sc.nextInt();
            sc.nextLine();

            if (userType == 1) {
                System.out.print("Enter Admin Username: ");
                String username = sc.nextLine();
                System.out.print("Enter Password: ");
                String password = sc.nextLine();

                if (Admin.login(username, password)) {
                    System.out.println("Admin Login Successful!");
                    boolean exit = false;

                    while (!exit) {
                        System.out.println("\n1. Add Accountant\n2. View Accountants\n3. Update Accountant\n4. Delete Accountant\n5. Logout");
                        System.out.print("Enter choice: ");
                        int choice = sc.nextInt();
                        sc.nextLine();

                        switch (choice) {
                            case 1:
                                System.out.print("Enter Name: ");
                                String name = sc.nextLine();
                                System.out.print("Enter Email: ");
                                String email = sc.nextLine();
                                System.out.print("Enter Phone: ");
                                String phone = sc.nextLine();
                                System.out.print("Enter Password: ");
                                String accPassword = sc.nextLine();

                                Accountant.addAccountant(name, email, phone, accPassword);
                                System.out.println("Accountant Added!");
                                break;

                            case 2:
                                List<String> accountants = Accountant.getAllAccountants();
                                System.out.println("\nAccountants List:");
                                for (String acc : accountants) {
                                    System.out.println(acc);
                                }
                                break;

                            case 3:
                                System.out.print("Enter Accountant ID to Update: ");
                                int updateId = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Enter New Name: ");
                                String newName = sc.nextLine();
                                System.out.print("Enter New Email: ");
                                String newEmail = sc.nextLine();
                                System.out.print("Enter New Phone: ");
                                String newPhone = sc.nextLine();
                                System.out.print("Enter New Password: ");
                                String newPassword = sc.nextLine();

                                Accountant.updateAccountant(updateId, newName, newEmail, newPhone, newPassword);
                                System.out.println("Accountant Updated!");
                                break;

                            case 4:
                                System.out.print("Enter Accountant ID to Delete: ");
                                int deleteId = sc.nextInt();
                                sc.nextLine();

                                Accountant.deleteAccountant(deleteId);
                                System.out.println("Accountant Deleted!");
                                break;

                            case 5:
                                System.out.println("Logging out...");
                                exit = true;
                                break;

                            default:
                                System.out.println("Invalid Choice!");
                        }
                    }
                } else {
                    System.out.println("Invalid Admin Credentials! Try Again.");
                }
            } else if (userType == 2) {
                System.out.print("Enter Accountant Email: ");
                String email = sc.nextLine();
                System.out.print("Enter Password: ");
                String password = sc.nextLine();

                if (Accountant.login(email, password)) {
                    System.out.println("Accountant Login Successful!");
                    boolean exit = false;

                    while (!exit) {
                        System.out.println("\n1. Add Student\n2. View Students\n3. Update Student\n4. Delete Student\n5. Check Due Fee\n6. Logout");
                        System.out.print("Enter choice: ");
                        int choice = sc.nextInt();
                        sc.nextLine();

                        switch (choice) {
                            case 1:
                                System.out.print("Enter Name: ");
                                String name = sc.nextLine();
                                System.out.print("Enter Email: ");
                                String studentEmail = sc.nextLine();
                                System.out.print("Enter Course: ");
                                String course = sc.nextLine();
                                System.out.print("Enter Total Fee: ");
                                double fee = sc.nextDouble();
                                System.out.print("Enter Paid Amount: ");
                                double paid = sc.nextDouble();
                                sc.nextLine();
                                System.out.print("Enter Address: ");
                                String address = sc.nextLine();
                                System.out.print("Enter Phone: ");
                                String phone = sc.nextLine();

                                Student.addStudent(name, studentEmail, course, fee, paid, address, phone);
                                System.out.println("Student Added!");
                                break;

                            case 2:
                                List<String> students = Student.getAllStudents();
                                System.out.println("\nStudent Records:");
                                for (String stu : students) {
                                    System.out.println(stu);
                                }
                                break;

                            case 3:
                                System.out.print("Enter Student ID to Update: ");
                                int updateId = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Enter New Name: ");
                                String newName = sc.nextLine();
                                System.out.print("Enter New Email: ");
                                String newEmail = sc.nextLine();
                                System.out.print("Enter New Course: ");
                                String newCourse = sc.nextLine();
                                System.out.print("Enter New Total Fee: ");
                                double newFee = sc.nextDouble();
                                System.out.print("Enter New Paid Amount: ");
                                double newPaid = sc.nextDouble();
                                sc.nextLine();
                                System.out.print("Enter New Address: ");
                                String newAddress = sc.nextLine();
                                System.out.print("Enter New Phone: ");
                                String newPhone = sc.nextLine();

                                Student.updateStudent(updateId, newName, newEmail, newCourse, newFee, newPaid, newAddress, newPhone);
                                System.out.println("Student Updated!");
                                break;

                            case 4:
                                System.out.print("Enter Student ID to Delete: ");
                                int deleteId = sc.nextInt();
                                sc.nextLine();

                                Student.deleteStudent(deleteId);
                                System.out.println("Student Deleted!");
                                break;

                            case 5:
                                List<String> dueStudents = Student.getDueFeeStudents();
                                System.out.println("\nStudents with Due Fee:");
                                for (String stu : dueStudents) {
                                    System.out.println(stu);
                                }
                                break;

                            case 6:
                                System.out.println("Logging out!");
                                exit = true;
                                break;

                            default:
                                System.out.println("Invalid Choice!");
                        }
                    }
                } else {
                    System.out.println("Invalid");
                }
            } else if (userType == 3) {
                System.out.println("Exiting!");
                break;
            } else {
                System.out.println("Invalid Choice!");
            }
        }
        sc.close();
    }
}
