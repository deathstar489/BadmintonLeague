package main;

import java.awt.event.ActionEvent;

public class Singles extends Match {

	protected Player first;
	protected Player second;

	public Singles(Player first, Player second) {
		this.first = first;
		this.second = second;
		count++;
		panel();
	}

	protected String getGame(String side) {
		if(side.equals("First"))
			return first.toString();
		else
			return second.toString();
	}

	protected void display() {
		System.out.println(first + "      vs      " + second);
		System.out.println();
		System.out.println("-------------------------------------");
	}

	protected void complete() {
		System.out.println("Who won? ('0' to cancel)");
		System.out.println();
		System.out.println("1. " + first + ".");
		System.out.println("2. " + second + ".");
		System.out.println("3. It was a tie.");

		int selected = Utility.validInt(0,3);

		if(selected != 0){
			if(selected == 3){ //ties
				ties();
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
		Pair pair = new Pair(first,second);
		pair.swap(player1, player2);
	}
	
	protected void tie() {
		first.tie();
		second.tie();
		setFinished(true);
	}
	
	private void finish(Player winner, Player loser) {
		winner.win();
		loser.lose();
		setFinished(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("First"))	finish(first, second);
		if(e.getActionCommand().equals("Tie"))		tie();
		if(e.getActionCommand().equals("Second"))	finish(second, first);

		frame.dispose();
	}
}
