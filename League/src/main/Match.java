package main;

import java.util.ArrayList;

public class Match {
	//Object Match, it creates a match
	private Pair one;
	private Pair two;
	private Player first;
	private Player second;
	private Mode mode;
	private boolean finished = false;
	
	public Match(Pair one, Pair two){
		this.one = one;
		this.two = two;
		this.mode = Mode.Doubles;
	}
		
	public Match(Player first, Player second){
		this.first = first;
		this.second = second;
		this.mode = Mode.Singles;
	}
	

	public void print(){

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
	
	public void complete(){
		
		if (mode == Mode.Doubles){
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
		}
		else{
			System.out.println("Who won? ('0' to cancel)");
			System.out.println();
			System.out.println("1. " + first + ".");
			System.out.println("2. " + second + ".");

			int selected = Utility.validInt(0,2);
			
			if(selected != 0){
				
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
	
	public void setFinished(boolean finished){
		this.finished = finished;
	}
	
	public boolean finished(){
		return finished;
	}
	
	
}
