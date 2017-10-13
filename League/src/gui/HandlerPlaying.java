package gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import main.Main;

@SuppressWarnings("serial")
public class HandlerPlaying extends Handler {
	
	public HandlerPlaying() {
		super();
	}
	
	@SuppressWarnings("rawtypes")
	protected Transferable createTransferable(JComponent c) {
		JList list = (JList) c;
		
		String value = list.getSelectedValue().toString();
        StringBuffer buff = new StringBuffer();
       
        buff.append("Playing");
        buff.append(value);
        
        return new StringSelection(buff.toString());
	}
	
	public boolean importData(TransferHandler.TransferSupport info) {
        if (!info.isDrop()) {
            return false;
        }
        
//		JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
//		JList list = (JList)info.getComponent();
//		DefaultListModel model = (DefaultListModel)list.getModel();
//		String swap = (String) model.get(dl.getIndex());
//		String[] split2 = swap.split(" ");
        
        //Get the string of the drop location
        String[] split2 = ((String)((DefaultListModel<?>)((JList<?>)info.getComponent()).getModel()).get(((JList.DropLocation)info.getDropLocation()).getIndex())).split(" ");
        
        //Get the string that is being dropped.
        Transferable t = info.getTransferable();
        String data;
        try {
            data = (String)t.getTransferData(DataFlavor.stringFlavor);
        } 
        catch (Exception e) { return false; }
        
        String[] split = data.split(" ");
        if(split[0].equals("Master"))
        	Main.add(split[1],split[2]);
        else if(split[0].equals("Playing"))
        	Main.swap(split[1],split[2],split2[1],split2[2]);
        		
        return true;
    }

}
