package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Player> pool = new ArrayList<Player>();
	private static ArrayList<Player> out = new ArrayList<Player>();
	private static ArrayList<Player> sidelines = new ArrayList<Player>();
	
	private static ArrayList<Match> matches = new ArrayList<Match>();
	private static ArrayList<Pair> pairs = new ArrayList<Pair>();
	private static ArrayList<String> master = new ArrayList<String>();
	
	private static Scanner input = Utility.input;
	
	private static int rounds = 0;
	private static int games;
	private static int extras;
	public static boolean careful = false;
	
	//Read from files and create players
	private static void begin() {
		
		ArrayList<String> playing = Utility.read("Playing");
		master = Utility.read("Master");
		
		for(String name: playing){
			String[] split = name.split("\t");
			String first = split[0];
			String last = split[1];
			
			find(first, last);
		}
	}
	
	//Each round, distribute players
	private static void round(){

		rounds++;

		int numPlayers = Player.getCount();
		games  = numPlayers / 4;
		extras = numPlayers % 4;
		
		System.out.println("Round " + rounds + ": ");
		System.out.println();
		
		populate(Type.Pool);
		
		//Find extra players
		extras();
		
		//Create Matches
		matches();
		
		//Display Matches
		display();
		
	}
	
	//Extra players
	private static void extras(){
		
		//Choose Extra People
		for (int extra = 0; extra < extras; extra++) {
			
			Player player = pick(Type.Out);
			sidelines.add(player);
			pool.remove(player);
			
			//everyone's sat out
			if (out.isEmpty()) {
				populate(Type.Out);
				for (Player side : sidelines){
					out.remove(side);
				}
			}
		}
	}
	
	//Matches
	private static void matches(){
		for (int game = 1; game < games + 1; game++) {

			boolean unique = false;
			int errors = 0;
			do{
				if(errors > 100){
					pairs.clear();
				}

				unique = true;

				//Pick four people
				Player first = pick(Type.Pool);
				Player second = pick(Type.Pool);
				Player third = pick(Type.Pool);
				Player fourth = pick(Type.Pool);

				//Create Pairs
				Pair one = new Pair(first, second);
				Pair two = new Pair(third, fourth);

				for(Pair pair:pairs){
					if ((one.equals(pair) || two.equals(pair)) && unique){
						unique = false;
						pool.add(first);
						pool.add(second);
						pool.add(third);
						pool.add(fourth);
						errors++;
					}
				}

				if(unique){
					pairs.add(one);
					pairs.add(two);
					Match match = new Match(one,two);
					matches.add(match);
				}

			}while(!unique);
		}
	}
	
	//Randomly pick from type
	private static Player pick(Type type) {

		Random rand = new Random();

		int num;
		Player picked = null;
		if (type == Type.Pool) {
			num = rand.nextInt(pool.size());
			picked = pool.get(num);
			pool.remove(picked);
		} else if (type == Type.Out) {
			num = rand.nextInt(out.size());
			picked = out.get(num);
			out.remove(picked);
		}

		return picked;
	}

	//Repopulate when empty
	private static void populate(Type type) {
		for (Player player : players) {
			if (type == Type.Pool)
				pool.add(player);
			else if (type == Type.Out)
				out.add(player);
		}
	}

	//Next round
	private static void next() {
		boolean finished = true;
		
		for(Match match: matches)
			if (!match.finished())
				finished = false;
		
		if(finished){
			if(!matches.isEmpty())
				matches.clear();
			round();
		}
		else{

			System.out.println("The following matches have not been completed:");
			System.out.println();
			System.out.println("-------------------------------------");
			for(Match match: matches){
				if (!match.finished()){
					System.out.println("Match " + (matches.indexOf(match)+1) + ":");
					System.out.println();
					match.print();
				}
			}
		}
	}
	
	private static void view() {
		System.out.println();
		for(Player player: players){
			player.print();
		}
		System.out.println();
	}
	
	private static void add() {
		
		System.out.print("First Name:");
		String first = input.next();
		
		System.out.print("Last Name:");
		String last = input.next();
		
		boolean play = false;
		for(Player player: players)
			if(player.is(first, last)){
				System.out.println(player + " is already playing.");
				play = true;
			}
		
		if(!play){
			find(first,last);
			
		}
	}
	
	private static void find(String first, String last){

		boolean found = false;
		for(String line: master){
			String[] split1 = line.split("\t");
			String first1 = split1[0];
			String last1 = split1[1];				
			
			if (first.equals(first1) && last.equals(last1)){
				found = true;
				int wins = Integer.parseInt(split1[2]);
				int losses = Integer.parseInt(split1[3]);
				players.add(new Player(first, last, wins, losses));
			}
			
			
		}
		if(!found){
			add(first,last);
			players.add(new Player(first, last));
		}
	}
	private static void add(String first, String last) {
		String line = first + "\t" + last + "\t0\t0";
		master.add(line);
	}
	

	private static void remove() {
		
		System.out.print("First Name:");
		String first = input.next();
		
		System.out.print("Last Name:");
		String last = input.next();
		
		Player delete = null;
		for(Player player:players)
			if(player.is(first, last)){
				replace(player);
				delete = player;
			}
		delete.delete();
		players.remove(delete);
	}

	private static void replace(Player player) {
		
		for(String line: master){
			String[] split = line.split("\t");
			String first = split[0];
			String last = split[1];
			if (player.is(first, last)){
				int wins = player.getWins();
				int losses = player.getLosses();
				String newLine = first + "\t" + last + "\t" + wins + "\t" + losses;
				master.set(master.indexOf(line), newLine);
			}
		}
	}
	
	private static void display() {

		//Display Extra People
		if (!sidelines.isEmpty()) {
			System.out.println("-------------------------------------");
			System.out.println("The following people sit out:");
			System.out.println();
			for (Player player: sidelines) {
				System.out.println(player);
			}
			System.out.println();
		}
		
		System.out.println("-------------------------------------");

				
		for(Match match:matches){
			int num = matches.indexOf(match)+1;
			System.out.println("Match " + num + ":");
			System.out.println();
			match.print();
		}
	}
	
	private static void swap() {
			
		System.out.println("First person to switch out:");
		System.out.println();
		System.out.print("First Name:");
		String first1 = input.next();
		
		System.out.print("Last Name:");
		String last1 = input.next();
		
		System.out.println("Person to switch with?");
		System.out.println();
		System.out.print("First Name:");
		String first2 = input.next();
		
		System.out.print("Last Name:");
		String last2 = input.next();
		
		Player player1 = null;
		Player player2 = null;
		
		
		for(Player player: players){
			if(player.is(first1, last1))
				player1 = player;
			if(player.is(first2, last2))
				player2 = player;
		}
		for(Match match: matches){
			match.swap(player1, player2);
		}
		for(Player player: sidelines){
			if(player.equals(player1)){
				player = player2;
			}
			else if(player.equals(player2)){
				player = player1;
			}
		}
	}
	
	private static void score() {
		// TODO get input on who wins
		
		if(matches.size() == 0)
			System.out.println("All matches are complete.");
		else{
			System.out.println("What was your match number? ('0' to go back.)");

			int matchNum = Utility.validInt(0, matches.size());

			if(matchNum != 0){
				Match match = matches.get(matchNum-1);
				if(match.finished()){
					System.out.println("This match is already complete");
					score();
				}
				System.out.println("-------------------------------------");
				System.out.println();
				match.print();

				if(careful){
					System.out.println("Is this your match?");
					System.out.println();

					if(Utility.confirm())
						match.complete();
					else
						score();
				}
				else
					match.complete();

			}
			else{
				//go back to main menu
			}
		}

	}
	
	private static void end() {
		for(Player player: players){
			replace(player);
		}
		
		Utility.write("", "Master" , false);
		for(String line: master){
			System.out.println(line);
			Utility.write(line + "\n", "Master" , true);
		}
	}
	
	private static void menu(){
		System.out.println("What would you like to do?");
		System.out.println();
		System.out.println("0. Start next round");
		System.out.println("1. View players");
		System.out.println("2. Add a player");
		System.out.println("3. Remove a player");
		System.out.println("4. Swap two players");
		if(!matches.isEmpty()){
			System.out.println("5. Submit a score");
			System.out.println("6. View matches");
		}
		System.out.println("9. Save to Master");
	}
	
	public static void main(String[] args) throws FileNotFoundException {

		begin();
		populate(Type.Out);
		input = new Scanner(System.in);
		
		while(true){

			menu();

			int selection = Utility.validInt(0,9);

			switch(selection){
				case 0:	next();		break;
				case 1:	view();		break;
				case 2: add();		break;
				case 3: remove();	break;
				case 4: swap();		break;
				case 5: score();	break;
				case 6: display();	break;
				case 9: end();		break;
			}
			System.out.println("\n\n");
		}
	}
	
	
}
