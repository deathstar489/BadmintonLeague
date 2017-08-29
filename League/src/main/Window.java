package main;

import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class Window extends JFrame{

	public Window(Match match, int number) {

		JFrame f = new JFrame();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		setVisible(true);
		setTitle("Match " + number);
		
		add(match);
		pack();
		
		//center
		setLocationRelativeTo(null);
	}
	
	
	
	
	
	
	
	
	
	

}
