package main;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Panel extends JPanel implements ActionListener{

	private Font font = Utility.font;
	private Font text = new Font("Ariel", Font.PLAIN, 20);
	
	private GridBagConstraints gbc = new GridBagConstraints();
	
	private JFrame frame;
	public JList<Object> playingList;
	public JList<Object> masterList;
	
	public String title;
	
	public Panel(PanelType type) {

		setLayout(new GridBagLayout());
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		switch(type) {
		case MAIN:		title = "Main";		main();		break;
		case VIEW:		title = "Players";	view();		break;
		case ADD:		title = "Add";		add();		break;
		case REMOVE:	title = "Remove";	remove();	break;
		case SWAP:		title = "Swap";		swap();		break;
		case MASTER:	title = "Master";	master();	break;
		case HELP:		title = "Help";		help();		break;
		default:
		}  
	}

	private void main() {
		
		//Buttons
		JButton[] button = new JButton[] {
				new JButton("Start next round"),
				new JButton("View players"),
				new JButton("Add a player"),
				new JButton("Remove a player"),
				new JButton("Swap two players"),
				new JButton("View Matches / Submit Scores"),
				new JButton("View Master Players"),
				new JButton("Help Menu"),
				new JButton("Save to \"" + Main.FILE_NAME + "\"")
		};

		String[] command = {
				"Next",
				"View",
				"Add",
				"Remove",
				"Swap",
				"Match",
				"Master",
				"Help",
				"Save"
		};
		
		for(int x = 0; x < button.length; x++) {
			button[x].setActionCommand(command[x]);
			button[x].setFont(font);
			button[x].addActionListener(this);
			
			gbc.gridy = x;
			
			add(button[x], gbc);
		}
		
		frame = new Window(this);
	}
	
	private void view() {
		playingList = new JList<Object>();
		playingList.setName("playing");
		playingList.setDragEnabled(true);
		playingList.setFont(text);
		playingList.setTransferHandler(new Handler(PanelType.VIEW));
		updateText();
		add(playingList, gbc);
		frame = new Window(this);
	}
	
	private void master() {
		masterList = new JList<Object>();
		masterList.setName("Master");
		masterList.setDragEnabled(true);
		masterList.setFont(text);
		masterList.setTransferHandler(new Handler(PanelType.MASTER));
		masterList.setLayoutOrientation(JList.VERTICAL_WRAP);
		updateMaster();
		add(masterList, gbc);
		frame = new Window(this);
		
	}
	
	@SuppressWarnings({"unchecked", "rawtypes" })
	public void updateText() {
		
		DefaultListModel model = new DefaultListModel<>();
		model.addElement(" Number of Players: " + Player.getCount()+ "          ");
		model.addElement(" ");
		for(Player player: Main.players) {
			if(!Main.extras.contains(player))
				model.addElement(" " + player.toString());
			else
				model.addElement(" " + player + " - Sitting Out\n ");
		}
		model.addElement(" ");
		
		playingList.setModel(model);
		if(frame != null)
			frame.pack();
	}
	
	@SuppressWarnings({"unchecked", "rawtypes" })
	public void updateMaster() {
		
		DefaultListModel model= new DefaultListModel<>();
		model.addElement(" Number of Players: " + Main.master.size() + "          ");
		model.addElement(" ");
		for(String line: Main.master) {
			String[] split = line.split("\t");
			model.addElement(split[0] + " " + split[1]);
		}
		model.addElement(" ");
		masterList.setModel(model);
		if(frame != null)
			frame.pack();
	}
	
	private void name() {
		JLabel first = new JLabel("First Name");
		JLabel last = new JLabel("Last Name");
		first.setFont(text);
		last.setFont(text);
		gbc.gridx = 1;
		add(first, gbc);
		gbc.gridx = 2;
		add(last, gbc);
	}
	
	private JTextField[] entry() {
		
		JTextField tffirst = new JTextField(6);
		tffirst.setToolTipText("First Name");
		tffirst.setFont(font);
		gbc.gridx = 1;
		add(tffirst, gbc);
		
		JTextField tflast = new JTextField(6);
		tflast.setToolTipText("Last Name");
		tflast.setFont(font);
		gbc.gridx = 2;
		add(tflast, gbc);
		
		return new JTextField[] {tffirst, tflast};
	}
	
	private void add() {

		name();
		
		JLabel label = new JLabel("Player to Add:");
		label.setFont(text);
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(label, gbc);
		
		JTextField[] array = entry();
		JTextField tffirst = array[0];
		JTextField tflast = array[1];
		
		JButton button = new JButton("Add");
		button.setFont(font);
		
		ActionListener actionListener = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(tffirst.getText() != null && tflast.getText() != null) {
					Main.add(tffirst.getText(),tflast.getText());
					frame.dispose();
				}
			}
		};
		
		tflast.addActionListener(actionListener);
		button.addActionListener(actionListener);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		add(button, gbc);
		
		frame = new Window(this);
	}

	private void remove() {
		
		name();
		
		JLabel label = new JLabel("Player to Remove:");
		label.setFont(text);
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(label, gbc);
		
		JTextField[] array = entry();
		JTextField tffirst = array[0];
		JTextField tflast = array[1];
		
		JButton button = new JButton("Remove");
		button.setFont(font);
		
		ActionListener listener = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(tffirst.getText() != null && tflast.getText() != null) {
					Main.remove(tffirst.getText(),tflast.getText());
					frame.dispose();
				}
			}
		};
		
		tflast.addActionListener(listener);
		button.addActionListener(listener);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		add(button, gbc);
		
		frame = new Window(this);
	}
	
	private void swap() {

		name();
		
		JLabel label1 = new JLabel("First Player to Swap:");
		label1.setFont(text);
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(label1, gbc);
		
		JTextField[] array1 = entry();
		JTextField tffirst1 = array1[0];
		JTextField tflast1 = array1[1];
		
		JLabel label2 = new JLabel("Second Player to Swap:");
		label2.setFont(text);
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(label2, gbc);
		
		JTextField[] array2 = entry();
		JTextField tffirst2 = array2[0];
		JTextField tflast2 = array2[1];
		
		JButton button = new JButton("Swap");
		button.setFont(font);
		
		ActionListener listener = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Main.swap(tffirst1.getText(), tflast1.getText(), tffirst2.getText(), tflast2.getText());
				frame.dispose();
			}
		};
		
		tflast2.addActionListener(listener);
		button.addActionListener(listener);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		add(button, gbc);
		
		frame = new Window(this);
	}
	
	private void help() {
		JTextArea TextArea = new JTextArea();
		TextArea.setEditable(false);
		TextArea.setFont(text);
		TextArea.setText(
				" HELP WITH USING THE PROGRAM:\n"
				+"\t1. First, enter the first and last names of the people that are present into the \"Playing\" file. (Separated by a tab)\n"
				+"\t\t a. If you can't find the file open, look to the left and under BadmintonLeague_League > src > main. (It's near the bottom)\n"
				+"\t2. Then you run the program, which you should know cause you're reading this right now.\n"
				+"\t3. Simply type 0 to start the first round and go play.\n"
				+"\t4. Once you're done, come back and type 6 to submit the scores.\n"
				+"\t5. Once everyone is done playing, you can go back to step 3. and continue.\n"
				+"\t6. Once you are tired and you're done playing for the day, Type 9, then Type 8.\n"
				+"\t\t a. If you messed up and didn't save... Well... That's really too bad cause those games no longer count :P\n"
				+"\t\t b. Just kidding, they're probably already saved, so don't worry! XD\n\n"
				+" Before you start, make sure to pull:\n"
				+"\t1. Go to the left and right click the main folder. (BadmintonLeague)\n"
				+"\t2. Look through the options and click \"Team\". (It's near the bottom ish)\n"
				+"\t3. Look through the options and click \"Pull\". (It's near the top ish)\n\n"
				+" After all games are done, make sure to push:\n" 
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
				+" Anyways... If you have any questions, comments, or concerns,"
						+ " please feel free to contact tech support at (587)889-8369 or jiashuwang459@gmail.com! :D \n");
		
		add(TextArea, gbc);
		
		frame = new Window(this);
	}

	private void match() {
		
		for(Match match: Main.matches) {
			if(!match.finished())
				match.frame.setVisible(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		case "Next"  : Main.next();							break;
		case "View"  : Main.playersPanel.frame.setVisible(true); break;
		case "Add"   : new Panel(PanelType.ADD);			break;
		case "Remove": new Panel(PanelType.REMOVE);			break;
		case "Swap"  : new Panel(PanelType.SWAP);			break;
		case "Match" : match();								break;
		case "Master": Main.masterPanel.frame.setVisible(true); break;
		case "Help"  : new Panel(PanelType.HELP);			break;
		case "Save"  : Main.save();							break;
		default:
		}
	}
}
