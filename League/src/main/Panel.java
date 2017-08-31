package main;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Panel extends JPanel implements ActionListener{

	private Font font = Utility.font;
	private Font font2 = new Font("Ariel", Font.PLAIN, 20);
	JLabel blank = new JLabel(" ");
	GridBagConstraints gbc = new GridBagConstraints();
	public static String title;
	public static JFrame frame;
	
	public Panel(PanelType type) {

		setLayout(new GridBagLayout());
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 100;
		
		switch(type) {
		case MAIN:		main();		title = "Main";		break;
		case ADD:		add();		title = "Add";		break;
		case REMOVE:	remove();	title = "Remove";	break;
		case SWAP:		swap();		title = "Swap";		break;
		case MATCH:		match();	title = "Match";	break;
		case HELP:		help();		title = "help";		break;
		default:
		}  
	}

	private void main() {
		
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
		
		for(int x = 0; x < button.length; x++) {
			button[x].setActionCommand(command[x]);
			button[x].setFont(font);
			button[x].addActionListener(this);
			
			gbc.gridy = x;
			
			add(button[x],gbc);
		}
	}
	
	private void add() {

		JLabel label = new JLabel("Player to Add:");
		label.setFont(font);
		gbc.gridwidth = 2;
		add(label, gbc);
		
		JTextField[] array = name();
		JTextField tffirst = array[0];
		JTextField tflast = array[1];
		
		JButton button = new JButton("Add");
		button.setFont(font);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Main.add(tffirst.getText(),tflast.getText());
				frame.dispose();
			}
		});
		gbc.gridy = 4;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		add(button, gbc);
	}

	private void remove() {
		
		gbc.ipadx = 0;
		JLabel label = new JLabel("Player to Remove:");
		label.setFont(font);
		gbc.gridx = 0;
		add(label, gbc);
		
		JTextField tffirst = new JTextField(6);
		tffirst.setToolTipText("First Name");
		tffirst.setFont(font);
		gbc.gridx = 1;
		add(tffirst, gbc);
		
		JTextField tflast = new JTextField(6);
		tffirst.setToolTipText("Last Name");
		tflast.setFont(font);
		gbc.gridx = 2;
		add(tflast, gbc);
		
		//JTextField[] array = name();
		//JTextField tffirst = array[0];
		//JTextField tflast = array[1];
		
		JButton button = new JButton("Remove");
		button.setFont(font);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Main.remove(tffirst.getText(),tflast.getText());
				frame.dispose();
			}
		});
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		add(button, gbc);
	}

	private JTextField[] name() {
		
		JLabel first = new JLabel("First Name: ");
		first.setFont(font);
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		add(first, gbc);
		
		JTextField tffirst = new JTextField(6);
		tffirst.setFont(font);
		gbc.gridx = 1;
		add(tffirst, gbc);
		
		JLabel last = new JLabel("Last Name: ");
		last.setFont(font);
		gbc.gridy = 3;
		gbc.gridx = 0;
		add(last, gbc);
		
		JTextField tflast = new JTextField(6);
		tflast.setFont(font);
		gbc.gridx = 1;
		add(tflast, gbc);
		
		return new JTextField[] {tffirst, tflast};
	}
	
	private JTextField[] name(String forSwap) {
		
		JLabel first = new JLabel("First Name: ");
		first.setFont(font);
		gbc.gridy = 2;
		gbc.gridx = 2;
		gbc.gridwidth = 1;
		add(first, gbc);
		
		JTextField tffirst = new JTextField(6);
		tffirst.setFont(font);
		gbc.gridx = 3;
		add(tffirst, gbc);
		
		JLabel last = new JLabel("Last Name: ");
		last.setFont(font);
		gbc.gridy = 3;
		gbc.gridx = 2;
		add(last, gbc);
		
		JTextField tflast = new JTextField(6);
		tflast.setFont(font);
		gbc.gridx = 3;
		add(tflast, gbc);
		
		return new JTextField[] {tffirst, tflast};
	}
	
	
	private void swap() {
		JLabel label1 = new JLabel("First Player to Swap:");
		label1.setFont(font);
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(label1, gbc);
		
		JTextField[] array1 = name();
		JTextField tffirst1 = array1[0];
		JTextField tflast1 = array1[1];
		
		JLabel label2 = new JLabel("Second Player to Swap:");
		label2.setFont(font);
		gbc.gridy = 0;
		gbc.gridx = 2;
		add(label2, gbc);
		
		JTextField[] array2 = name(" ");
		JTextField tffirst2 = array2[0];
		JTextField tflast2 = array2[1];
		
		JButton button = new JButton("Swap");
		button.setFont(font);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Main.swap(tffirst1.getText(), tflast1.getText(), tffirst2.getText(), tflast2.getText());
				frame.dispose();
			}
		});
		gbc.gridy = 4;
		gbc.gridx = 0;
		gbc.gridwidth = 4;
		add(button, gbc);
		
	}
	
	private void help() {
		JTextArea text = new JTextArea();
		text.setEditable(false);
		text.setFont(font2);
		text.setText(
				"HELP WITH USING THE PROGRAM:\n"
				+"\t1. First, enter the first and last names of the people that are present into the \"Playing\" file. (Separated by a tab)\n"
				+"\t\t a. If you can't find the file open, look to the left and under BadmintonLeague_League > src > main. (It's near the bottom)\n"
				+"\t2. Then you run the program, which you should know cause you're reading this right now.\n"
				+"\t3. Simply type 0 to start the first round and go play.\n"
				+"\t4. Once you're done, come back and type 6 to submit the scores.\n"
				+"\t5. Once everyone is done playing, you can go back to step 3. and continue.\n"
				+"\t6. Once you are tired and you're done playing for the day, Type 9, then Type 8.\n"
				+"\t\t a. If you messed up and didn't save... Well... That's really too bad cause those games no longer count :P\n"
				+"\t\t b. Just kidding, they're probably already saved, so don't worry! XD\n\n"
				+"Before you start, make sure to pull:\n"
				+"\t1. Go to the left and right click the main folder. (BadmintonLeague)\n"
				+"\t2. Look through the options and click \"Team\". (It's near the bottom ish)\n"
				+"\t3. Look through the options and click \"Pull\". (It's near the top ish)\n\n"
				+"After all games are done, make sure to push:\n" 
				+"\t1. Go to the left and right click the main folder. (BadmintonLeague)\n"
				+"\t2. Look through the options and click \"Team\". (It's near the bottom ish)\n"
				+"\t3. Look through the options and click \"Commit...\". (It's the first one)\n"
				+"\t\t a. Or you can simply press:   Ctrl + #\n"
				+"\t4. Something should pop up on the bottom, and there should be a few files under \"Unstaged Changes\". (Top Left)\n"
				+"\t\t If there isn't... Then you didn't save or something went wrong. Please contact tech support.\n"
				+"\t5. a. Select and Drag all of those files down to \"Staged Changes\". (Bottom Left)\n"
				+"\t6. Now under \"Commit Message\", Enter in any other relavent information that you believe to be important. (Top Right)\n"
				+"\t\t a. If you have nothing to write, then simply put the date.\n"
				+"\t7. Now click \"Commit and Push...\". Wait for something to pop up, and hit \"OK\".\n"
				+"\t\t a. If nothing pops up... then you clicked \"Commit\" instead, then you are a BAKA!\n"
				+"\t\t b. Follow Steps 1 and 2, then click \"Push to UpStream\". (It's near the top ish)\n\n"
				+"Anyways... If you have any questions, comments, or concerns,"
						+ " please feel free to contact tech support at (587)889-8369 or jiashuwang459@gmail.com! :D\n"
				+"\n\n"
				);
		
		add(text);
	}

	private void match() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
//		case "Next": Main.next();		break;
//		case "View": view();			break;
//		case "Add": add();				break;
//		case "Remove": remove();		break;
//		case "Swap": swap();			break;
//		case "Submit": score();			break;
//		case "Display": display();		break;
//		case "Help": help();			break;
//		case "Close": running = false;	break;
//		case "Save": save();			break;
		default:
		}
	}
	
	public static void main(String[] args) {
		
		//Panel panel = new Panel(PanelType.MAIN);
		Panel panel = new Panel(PanelType.HELP);
		frame = new Window(panel);
	}
}
