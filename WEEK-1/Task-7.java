package com.practice;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class Anagram {
	public static List<Integer> findAnagram(String s,String p)
	{
		List<Integer> result = new ArrayList<>();
		if(s.length()<p.length())
		{
			return result;
		}
		int[] sCount = new int[26];
		int[] pCount = new int[26];
		
		for(int i=0;i<p.length();i++)
		{
			sCount[s.charAt(i)-'a']++;
			pCount[p.charAt(i)-'a']++;
			
			if(isAnagram(pCount,sCount))
			{
				result.add(0);
			}
		}
		//Sliding Window 
		for(int i=p.length();i<s.length();i++)
		{
			sCount[s.charAt(i)-'a']++;
			sCount[s.charAt(i-p.length())-'a']--;
			
			if(isAnagram(pCount,sCount))
			{
				result.add(i-p.length()+1);
			}
		}
		return result;
	}
		
		public static boolean isAnagram(int[] pCount,int[] sCount)
		{
			for(int i=0;i<26;i++)
			{
				if(pCount[i]!=sCount[i])
				{
					return false;
				}
			}
			return true;
		}
			

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter String 1");
		String s = sc.nextLine();
		System.out.println("Enter String 2");
		String p = sc.nextLine();
		System.out.println(findAnagram(s,p));
	}

}
