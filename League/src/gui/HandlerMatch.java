package gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import main.Main;

//UNUSED

@SuppressWarnings("serial")
public class HandlerMatch extends Handler {
	
	private int player;
	
	public HandlerMatch() {
		super();
	}
	
	protected Transferable createTransferable(JComponent c) {
		JButton button = (JButton) c;
		
		String value = button.getText();
		String[] split = value.replaceAll(">", " ").replaceAll("<", " ").split(" ");
		StringBuffer buff = new StringBuffer();
		
		String string = "ERROR";
		switch(player) {
		case 0: 
		case 1: string = split[4] + " " + split[5]; break;
		case 2: string = split[9] + " " + split[10]; break;
		default:
		}
		
		System.out.println(string);
		
        buff.append("Match ");
        buff.append(string);
        
        return new StringSelection(buff.toString());
	}
	
	public void exportAsDrag(JComponent comp, InputEvent e, int action, int player) {
		this.player = player;
		super.exportAsDrag(comp, e, action);
	}

	public boolean canImport(TransferHandler.TransferSupport support) {
		System.out.println("....");
		boolean bool = super.canImport(support);
		return bool;
	}
	
	public boolean importData(TransferHandler.TransferSupport info) {
        if (!info.isDrop()) {
        	System.out.println(".............");
            return false;
        }

		JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
		System.out.println(dl.getDropPoint().getX());
		System.out.println(dl.getDropPoint().getY());
		JButton button = (JButton)info.getComponent();
		DefaultListModel<?> model = (DefaultListModel<?>)button.getModel();
		
		String swap = (String) model.get(dl.getIndex());
		String[] split2 = swap.split(" ");
        
        //Get the string of the drop location
      //  String[] split2 = ((String)((DefaultListModel<?>)((JList<?>)info.getComponent()).getModel()).get(((JList.DropLocation)info.getDropLocation()).getIndex())).split(" ");
        
        //Get the string that is being dropped.
        Transferable t = info.getTransferable();
        String data;
        try {
            data = (String)t.getTransferData(DataFlavor.stringFlavor);
        } 
        catch (Exception e) { return false; }
        
        String[] split = data.split(" ");
        if(split[0].equals("Match"))
    		Main.swap(split[1],split[2],split2[1],split2[2]);
        
        return true;
    }

}
