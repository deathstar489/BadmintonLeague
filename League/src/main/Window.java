package main;

import java.awt.GridBagLayout;

import javax.swing.JFrame;

/**
 * @author Jiashu Wang
 *
 */
public class Window extends JFrame{

	/**
	 * JFrame for matches.
	 * @param match The match to display
	 */
	public Window(Match match) {

		JFrame f = new JFrame();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		setVisible(true);
		setTitle("Match " + Match.count);
		
		add(match);
		pack();
		
		//center
		setLocationRelativeTo(null);
	}
	
	
	
	
	
	
	
	
	
	

}
