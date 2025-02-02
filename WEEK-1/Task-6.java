package com.practice;
import java.util.HashMap;
import java.util.Map;
public class LinkedList {
	static class Node
	{
		 int data;
	     Node next;
	     
	     Node(int data) 
	     {
	    	 this.data = data;
	    	 this.next = null;
	        }
	}
    public static boolean hasCycle(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;  
            }
        }

        return false;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList list = new LinkedList();
		Node head = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);
        Node fourth = new Node(4);
        Node fifth = new Node(5);

        head.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = fifth;
        fifth.next = third;
        System.out.println("Cycle detected: " + list.hasCycle(head));

	}

}
