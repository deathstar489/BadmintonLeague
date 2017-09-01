package main;

import java.awt.event.ActionEvent;

public class Doubles extends Match {

	protected Pair one;
	protected Pair two;

	public Doubles(Pair one, Pair two) {
		this.one = one;
		this.two = two;	
		count++;
		if(Main.GUI) panel();
	}

	/* (non-Javadoc)
	 * @see main.Match#getGame(java.lang.String)
	 */
	protected String getGame(String side) {
		if(side.equals("First"))
			return one.toLabel();
		else
			return two.toLabel();
	}

	/* (non-Javadoc)
	 * @see main.Match#display()
	 */
	protected void display() {
		System.out.printf("%-20s %-20s\n", one.getFirst(), two.getFirst());
		System.out.println("     &        vs          &");
		System.out.printf("%-20s %-20s\n", one.getSecond(), two.getSecond());
		System.out.println();
		System.out.println("-------------------------------------");
	}


	/* (non-Javadoc)
	 * @see main.Match#complete()
	 */
	protected void complete() {
		System.out.println("Who won? ('0' to cancel)");
		System.out.println();
		System.out.println("1. " + one + ".");
		System.out.println("2. " + two + ".");
		System.out.println("3. It was a tie.");

		int selected = Utility.validInt(0,3);

		if(selected != 0){
			if(selected == 3){ //ties
				ties();
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
						finish(winner, loser);
					}
					else{
						complete();
					}
				}
				else{
					finish(winner,loser);
				}
			}
		}
	}
	
	protected void swap(Player player1, Player player2) {
		one.swap(player1, player2);
		two.swap(player1, player2);
		text();
	}
	
	protected void tie() {
		one.tie();
		two.tie();
		setFinished(true);
	}

	private void finish(Pair winner, Pair loser) {
		winner.win();
		loser.lose();
		setFinished(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("First"))	finish(one, two);
		if(e.getActionCommand().equals("Tie"))		tie();
		if(e.getActionCommand().equals("Second"))	finish(two, one);
		
		frame.dispose();
	}
	
	
}