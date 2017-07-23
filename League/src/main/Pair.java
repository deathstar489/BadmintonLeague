package main;

public class Pair {
	//Creates a Pair
	private Player first;
	private Player second;
	
	public Pair(Player first, Player second){
		this.first = first;
		this.second = second;
	}
	
	public boolean equals(Pair pair){
		
		boolean equal = false;
		if((first.equals(pair.getFirst()) && second.equals(pair.getSecond()))
				|| (first.equals(pair.getSecond()) && second.equals(pair.getFirst())))
			equal = true;
		
		return equal;
	}
	
	public void win(){
		System.out.println("Congratulations to " + this + "!");
		first.win();
		second.win();
	}
	
	public void lose(){
		first.lose();
		second.lose();
	}
	
	
	public Player getFirst(){
		return first;
	}
	
	public Player getSecond(){
		return second;
	}

	public String toString() {
		return first + " and " + second;
	}
	
	public void swap(Player player1, Player player2){
		
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

	public void tie() {
		first.tie();
		second.tie();
	}

}
