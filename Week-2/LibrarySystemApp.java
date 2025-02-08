package com.practice.tm;

import java.io.*;
import java.util.*;

// Book class
class Book implements Serializable {
    private String title, author, ISBN;
    private boolean isBorrowed;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isBorrowed = false;
    }

    public String getTitle() { return title; }
    public String getISBN() { return ISBN; }
    public boolean isBorrowed() { return isBorrowed; }

    public synchronized void borrow() { this.isBorrowed = true; }
    public synchronized void returnBook() { this.isBorrowed = false; }

    @Override
    public String toString() { 
        return title + " by " + author + " (ISBN: " + ISBN + ") - " + (isBorrowed ? "Borrowed" : "Available");
    }
}

// User class
class User implements Serializable {
    private String name, userID;
    private List<Book> borrowedBooks = new ArrayList<>();

    public User(String name, String userID) { 
        this.name = name;
        this.userID = userID; 
    }

    public String getUserID() { return userID; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }

    public synchronized void borrowBook(Book book) { borrowedBooks.add(book); }
    public synchronized void returnBook(Book book) { borrowedBooks.remove(book); }

    @Override
    public String toString() { 
        return "User: " + name + " (ID: " + userID + ") - Borrowed Books: " + borrowedBooks.size();
    }
}

// Custom Exceptions
class BookNotFoundException extends Exception { 
    public BookNotFoundException(String msg) { super(msg); } 
}
class UserNotFoundException extends Exception { 
    public UserNotFoundException(String msg) { super(msg); } 
}
class MaxBooksAllowedException extends Exception { 
    public MaxBooksAllowedException(String msg) { super(msg); } 
}

// Library Interface
interface ILibrary {
    void borrowBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException;
    void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException;
    void displayLibraryState();
}

// Library Manager
class LibraryManager implements ILibrary {
    private static final int MAX_BORROWED_BOOKS = 3;
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    // Add books & users
    public void addBook(Book book) { books.add(book); }
    public void addUser(User user) { users.add(user); }

    // Search for a book by ISBN
    private Book getBookByISBN(String ISBN) throws BookNotFoundException {
        return books.stream()
                .filter(b -> b.getISBN().equals(ISBN))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found: " + ISBN));
    }

    // Search for a user by ID
    private User getUserByID(String userID) throws UserNotFoundException {
        return users.stream()
                .filter(u -> u.getUserID().equals(userID))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userID));
    }

    // Borrow a book
    public synchronized void borrowBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException {
        User user = getUserByID(userID);
        Book book = getBookByISBN(ISBN);

        if (user.getBorrowedBooks().size() >= MAX_BORROWED_BOOKS)
            throw new MaxBooksAllowedException("Max book limit reached for user: " + userID);

        if (!book.isBorrowed()) {
            book.borrow();
            user.borrowBook(book);
            logTransaction(userID + " borrowed " + book.getTitle());
            System.out.println(userID + " borrowed " + book.getTitle());
        } else {
            System.out.println("Book is already borrowed: " + book.getTitle());
        }
    }

    // Return a book
    public synchronized void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
        User user = getUserByID(userID);
        Book book = user.getBorrowedBooks().stream()
                .filter(b -> b.getISBN().equals(ISBN))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not borrowed by user: " + ISBN));

        book.returnBook();
        user.returnBook(book);
        logTransaction(userID + " returned " + book.getTitle());
        System.out.println(userID + " returned " + book.getTitle());
    }

    // Log transactions
    private void logTransaction(String message) {
        try (FileWriter writer = new FileWriter("library_log.txt", true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }

    // Display final state of library
    public void displayLibraryState() {
        System.out.println("\nFinal Library State:");
        books.forEach(System.out::println);
        users.forEach(System.out::println);
    }
}

// Main class
public class LibrarySystemApp {
    public static void main(String[] args) {
        LibraryManager libManager = new LibraryManager();

        // Initialize books & users
        libManager.addBook(new Book("Harry Potter", "J.K. Rowling", "123"));
        libManager.addBook(new Book("Verity", "Colleen Hoover", "456"));
        libManager.addBook(new Book("Java Basics", "James Gosling", "789"));
        
        libManager.addUser(new User("Alex", "U001"));
        libManager.addUser(new User("Rhys", "U002"));
        libManager.addUser(new User("Taylor", "U003"));

        // Multithreading simulation
        Thread user1 = new Thread(() -> {
            try { libManager.borrowBook("123", "U001"); } 
            catch (Exception e) { System.out.println(e.getMessage()); }
        });

        Thread user2 = new Thread(() -> {
            try { libManager.borrowBook("123", "U002"); } 
            catch (Exception e) { System.out.println(e.getMessage()); }
        });

        Thread user3 = new Thread(() -> {
            try { libManager.returnBook("123", "U001"); } 
            catch (Exception e) { System.out.println(e.getMessage()); }
        });

        user1.start();
        user2.start();
        
        try { user1.join(); user2.join(); } catch (InterruptedException e) { e.printStackTrace(); }
        
        user3.start();
        try { user3.join(); } catch (InterruptedException e) { e.printStackTrace(); }

        // Display final library state
        libManager.displayLibraryState();
    }
}
