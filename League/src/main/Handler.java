package main;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public class Handler extends TransferHandler {
	
	private PanelType type;
	
	public Handler(PanelType type) {
		super();
		this.type = type;
	}
	
	public boolean canImport(TransferHandler.TransferSupport support) {
		if (!support.isDrop()) {
	        return false;
	    }
		
		//Check for String flavor
		if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }
       
		 // check if the source actions (a bitwise-OR of supported actions)
	    // contains the COPY action
	    boolean copySupported = (COPY & support.getSourceDropActions()) == COPY;
	    if (copySupported) {
	        support.setDropAction(COPY);
	        return true;
	    }

	    // COPY is not supported, so reject the transfer
	    return false;
		
	}
	
	public int getSourceActions(JComponent c) {
	   return COPY;
	}

	
	@SuppressWarnings("rawtypes")
	protected Transferable createTransferable(JComponent c) {
		JList list = (JList) c;
		
		String value = list.getSelectedValue().toString();
        StringBuffer buff = new StringBuffer();
       
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
        if(type == PanelType.MASTER && split[0].equals(""))
        	Main.remove(split[1],split[2]);
        else if(type == PanelType.VIEW && !split[0].equals(""))
        	Main.add(split[0],split[1]);
        		
        return true;
    }
	
	/**
     * Remove the items moved from the list.
     */
    protected void exportDone(JComponent c, Transferable data, int action) {        
    }
	

}
