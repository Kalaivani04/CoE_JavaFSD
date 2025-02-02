package com.practice;
import java.util.InputMismatchException;
import java.util.Scanner;
public class ExceptionHandling {
	
	public static void processInput()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number: ");
		try
		{
			int num=sc.nextInt();
			
				int reciprocal = 1/num;
				System.out.println("Reciprocal of the number is: "+reciprocal);
			
		}
		catch(ArithmeticException e)
		{
			System.out.println(e.toString());
		}
		catch (InputMismatchException e) 
		{
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		processInput();

	}

}
