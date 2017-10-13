package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import gui.Panel;
import gui.PanelType;
import match.Doubles;
import match.Match;
import match.Pair;
import match.Singles;


/**
 * @author Jiashu Wang
 *
 */
public class Main {

	//Change the file name, depending on which league is playing.
	public final static String FILE_NAME = "Saturday";
	public static boolean careful = false;
	public static boolean GUI = true;

	public static boolean running = true;
	
	public static ArrayList<Player> players = new ArrayList<Player>(); //all players playing today
	private static ArrayList<Player> pool = new ArrayList<Player>(); //all players who are not sitting out
	private static ArrayList<Player> out = new ArrayList<Player>(); //all players who haven't been out
	public static ArrayList<Player> extras = new ArrayList<Player>(); //all players who are out that current round
	private static ArrayList<Pair> pairs = new ArrayList<Pair>(); //array of pairs (teams) so nobody gets same team again
	public static ArrayList<String> master = new ArrayList<String>(); //array of strings of every line in Master (everyone in the league)
	
	public static ArrayList<Match> matches = new ArrayList<Match>(); //array of matches
	
	private static Scanner input = Utility.input;

	private static boolean single;

	private static int numGames;
	private static int numExtra;

	public static Panel playersPanel;
	public static Panel masterPanel;
	
	private static void help() {

		System.out.println("HELP WITH USING THE PROGRAM:");
		System.out.println("\t1. First, enter the first and last names of the people that are present into the \"Playing\" file. (Separated by a tab)");
		System.out.println("\t\t a. If you can't find the file open, look to the left and under BadmintonLeague_League > src > main. (It's near the bottom)");
		System.out.println("\t2. Then you run the program, which you should know cause you're reading this right now.");
		System.out.println("\t3. Simply type 0 to start the first round and go play.");
		System.out.println("\t4. Once you're done, come back and type 6 to submit the scores.");
		System.out.println("\t5. Once everyone is done playing, you can go back to step 3. and continue.");
		System.out.println("\t6. Once you are tired and you're done playing for the day, Type 9, then Type 8.");
		System.out.println("\t\t a. If you messed up and didn't save... Well... That's really too bad cause those games no longer count :P");
		System.out.println("\t\t b. Just kidding, they're probably already saved, so don't worry! XD");
		System.out.println();
		System.out.println();
		System.out.println("Before you start, make sure to pull:");
		System.out.println("\t1. Go to the left and right click the main folder. (BadmintonLeague)");
		System.out.println("\t2. Look through the options and click \"Team\". (It's near the bottom ish)");
		System.out.println("\t3. Look through the options and click \"Pull\". (It's near the top ish)");
		System.out.println();
		System.out.println();
		System.out.println("After all games are done, make sure to push:"); 
		System.out.println("\t1. Go to the left and right click the main folder. (BadmintonLeague)");
		System.out.println("\t2. Look through the options and click \"Team\". (It's near the bottom ish)");
		System.out.println("\t3. Look through the options and click \"Commit...\". (It's the first one)");
		System.out.println("\t\t a. Or you can simply press:   Ctrl + #");
		System.out.println("\t4. Something should pop up on the bottom, and there should be a few files under \"Unstaged Changes\". (Top Left)");
		System.out.println("\t\t If there isn't... Then you didn't save or something went wrong. Please contact tech support.");
		System.out.println("\t5. a. Select and Drag all of those files down to \"Staged Changes\". (Bottom Left)");
		System.out.println("\t6. Now under \"Commit Message\", Enter in any other relavent information that you believe to be important. (Top Right)");
		System.out.println("\t\t a. If you have nothing to write, then simply put the date.");
		System.out.println("\t7. Now click \"Commit and Push...\". Wait for something to pop up, and hit \"OK\".");
		System.out.println("\t\t a. If nothing pops up... then you clicked \"Commit\" instead, then you are a BAKA!");
		System.out.println("\t\t b. Follow Steps 1 and 2, then click \"Push to UpStream\". (It's near the top ish)");
		System.out.println();
		System.out.println();
		System.out.println("Anyways... If you have any questions, comments, or concerns,"
				+ " please feel free to contact tech support at (587)889-8369 or jiashuwang459@gmail.com! :D");
		System.out.println("\n\n");
	}

