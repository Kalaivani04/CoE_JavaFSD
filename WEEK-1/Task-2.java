package com.practice.tm;
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " deposited " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Deposit amount must be greater than zero.");
        }
    }

    public synchronized void withdraw(double amount) {
        if (amount > 0) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ". New balance: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " attempted to withdraw " + amount + ", but insufficient funds.");
            }
        } else {
            System.out.println("Withdrawal amount must be greater than zero.");
        }
    }

    public double getBalance() {
        return balance;
    }
}

class BankUser implements Runnable {
    private BankAccount account;

    public BankUser(BankAccount account) {
        this.account = account;
    }

    @Override
    public void run() {
        account.deposit(100);
        account.withdraw(50);
        account.withdraw(70);
    }
}

public class Bank { 
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(500);

        BankUser user1 = new BankUser(account);
        BankUser user2 = new BankUser(account);

        Thread thread1 = new Thread(user1, "User 1");
        Thread thread2 = new Thread(user2, "User 2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final balance: " + account.getBalance());
    }
}
