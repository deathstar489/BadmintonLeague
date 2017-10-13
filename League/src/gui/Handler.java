package gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public abstract class Handler extends TransferHandler {
	
	public Handler() {
		super();
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

	
	
	
//	public boolean importData(TransferHandler.TransferSupport info) {
//        if (!info.isDrop()) {
//            return false;
//        }
//
//        // Get the string that is being dropped.
//        Transferable t = info.getTransferable();
//        
//        String data;
//        try {
//            data = (String)t.getTransferData(DataFlavor.stringFlavor);
//        } 
//        catch (Exception e) {
//        	return false;
//        }
//        
//        return true;
//    }
	
	/**
     * Remove the items moved from the list.
     */
    protected void exportDone(JComponent c, Transferable data, int action) {        
    }
	

}
