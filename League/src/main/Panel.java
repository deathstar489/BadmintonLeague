package main;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel extends JPanel implements ActionListener{

	private Font font = Utility.font;
	
	public Panel(PanelType type) {
		
		switch(type) {
		case MAIN:		main();		break;
		case ADD:		add();		break;
		case REMOVE:	remove();	break;
		case SWAP:		swap();		break;
		case MATCH:		match();	break;
		default:	
		}
	}

	private void main() {
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Buttons
		JButton[] button = new JButton[] {
				new JButton("Start next round"),
				new JButton("View players"),
				new JButton("Add a player"),
				new JButton ("Remove a player"),
				new JButton("Swap two players"),
				new JButton("Submit a score"),
				new JButton("Display matches"),
				new JButton("Help Menu"),
				new JButton("End Game"),
				new JButton("Save to \"" + Main.FILE_NAME + "\"")
		};

		String[] command = {
				"Next",
				"View",
				"Add",
				"Remove",
				"Swap",
				"Submit",
				"Display",
				"Help",
				"Close",
				"Save"
		};
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 100;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		
		for(int x = 0; x < button.length; x++) {
			button[x].setActionCommand(command[x]);
			button[x].setFont(font);
			button[x].addActionListener(this);
			
			gbc.gridy = x;
			
			
			
			add(button[x],gbc);
		}
	}

	private void add() {
		// TODO Auto-generated method stub
		
	}

	private void remove() {
		// TODO Auto-generated method stub
		
	}

	private void swap() {
		// TODO Auto-generated method stub
		
	}

	private void match() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		default:
		}
	}
	
	public static void main(String[] args) {
		
		Panel panel = new Panel(PanelType.MAIN);
		new Window(panel);	
	}
}
