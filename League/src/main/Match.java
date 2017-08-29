package main;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author Jiashu Wang
 *
 */
public abstract class Match extends JPanel implements ActionListener{//Object Match, it creates a match

	private Font font = new Font("Arial", Font.BOLD, 30);
	protected Window frame;
	
	private boolean finished = false;
	protected static int count = 0;
	
	/**
	 * Panel manager for GUI.
	 */
	protected void panel() {
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Prompt Label
		JLabel label = new JLabel("What were the results? (Who won?)", SwingConstants.CENTER);
		
		label.setFont(font);
		
		//Layout for label
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 3;
		gbc.gridy = 1;
		
		add(label, gbc);
		
		//Buttons
		
		JButton[] array = new JButton[] {
				new JButton("<html><center>" + getGame("First").replaceAll("\\n", "<br>") + "</center></html>"),
				new JButton("<html><center>Tie</center></html>"),
				new JButton("<html><center>" + getGame("Second").replaceAll("\\n", "<br>") + "</center></html>")
		};
		
		JButton hello = new JButton();
		//Action References
		array[0].setActionCommand("First");
		array[1].setActionCommand("Tie");
		array[2].setActionCommand("Second");
		
		//Layout for Buttons
		gbc.gridwidth = 1;
		gbc.gridy = 2;
		gbc.ipadx = 100;
		gbc.insets = new Insets(5,5,5,5);  //padding

		//For all buttons...
		for(JButton button: array) {
			button.setFont(font);
			button.addActionListener(this);
			
			add(button, gbc);
		}
		
		frame = new Window(this, count);
	}

	/**
	 * Takes the name of the Pair/Player selected.
	 * @param side Which Pair/player to get the name of.
	 * @return the Pair/Player selected, ready to print onto button.
	 */
	protected abstract String getGame(String side);
	
	/**
	 * Displays the Match.
	 */
	protected abstract void display();
	
	/**
	 * Completes a Match. (Submitting a score)
	 */
	protected abstract void complete();
	
	/**
	 * Swaps two Players.
	 * @param player1 The first player to swap.
	 * @param player2 The second player to swap.
	 */
	protected abstract void swap(Player player1, Player player2);

	protected abstract void tie();
	public abstract void actionPerformed(ActionEvent e);
	
	protected void ties() {

		if(Main.careful){
			System.out.println("You selected a tie.");
			System.out.println("Are you sure it was a tie?");
			System.out.println();					
			if(!Utility.confirm())
				tie();
			else
				complete();
		}
		else
			tie();
	}

	/**
	 * Setter for whether or not the match is finished.
	 * @param finished If the match is completed or not.
	 */
	protected void setFinished(boolean finished){
		this.finished = finished;
		Main.save();
	}
	
	/**
	 * Shows whether or not the match is finished.
	 * @return true if match is completed.
	 */
	public boolean finished(){
		return finished;
	}
	
}