	/**
	 * Reads from "Playing" and create players.
	 */
	private static void begin() {

		ArrayList<String> playing = Utility.read("Playing");
		master = Utility.read(FILE_NAME);

		for(String name: playing){
			String[] split = name.split("\t");
			String first = split[0];
			String last = split[1];

			load(first, last);
		}
	}

	/**
	 * Starts the next round if all games are finished.
	 */
	public static void next() {
		boolean finished = true;

		for(Match match: matches)
			if (!match.finished()) {
				finished = false;
				match.frame.setVisible(true);
			} 

		if(finished){
			if(!matches.isEmpty())	matches.clear();
			start();
		}
		else{
			System.out.println("The following matches have not been completed:");
			System.out.println();
			System.out.println("-------------------------------------");
			for(Match match: matches){
				if (!match.finished()){
					System.out.println("Match " + (matches.indexOf(match)+1) + ":");
					System.out.println();
					match.display();
				}
			}
			
		}
	}


	/**
	 * General distribution of players for sitting out or playing.
	 */
	private static void start(){

		//Get total number of players
		int numPlayers = Player.getCount();
		
		numGames  = numPlayers / 4;
		numExtra = numPlayers % 4;
		
		//Populates the pool of players
		populate(ArrayType.POOL);
		extras.clear();

		//Distributes players into sitting out or playing
		extras();
		matches();
		
		if(GUI) playersPanel.updateText();
		
		display();
	}

	/**
	 * Determines the players who will sit out or play singles.
	 */
	private static void extras(){

		//If there will be a singles match
		if (numExtra >= 2)	single = true;
		else				single = false;

		//Choose the people who are sitting out or playing singles.
		for (int extra = 0; extra < numExtra; extra++) {
			Player player = pick(ArrayType.OUT);
			extras.add(player);
			pool.remove(player);

			//Once everyone sat out, repopulate the Out array.
			if (out.isEmpty()) {
				populate(ArrayType.OUT);
				for (Player side : extras)	out.remove(side); 
			}
		}
	}


	/**
	 * Distributes the players into matches.
	 */
	private static void matches(){
		Match.count = 0;
		for (int game = 1; game < numGames + 1; game++) {
			boolean unique;
			int errors = 0;
			do{
				unique = true;
				//Clears repeat array of pairs if too many tries
				if(errors > 100){
					pairs.clear();
				}
				
				//Pick four people
				Player a = pick(ArrayType.POOL);
				Player b = pick(ArrayType.POOL);
				Player c = pick(ArrayType.POOL);
				Player d = pick(ArrayType.POOL);

				//Create Pairs
				Pair one = new Pair(a, b);
				Pair two = new Pair(c, d);

				//Checks if it's a unique pair
				for(Pair pair: pairs){
					if ((one.equals(pair) || two.equals(pair)) && unique){
						unique = false;
						errors++;
						
						//adds them back to the pool
						pool.add(a);
						pool.add(b);
						pool.add(c);
						pool.add(d);
					}
				}

				//Adds the unique pairs into the array so it doesn't repeat
				if(unique){
					pairs.add(one);
					pairs.add(two);
					
					Doubles match = new Doubles(one,two);
					matches.add(match);
				}

			}while(!unique);
		}
		//Singles game
		if (single)	singles();
	}

	/**
	 * Creates a singles game.
	 */
	private static void singles(){

		//Pick players from extra
		Player first = pick(ArrayType.EXTRA);
		Player second = pick(ArrayType.EXTRA);

		//Creates match
		Singles match = new Singles(first,second);
		matches.add(match);
	}

	/**
	 * Randomly chooses a Player from an array.
	 * @param type The type of array to choose the Player from.
	 * @return The Player picked from the array.
	 */
	private static Player pick(ArrayType type) {

		Random rand = new Random();

		ArrayList<Player> array;
		switch(type) {
		case OUT: array = out;		break;
		case POOL: array = pool;	break;
		case EXTRA: array = extras;	break;
		default: array = null;
		}

		//Pick a random Player and remove.
		int num = rand.nextInt(array.size());
		Player picked = array.get(num);
		array.remove(picked);

		return picked;
	}

