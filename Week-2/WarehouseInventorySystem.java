package com.practice.tm;
import java.util.*;
import java.util.concurrent.*;

class Location {
    private int aisle, shelf, bin;

    public Location(int aisle, int shelf, int bin) {
        this.aisle = aisle;
        this.shelf = shelf;
        this.bin = bin;
    }

    @Override
    public String toString() {
        return "Aisle " + aisle + ", Shelf " + shelf + ", Bin " + bin;
    }
}

class Product {
    private String productID;
    private String name;
    private int quantity;
    private Location location;

    public Product(String productID, String name, int quantity, Location location) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public String getProductID() { return productID; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public Location getLocation() { return location; }

    public void adjustQuantity(int change) { this.quantity += change; }

    @Override
    public String toString() {
        return name + " (ID: " + productID + "), Qty: " + quantity + ", Location: " + location;
    }
}

class Order implements Comparable<Order> {
    private String orderID;
    private List<String> productIDs;
    private Priority priority;

    public enum Priority { STANDARD, EXPEDITED }

    public Order(String orderID, List<String> productIDs, Priority priority) {
        this.orderID = orderID;
        this.productIDs = productIDs;
        this.priority = priority;
    }

    public String getOrderID() { return orderID; }
    public List<String> getProductIDs() { return productIDs; }
    public Priority getPriority() { return priority; }

    @Override
    public int compareTo(Order o) {
        return this.priority.compareTo(o.priority);
    }

    @Override
    public String toString() {
        return "Order " + orderID + " (" + priority + ")";
    }
}

class InventoryManager {
    private Map<String, Product> products = new ConcurrentHashMap<>();
    private PriorityQueue<Order> orderQueue = new PriorityQueue<>(Collections.reverseOrder());

    public void addProduct(Product product) {
        products.put(product.getProductID(), product);
    }

    public synchronized void processOrder(Order order) throws Exception {
        System.out.println("Processing " + order);
        for (String productID : order.getProductIDs()) {
            if (!products.containsKey(productID)) throw new Exception("Product not found: " + productID);

            Product product = products.get(productID);
            if (product.getQuantity() <= 0) throw new Exception("Out of stock: " + product.getName());

            product.adjustQuantity(-1);
            System.out.println("Picked: " + product.getName() + " from " + product.getLocation());
        }
    }

    public void queueOrder(Order order) {
        orderQueue.add(order);
    }

    public void processOrders() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            executor.execute(() -> {
                try {
                    processOrder(order);
                } catch (Exception e) {
                    System.out.println("Order Failed: " + e.getMessage());
                }
            });
        }
        executor.shutdown();
    }

    public void printInventory() {
        products.values().forEach(System.out::println);
    }
}

public class WarehouseInventorySystem {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();

        inventoryManager.addProduct(new Product("P1001", "Laptop", 5, new Location(1, 2, 3)));
        inventoryManager.addProduct(new Product("P1002", "Phone", 10, new Location(2, 3, 4)));
        inventoryManager.addProduct(new Product("P1003", "Tablet", 3, new Location(3, 1, 2)));

        Order order1 = new Order("O001", Arrays.asList("P1001", "P1002"), Order.Priority.EXPEDITED);
        Order order2 = new Order("O002", Arrays.asList("P1003", "P1001"), Order.Priority.STANDARD);
        Order order3 = new Order("O003", Arrays.asList("P1002", "P1003"), Order.Priority.EXPEDITED);

        inventoryManager.queueOrder(order1);
        inventoryManager.queueOrder(order2);
        inventoryManager.queueOrder(order3);

        inventoryManager.processOrders();

        System.out.println("\nFinal Inventory State:");
        inventoryManager.printInventory();
    }
}
