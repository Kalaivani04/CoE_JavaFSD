package com.practice;
import java.util.PriorityQueue;
import java.util.Comparator;
public class TaskManager {
	class Task implements Comparable<Task>
	{
		String id;
		String description;
		int priority;
		
		public Task(String id,String description, int priority)
		{
			this.id = id;
			this.description = description;
			this.priority = priority;
		    }
		 public int compareTo(Task other) 
		 {
			 return Integer.compare(other.priority, this.priority); 
			 }
		 public String toString() {
	            return "ID: " + id + ", Description: " + description + " (Priority: " + priority + ")";
	        }
	}
	public PriorityQueue<Task> tasks = new PriorityQueue<>();
	
	public void addTask(String id,String description,int priority)
	{
		Task newTask = new Task(id, description, priority);
        tasks.add(newTask);
        System.out.println("Added: " + newTask);
	}
	public void removeTask(String id)
	{
		tasks.removeIf(task -> task.id.equals(id));
        System.out.println("Removed task with ID: " + id);
	}
	public void getHighestPriority()
	{
		Task highestPriorityTask = tasks.peek();
		System.out.println("Highest Priority Task: " + highestPriorityTask);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TaskManager tm = new TaskManager();
		tm.addTask("1","Task-A",3);
		tm.addTask("2","Task-B",1);
		tm.addTask("3","Task-C",2);
		tm.getHighestPriority();
		tm.removeTask("2");
		tm.getHighestPriority();

	}

}
