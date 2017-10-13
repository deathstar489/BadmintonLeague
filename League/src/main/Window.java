package main;

import java.awt.GridBagLayout;

import javax.swing.JFrame;

import gui.Panel;
import match.Match;

/**
 * @author Jiashu Wang
 *
 */
@SuppressWarnings("serial")
public class Window extends JFrame{

	/**
	 * JFrame for matches.
	 * @param match The match to display.
	 */
	public Window(Match match) {

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new GridBagLayout());
		setVisible(true);
		setTitle("Match " + Match.count);
		this.setResizable(false);
		add(match);
		pack();
		
		//center
		setLocationRelativeTo(null);
	}
	
	/**
	 * JFrame for the rest of the program.
	 * @param main The main program.
	 */
	public Window(Panel panel) {
		
		setLayout(new GridBagLayout());
		setVisible(true);
		this.setResizable(false);
		
		switch(panel.title) {
		case "Main": setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	break;
		case "Players":
		case "Add": 
		case "Remove":
		case "Swap":
		case "Master":
		case "Help":
		default: setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		setTitle(panel.title);
		
		
		add(panel);
		pack();
		
		//center
		setLocationRelativeTo(null);
	}
}