	/**
	 * Populates the arrays with all of the players playing.
	 * @param type The type of array to populate
	 */
	private static void populate(ArrayType type) {
		for (Player player : players) {
			switch(type) {
			case POOL:	pool.add(player); break;
			case OUT:	out.add(player);
			case EXTRA:
			default:
			}
		}
	}

	/**
	 * Displays all of the Players currently playing.
	 */
	private static void viewPlayers() {
		System.out.println();
		System.out.println("Number of Players: " + Player.getCount());
		for(Player player: players)
			System.out.println(player);
		System.out.println();
	}

	/**
	 * Add a player.
	 */
	private static void add() {

		System.out.println("Player to add:");
		System.out.println();
		System.out.print("First Name:");
		String first = input.next();

		System.out.print("Last Name:");
		String last = input.next();

		add(first, last);
	}

	public static void add(String first, String last) {
		Player player = find(first, last);
		if(player != null)
			System.out.println(player + " is already playing.");
		else {
			boolean confirm = true;
			if(careful) {
				System.out.println("Are you sure you want to add " + first + " " + last + "?");
				System.out.println();

				if(!Utility.confirm()) {
					confirm = false;
					System.out.println("ADD has been canceled.");
				}
			}
			if(confirm) {
				load(first,last);
				System.out.println(first + " " + last + " has joined the game!");
				if(GUI) playersPanel.updateText();
				if(GUI) masterPanel.updateMaster();
			}
		}
	}
	
	/**
	 * Loads Player in the "Master" file and adds it to Player.
	 * @param first The first name of the Player.
	 * @param last The last name of the Player.
	 */
	private static void load(String first, String last){

		boolean loaded = false;
		for(String line: master){
			String[] split1 = line.split("\t");
			String first1 = split1[0];
			String last1 = split1[1];				

			if (first.equals(first1) && last.equals(last1)){
				loaded = true;
				int wins = Integer.parseInt(split1[2]);
				int losses = Integer.parseInt(split1[3]);
				int ties = Integer.parseInt(split1[4]);
				players.add(new Player(first, last, wins, losses, ties));
			}
		}
		if(!loaded){
			Player player = new Player(first, last);

			master.add(player.toLine());
			Utility.write(player.toLine() + "\n", FILE_NAME , true);
			players.add(player);
		}
	}

	/**
	 * Removes a player.
	 */
	private static void remove() {

		System.out.println("Player to remove:");
		System.out.println();
		System.out.print("First Name:");
		String first = input.next();

		System.out.print("Last Name:");
		String last = input.next();
		
		remove(first, last);
	}

	/**
	 * Removes a player by name.
	 * @param first The first name of player to remove.
	 * @param last The last name of player to remove.
	 */
	public static void remove(String first, String last) {
		Player player = find(first, last);
		if(player == null) {
			System.out.println("The player you have specified does not exist. (" + first + " " + last + ")");
		}
		else {
			boolean confirm = true;
			if(careful){
				System.out.println("Are you sure you want to remove " + player + "?");
				System.out.println();

				if(!Utility.confirm()) {
					confirm = false;
					System.out.println("REMOVE has been canceled.");
				}
			}
			if(confirm){
				save();
				System.out.println(player + " has been removed from play!");
				player.delete();
				players.remove(player);
				out.remove(player);
				if(GUI) playersPanel.updateText();
			}
		}
	}
	
	/**
	 * Updates the Player from "Master". (Takes old player and replaces it with updated version)
	 * @param player The player to update.
	 */
	private static void update(Player player) {

		for(String line: master){
			String[] split = line.split("\t");
			String first = split[0];
			String last = split[1];
			if (player.is(first, last)){
				master.set(master.indexOf(line), player.toLine());
			}
		}
	}

	/**
	 * Displays the matches and extras.
	 */
	private static void display() {

		//Display Extra People
		if (!extras.isEmpty()) {
			System.out.println("-------------------------------------");
			System.out.println("The following people sit out:");
			System.out.println();
			for (Player player: extras)
				System.out.println(player);
			System.out.println();
		}

		System.out.println("-------------------------------------");

		for(Match match:matches){
			int num = matches.indexOf(match)+1;
			System.out.println("Match " + num + ":");
			System.out.println();
			match.display();
		}
	}

