package main;

import java.util.ArrayList;

public class Scores {

	private static ArrayList<Player> players = new ArrayList<Player>();
	private static int count = 0;
	
	private static String fileName = "Master";
	
	/**
	 * Creates a Player for every unique name in Master.
	 */
	private static void creation(){
		
		ArrayList<String> master = Utility.read(fileName);
		for(String line: master){
			String[] split = line.split("\t");
			String first = split[0];
			String last = split[1];
			
			int wins = Integer.parseInt(split[2]);
			int losses = Integer.parseInt(split[3]);
			int ties = Integer.parseInt(split[4]);
			Player player1 = new Player(first, last, wins, losses, ties);
			
			boolean found = false;
			for(Player player : players) {
				if (player.is(player1)) {
					found = true;
					player.combine(player1);
				}
			}
			if(!found) {
				players.add(player1);
				count++;
			}
		}
	}
	
	/**
	 * Displays all of the Players with their stats.
	 */
	private static void display() {
		System.out.println();
		System.out.println("Number of Players: " + count);
		for(Player player: players){
			player.stats();
		}
		System.out.println();
	}
	
//	/**
//	 * Displays all of the Players with their points.
//	 */
//	private static void viewPoints() {
//		System.out.println();
//		System.out.println("Number of Players: " + Player.getCount());
//		for(Player player: players){
//			player.points();
//		}
//		System.out.println();
//	}
	
	private static void sort(Sort type) {
		
		
		boolean sorted = false;
		while(!sorted) {
			sorted = true;
			
			for(int x = 0; x < count-1; x++) {
				int y = x+1;
				
				Player one = players.get(x);
				Player two = players.get(y);
				
				if (   (type == Sort.WINS	&& (one.getWins() < two.getWins()))
					|| (type == Sort.LOSSES	&& (one.getLosses() < two.getLosses()))
					|| (type == Sort.TIES	&& (one.getTies() < two.getTies()))
					|| (type == Sort.POINTS	&& (one.getPoints() < two.getPoints()))
					|| (type == Sort.GAMES	&& (one.getGames() < two.getGames()))
					|| (type == Sort.ALPHA	&& (0 < one.toString().compareToIgnoreCase(two.toString())))) {
					
					sorted = false;
					players.set(x, two);
					players.set(y, one);
				}
			}
		}
		
		display();
	}
	
	/**
	 * Uploads results to "Master" file.
	 */
	private static void save() {
		ArrayList<String> updated = new ArrayList<String>();
		for(Player player: players){
			String line = player.toLine();
			updated.add(line);
		}
		
		Utility.write("", "Updated" , false);
		for(String line: updated){
			//System.out.println(line);
			Utility.write(line + "\n", "Updated" , true);
		}
	}
	
	/**
	 * Main menu.
	 */
	private static void menu(){
		System.out.println("What would you like to do?");
		System.out.println();
		System.out.println("0. Go back to main");
		System.out.println("1. View stats");
		System.out.println("2. Sort by wins");
		System.out.println("3. Sort by losses");
		System.out.println("4. Sort by ties");
		System.out.println("5. Sort by points");
		System.out.println("6. Sort by games");
		System.out.println("7. Sort by alphabet");
		System.out.println("9. Save to " + fileName);
	}
	
	/**
	 * Reads from "Playing" and create players.
	 */
	public static void main() {
		
		creation();
		
		boolean running = true;
		while(running){

			menu();
			int selection = Utility.validInt(0,9);

			switch(selection){
				case 0: running = false;	break;
				case 1: display();			break;
				case 2: sort(Sort.WINS);	break;
				case 3: sort(Sort.LOSSES);	break;
				case 4: sort(Sort.TIES);	break;
				case 5: sort(Sort.POINTS);	break;
				case 6: sort(Sort.GAMES);	break;
				case 7: sort(Sort.ALPHA);	break;
				case 9: save();				break;
				default: System.out.println("Invalid Entry.");   
			}
			System.out.println("\n\n");
		}
		
		//reset
		for (Player player : players) {
			player.delete();
		}
		players.removeAll(players);
		count = 0;
	}
}
