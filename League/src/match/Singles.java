package match;

import java.awt.event.ActionEvent;

import main.Main;
import main.Player;
import main.Utility;

@SuppressWarnings("serial")
public class Singles extends Match {

	protected Player first;
	protected Player second;

	public Singles(Player first, Player second) {
		this.first = first;
		this.second = second;
		type = MatchType.SINGLES;
		count++;
		if(Main.GUI) panel();
	}

	protected String getGame(String side) {
		if(side.equals("First"))
			return first.toString();
		else
			return second.toString();
	}

	public void display() {
		System.out.println(first + "      vs      " + second);
		System.out.println();
		System.out.println("-------------------------------------");
	}

	public void complete() {
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

	public void swap(Player player1, Player player2) {

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
		
		text();
		frame.pack();
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

		switch(e.getActionCommand()) {
		case "First":	finish(first, second);	break;
		case "Tie":		tie();					break;
		case "Second":	finish(second, first);	break;
		default:
		}
		
		frame.dispose();
	}
}
