package main;

/**
 * @author Jiashu Wang
 *
 */
public class Player {
	//Creates a Player
	private final String first;
	private final String last;
	private int wins = 0;
	private int losses = 0;
	private int ties = 0;
	private int points = 0;
	private int games = 0;
	private static int count = 0;
	
	public Player(String first, String last){
		this.first = first;
		this.last = last;
		count++;
	}
	
	public Player(String first){
		this(first, null);
	}

	public Player(String first, String last, int wins, int losses, int ties) {
		this(first, last);
		this.wins = wins;
		this.losses = losses;
		this.ties = ties;
		setPoints();
		setGames();
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
	
	public void tie(){
		ties++;
	}
	
	public int getWins(){
		return wins;
	}
	
	public int getLosses(){
		return losses;
	}
	
	public int getTies(){
		return ties;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getGames() {
		return games;
	}
	public void delete(){
		count--;
	}
	
	public static int getCount(){
		return count;
	}
	
	private void setPoints() {
		points  = 3*wins;
		points += 2*ties;
		points += 1*losses;
	}
	
	private void setGames() {
		games  = wins;
		games += ties;
		games += losses;
	}
	
	public void combine(Player player) {
		wins += player.getWins();
		losses += player.getLosses();
		ties += player.getTies();
		setPoints();
		setGames();
		count--;
	}
	
	public boolean is(String first, String last){
		boolean same = false;
		if(getFirst().equals(first) && getLast().equals(last))
			same = true;
		return same;
	}
	
	public boolean is(Player player){
		boolean same = false;
		if(getFirst().equals(player.getFirst()) && getLast().equals(player.getLast()))
			same = true;
		return same;
	}

	public void stats(){
		System.out.printf("%-22s %-15s\n", first + "     \t" + last, "\t points: " + points + "\t wins: " + wins + "\t losses: " + losses + "\t ties: " + ties + "\t games: " + games);
	}
	
	public String toLine() {
		return first + "\t" + last + "\t" + wins + "\t" + losses + "\t" + ties;
	}
	
	public String reverse() {
		return last + " " + first;
	}
	
	public String toString(){
		return first + " " + last;
	}

}