package com.practice;
import java.util.Scanner;
public class StringProcessor {

	public String reverseString(String str)
	{
		
		String reverse="";
		StringBuffer sb = new StringBuffer(str);
		reverse = sb.reverse().toString();
		return reverse;
	}
	public int countOccurrences(String text, String sub)
	{
		int count = 0;
		int index = 0;
		while ((index = text.indexOf(sub, index)) != -1) {
            count++;
            index += sub.length(); 
        }
        
        return count;
	}
	public String splitAndCapitalize(String str)
	{
		String[] words = str.split(" ");
		StringBuffer result = new StringBuffer();
		for(String word:words)
		{
			String capital_word = word.substring(0,1).toUpperCase()+word.substring(1);
			result.append(capital_word).append(" ");
		}
		return result.toString();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter String");
		String str = sc.nextLine();
		StringProcessor sp = new StringProcessor();
		System.out.println("Reverse of the string is: "+sp.reverseString(str));
		System.out.println("Enter text");
		String text = sc.nextLine();
		System.out.println("Enter sub");
		String sub = sc.nextLine();
		System.out.println("Occurence of sub in text is: "+sp.countOccurrences(text, sub));
		System.out.println("Split and Capitalize: "+sp.splitAndCapitalize(str));

	}

}
