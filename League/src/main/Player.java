package main;

public class Player {

	private final String first;
	private final String last;
	private int wins = 0;
	private int losses = 0;
	private int points;
	private static int count;
	
	
	public Player(String first, String last){
		this.first = first;
		this.last = last;
		count++;
	}
	
	public Player(String first){
		this(first, null);
	}

	public Player(String first, String last, int wins, int losses) {
		this(first, last);
		this.wins = wins;
		this.losses = losses;
	}
	
	public String getFirst(){
		return first;
	}
	
	public String getLast(){
		return last;
	}
	
	public void win(){
		wins++;
	}
	
	public void lose(){
		losses++;
	}
	
	public int getWins(){
		return wins;
	}
	
	public int getLosses(){
		return losses;
	}
	
	public void delete(){
		count--;
	}
	
	public void print(){
		System.out.println(this + "\t wins: " + wins + "\t losses: " + losses);
	}
	
	
	public static int getCount(){
		return count;
	}
	public boolean is(String first, String last){
		boolean same = false;
		if(this.first.equals(first) && this.last.equals(last))
			same = true;
		return same;
	}
	
	public String toString(){
		return first + " " + last;
	}
	
	

}
