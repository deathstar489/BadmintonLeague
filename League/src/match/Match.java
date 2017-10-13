package match;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Main;
import main.Player;
import main.Utility;
import main.Window;


/**
 * @author Jiashu Wang
 *
 */
@SuppressWarnings("serial")
public abstract class Match extends JPanel implements ActionListener{//Object Match, it creates a match

	private Font font = Utility.font;
	
	private boolean finished = false;
	protected MatchType type;
	public static int count = 0;
	
	public Window frame;
	
	private JButton[] buttons = new JButton[] {new JButton(), new JButton(), new JButton()};
	
	/**
	 * Creates a panel to place into window frame.
	 */
	protected void panel() {
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Labels
		JLabel number = new JLabel(" Match " + count + ":");
		JLabel label = new JLabel("What were the results? (Who Won?)", SwingConstants.CENTER);
		
		//Fonts
		number.setFont(font);
		label.setFont(font);
		
		//Layout for Label
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 3;
		gbc.gridy = 1;
		
		add(number, gbc);
		gbc.gridy = 2;
		add(label, gbc);
				
		//Button Text
		text();
		
		//Action References
		buttons[0].setActionCommand("First");
		buttons[1].setActionCommand("Tie");
		buttons[2].setActionCommand("Second");
		
		//Layout for Buttons
		gbc.gridwidth = 1;
		gbc.gridy = 3;
		gbc.ipadx = 100;
		gbc.insets = new Insets(5,5,5,5);  //padding

		//For all buttons...
		for(JButton button: buttons) {
			button.setFont(font);
			button.addActionListener(this);
			add(button, gbc);
//			button.setTransferHandler(new HandlerMatch());
//			button.addMouseListener(new MouseAdapter() {
//			    public void mousePressed(MouseEvent e) {
//			    	
//			    	if(33 < e.getY() && e.getY() < 66 && type == MatchType.DOUBLES) {
//			    	}
//			    	else {
//			    		int player;
//			    		if(type == MatchType.SINGLES)
//			    			player = 0;
//			    		else if(e.getY() <= 33)
//				        	player = 1;
//				        else
//				        	player = 2;
//				    	System.out.println(e.getY());
//				        JComponent c = (JComponent) e.getComponent(); 
//				        HandlerMatch handler = (HandlerMatch) c.getTransferHandler();
//				        handler.exportAsDrag(c, e, TransferHandler.COPY, player);
//			    	}
//			    }
//			    
//			    public void mouseReleased(MouseEvent e) {
//
//			        JComponent c = (JComponent) e.getComponent(); 
//			    	HandlerMatch handler = (HandlerMatch) c.getTransferHandler();
//			    	//handler.importData(info);
//			    }
//			    
//			});
		}
		
		frame = new Window(this);
	}

	protected void text() {
		buttons[0].setText("<html><center>" + getGame("First").replaceAll("\\n", "<br>") + "</center></html>");
		buttons[1].setText("<html><center>Tie</center></html>");
		buttons[2].setText("<html><center>" + getGame("Second").replaceAll("\\n", "<br>") + "</center></html>");
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
	public abstract void display();
	
	/**
	 * Completes a Match. (Submitting a score)
	 */
	public abstract void complete();
	
	/**
	 * Swaps two Players.
	 * @param player1 The first player to swap.
	 * @param player2 The second player to swap.
	 */
	public abstract void swap(Player player1, Player player2);

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