	/**
	 * Swaps two Players.
	 */
	private static void swap() {

		System.out.println("PLEASE DON'T SWITCH PEOPLE ON THE SAME TEAM!!!!");
		System.out.println("First person to switch:");
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
		
		swap(first1, last1, first2, last2);
	}
	
	public static void swap(String first1, String last1, String first2, String last2) {

		Player player1 = find(first1, last1);
		Player player2 = find(first2, last2);

		if(player1 == null || player2 == null){
			System.out.println("One or more of the players you entered does not exist. Swapping is canceled.");
		}
		else if(player1.equals(player2)) {
			System.out.println("You're selecting the same person. Swapping is canceled.");
		}
		else {
			boolean confirm = true;
			if(careful){
				System.out.println("Are you sure you want to swap " + player1 + " and " + player2 + "?");
				System.out.println();

				if(!Utility.confirm()) {
					confirm = false;
					System.out.println("SWAP has been canceled.");
				}
			}
			if(confirm) {
				for(Match match: matches)
					match.swap(player1, player2);
				//Adds other player to the array and removes old player if old player is sitting out.
				for(Player player: extras){
					if(player.equals(player1)){
						extras.add(player2);
						extras.remove(player1);
					}
					else if(player.equals(player2)){
						extras.add(player1);
						extras.remove(player2);
					}
				}
				System.out.println(player1 + " and " + player2 + " have been swapped.");
			}
		}
		if(GUI) playersPanel.updateText();
	}
	
	/**
	 * Submitting a score.
	 */
	private static void score() {

		boolean completed = true;
		for(Match match: matches)
			if (!match.finished())
				completed = false;
		if(completed)
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
				else{
					System.out.println("-------------------------------------");
					System.out.println();
					match.display();

					boolean confirm = true;
					if(careful){
						System.out.println("Are you sure this is your match?");
						System.out.println();

						if(!Utility.confirm()) {
							confirm = false;
							System.out.println("SCORE ENTRY has been canceled.");
						}
					}
					if(confirm)
						match.complete();
				}
			}
		}
	}

	/**
	 * Finds a Player in the array "players" by name.
	 * @param first The first name of the person to be found.
	 * @param last The last name of the person to be found.
	 * @return The player if found. Otherwise null.
	 */
	private static Player find(String first, String last) {
		Player found = null;
		for(Player player: players)
			if(player.is(first, last))
				found = player;
		return found;
	}

	/**
	 * Uploads results to "Master" file.
	 */
	public static void save() {
		for(Player player: players){
			update(player);
		}

		Utility.write("", FILE_NAME , false);
		for(String line: master){
			//System.out.println(line);
			Utility.write(line + "\n", FILE_NAME , true);
		}
	}


	/**
	 * Main menu.
	 */
	private static void menu(){
		System.out.println("What would you like to do?");
		System.out.println();
		System.out.println("0. Start next round");
		System.out.println("1. View players");
		System.out.println("2. Add a player");
		System.out.println("3. Remove a player");
		System.out.println("4. Swap two players");
		//System.out.println("5. Create Pairs");
		//System.out.println("6. Remove Pairs");
		if(!matches.isEmpty()){
			System.out.println("5. Submit a score");
			System.out.println("6. View matches");
		}
		System.out.println("7. Help Menu. (If you have nothing else to do)");
		System.out.println("8. End Game");
		System.out.println("9. Save to " + FILE_NAME);
	}

	/**
	 * Where all the magic happens! :D
	 * @param args No particular use...
	 */
	public static void main(String[] args) {
		begin();
		populate(ArrayType.OUT);
		
		if(GUI) {
			new Panel(PanelType.MAIN);
			playersPanel = new Panel(PanelType.VIEW);
			masterPanel = new Panel(PanelType.MASTER);
		}
		else {
			help();
			while(running){
	
				menu();
				int selection = Utility.validInt(0, 10);
	
				switch(selection){
				case 0:	next();				break;
				case 1:	viewPlayers();		break;
				case 2: add();				break;
				case 3: remove();			break;
				case 4: swap();				break;
				case 5: score();			break;
				case 6: display();			break;
				case 7: help();				break;
				case 8: running = false;	break;
				case 9: save();				break;
				case 10: Scores.main();
				master = Utility.read(FILE_NAME);	break;
				default: System.out.println("Invalid Entry");
				}
				System.out.println("\n");
			}
		}
	}
}