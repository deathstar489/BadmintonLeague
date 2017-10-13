package gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import main.Main;

@SuppressWarnings("serial")
public class HandlerMaster extends Handler {
	
	public HandlerMaster() {
		super();
	}
	
	@SuppressWarnings("rawtypes")
	protected Transferable createTransferable(JComponent c) {
		JList list = (JList) c;
		
		String value = list.getSelectedValue().toString();
        StringBuffer buff = new StringBuffer();
       
        buff.append("Master ");
        buff.append(value);
        
        return new StringSelection(buff.toString());
	}
	
	public boolean importData(TransferHandler.TransferSupport info) {
        if (!info.isDrop()) {
            return false;
        }

        // Get the string that is being dropped.
        Transferable t = info.getTransferable();
        String data;
        try {
            data = (String)t.getTransferData(DataFlavor.stringFlavor);
        } 
        catch (Exception e) { return false; }
        
        String[] split = data.split(" ");
        if(split[0].equals("Playing"))
        	Main.remove(split[1],split[2]);
        		
        return true;
    }

}
