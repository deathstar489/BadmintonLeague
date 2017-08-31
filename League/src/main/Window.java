package main;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Jiashu Wang
 *
 */
public class Window extends JFrame{

	/**
	 * JFrame for matches.
	 * @param match The match to display.
	 */
	public Window(Match match) {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		setVisible(true);
		setTitle("Match " + Match.count);
		
		add(match);
		pack();
		
		//center
		setLocationRelativeTo(null);
	}
	
	/**
	 * JFrame for main program.
	 * @param main The main program.
	 */
	public Window(Panel panel) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		setVisible(true);
		//setTitle("Main");
		setSize(1000,1000);
		add(panel);
		pack();
		
		//center
		setLocationRelativeTo(null);
	}
	
	
	public Window(JPanel panel, String title) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		setVisible(true);
		setTitle(title);
		
		add(panel);
		pack();
		
		//center
		setLocationRelativeTo(null);
	}
	
	
}
