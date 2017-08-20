package main;

/**
 * @author SmashCity
 *
 */
public class Match {//Object Match, it creates a match
	
	//Doubles
	private Pair one;
	private Pair two;
	
	//Singles
	private Player first;
	private Player second;
	
	private Mode mode;
	
	
	private boolean finished = false;
	
	/**
	 * Constructor for a doubles match.
	 * @param one First pair playing in this match.
	 * @param two Second pair playing in this match.
	 */
	public Match(Pair one, Pair two){
		this.one = one;
		this.two = two;
		this.mode = Mode.Doubles;
	}
	
	/**
	 * Constructor for a singles match.
	 * @param one First player playing in this match.
	 * @param two Second player playing in this match.
	 */
	public Match(Player first, Player second){
		this.first = first;
		this.second = second;
		this.mode = Mode.Singles;
	}
	
	/**
	 * Displays the match.
	 */
	public void display(){
		if (mode == Mode.Doubles){
			System.out.printf("%-20s %-20s\n", one.getFirst(), two.getFirst());
			System.out.println("     &        vs          &");
			System.out.printf("%-20s %-20s\n", one.getSecond(), two.getSecond());
		}
		else{
			System.out.println(first + "      vs      " + second);
		}
		System.out.println();
		System.out.println("-------------------------------------");
	}
	
	/**
	 * Completes a match. (Submitting a score)
	 */
	public void complete(){
		
		if (mode == Mode.Doubles){
			System.out.println("Which team won? ('0' to cancel)");
			System.out.println();
			System.out.println("1. " + one + ".");
			System.out.println("2. " + two + ".");
			System.out.println("3. It was a tie.");

			int selected = Utility.validInt(0,3);
			
			if(selected != 0){
				
				if(selected == 3){ //ties
					if(Main.careful){
						System.out.println("You selected a tie.");
						System.out.println("Are you sure it was a tie?");
						System.out.println();					
						if(Utility.confirm()){
							one.tie();
							two.tie();
							setFinished(true);
						}
						else{
							complete();
						}
					}
					else{
						one.tie();
						two.tie();
						setFinished(true);
					}
				}
				else{
					Pair winner;
					Pair loser;

					if(selected == 1){
						winner = one;
						loser = two;
					}
					else{
						winner = two;
						loser = one;
					}

					if(Main.careful){
						System.out.println("You selected " + winner + ".");
						System.out.println("Are you sure they won?");
						System.out.println();					
						if(Utility.confirm()){
							winner.win();
							loser.lose();
							setFinished(true);
						}
						else{
							complete();
						}
					}
					else{
						winner.win();
						loser.lose();
						setFinished(true);
					}
				}
			}
		}
		else{
			System.out.println("Who won? ('0' to cancel)");
			System.out.println();
			System.out.println("1. " + first + ".");
			System.out.println("2. " + second + ".");

			int selected = Utility.validInt(0,3);
			
			if(selected != 0){
				if(selected == 3){ //ties
					if(Main.careful){
						System.out.println("You selected a tie.");
						System.out.println("Are you sure it was a tie?");
						System.out.println();					
						if(Utility.confirm()){
							one.tie();
							two.tie();
							setFinished(true);
						}
						else{
							complete();
						}
					}
					else{
						one.tie();
						two.tie();
						setFinished(true);
					}
				}
				else{
					Player winner;
					Player loser;

					if(selected == 1){
						winner = first;
						loser = second;
					}
					else{
						winner = second;
						loser = first;
					}
					if(Main.careful){
						System.out.println("You selected " + winner + ".");
						System.out.println("Are you sure they won?");
						System.out.println();					
						if(Utility.confirm()){
							winner.win();
							loser.lose();
							setFinished(true);
						}
						else{
							complete();
						}
					}
					else{
						winner.win();
						loser.lose();
						setFinished(true);
					}
				}
			}
		}			
		
	}
	
	/**
	 * Swaps two Players.
	 * @param player1 The first player to swap.
	 * @param player2 The second player to swap.
	 */
	public void swap(Player player1, Player player2){
		
		if(mode == Mode.Doubles){
			one.swap(player1, player2);
			two.swap(player1, player2);
		}
		else{
			if(first.equals(player1)){
				first = player2;
			}
			else if(first.equals(player2)){
				first = player1;
			}
			else if(second.equals(player1)){
				second = player2;
			}
			else if(second.equals(player2)){
				second = player1;
			}
		}
	}
	
	
	/**
	 * Setter for whether or not the match is finished.
	 * @param finished If the match is completed or not.
	 */
	public void setFinished(boolean finished){
		this.finished = finished;
	}
	
	/**
	 * Shows whether or not the match is finished.
	 * @return true if match is completed.
	 */
	public boolean finished(){
		return finished;
	}
	
	
}
