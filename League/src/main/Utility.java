package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utility {

	public static Scanner input = new Scanner(System.in);
	
	public static int inputInt(){  //INTEGER ERRORS	
			
		boolean error = true;
		int choice = -1;
		while (error == true)
		{
			error = false;
			try
			{
				System.out.println("\n\n");
				choice = input.nextInt();
			}
			catch(Exception e)
			{
				input.nextLine();
				error = true;
				System.out.println("Sorry, That Wasn't A Valid Entry. Please Enter An Integer.");
			}
		}
		return choice;
	}
	public static int validInt(int min, int max){  //VALID INTEGER

		int number = 0;
		boolean valid = false;
		do
		{
			number = inputInt();
			if (min <= number && number <= max)
			{
				valid = true;
			}
			else
			{
				System.out.println("Invalid Input, Please Enter A Value Between " + min + " and " + max + ":");
			}

		}while(valid == false);

		return number;
	
	}
	
	public static boolean confirm(){
		
		System.out.println("1. Yes");
		System.out.println("2. No");
		
		int choice = validInt(1,2);
		
		boolean confirm;
		switch(choice){
			case 1: confirm = true; break;
			default: confirm = false;
		}
		return confirm;
		
	}
	
	
	public static ArrayList<String> read(String fileName){
		ArrayList <String> content = new ArrayList<String>();
		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader("src/main/" + fileName);
			br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				content.add(line);
			}

		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				
				if (br != null) br.close();
				if (fr != null)	fr.close();
				
			} catch (IOException ex) {
				
				ex.printStackTrace();
			}
		}
		return content;
	}
	
	public static void write(String content, String fileName, Boolean append){

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter("src/main/" + fileName, append);
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.flush();
			
			System.out.println("Done");

		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {

			try {

				if (bw != null)	bw.close();
				if (fw != null)	fw.close();

			} catch (IOException ex) {
				
				ex.printStackTrace();
			}
		}
	}
	
	
	
	
}
