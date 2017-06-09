package main;

import java.util.ArrayList;

public class Match {

	private Pair one;
	private Pair two;
	private boolean finished = false;
	
	public Match(Pair one, Pair two){
		this.one = one;
		this.two = two;
	}
	
	public void print(){
		System.out.printf("%-20s %-20s\n", one.getFirst(), two.getFirst());
		System.out.println("     &        vs          &");
		System.out.printf("%-20s %-20s\n", one.getSecond(), two.getSecond());
		System.out.println();
		System.out.println("-------------------------------------");
	}
	
	public void complete(){
		
		System.out.println("Which team won? ('0' to cancel)");
		System.out.println();
		System.out.println("1. " + one + ".");
		System.out.println("2. " + two + ".");

		int selected = Utility.validInt(0,2);
		
		if(selected != 0){
			
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
		else{
			
		}
		
		
		
	}
	
	public ArrayList<Player> getPlayers(){
		ArrayList<Player> matchUp = new ArrayList<Player>(); 
		
		matchUp.add(one.getFirst());
		matchUp.add(one.getSecond());
		matchUp.add(two.getFirst());
		matchUp.add(two.getSecond());
		
		return matchUp;
	}
	
	public void swap(Player player1, Player player2){
		
		one.swap(player1, player2);
		two.swap(player1, player2);
	}
	
	
	
	public void setFinished(boolean finished){
		this.finished = finished;
	}
	
	public boolean finished(){
		return finished;
	}
	
	
}
