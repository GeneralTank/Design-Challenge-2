package designchallenge1;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.*;

/**
 *
 * @author Arturo III
 */
public class TableRenderer extends JTextPane implements TableCellRenderer
{
	private ArrayList<Event> event;
	
	public TableRenderer(ArrayList<Event> e) {
		this.event = e;
	}
	
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
            
            StyledDocument styleDoc = this.getStyledDocument();
            Style s = this.addStyle("temp", null);
            StyleConstants.setForeground(s, Color.black);
            if (column == 0 || column == 6)
                    setBackground(new Color(220,220,255));
            else
                    setBackground(Color.WHITE);
            setBorder(null);
            //setForeground(Color.black);
            
            try {
            	styleDoc.remove(0, styleDoc.getLength());
            }catch (BadLocationException ex) {
            	Logger.getLogger(TableRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(value != null){
                
                String str = String.valueOf(value);
                String sub = str.substring(str.indexOf(" ") + 1, str.length());
                
                try {
                
                    if(str.indexOf(" ") == -1)
                        styleDoc.insertString(styleDoc.getLength(), str, s);
                    else
                        styleDoc.insertString(styleDoc.getLength(), str.substring(0, str.indexOf(" ")), s);
                    
                    for(int i = 0; i < event.size(); i++){
                    
                        if(sub.contains(event.get(i).eName)){
                        
                            switch((event.get(i).eColor.toLowerCase().replaceAll("\\s", ""))){
                            
                                case "red":
                                           StyleConstants.setForeground(s, Color.RED);
                                           //setForeground(Color.RED);
                                           break;
                                case "blue":
                                           StyleConstants.setForeground(s, Color.BLUE);
                                           //setForeground(Color.BLUE);
                                           break;
                                case "green":
                                           StyleConstants.setForeground(s, Color.GREEN);
                                           //setForeground(Color.GREEN);
                                           break;
                                default:
                                        StyleConstants.setForeground(s, Color.BLACK);
                                        break;
                            
                            }
                            styleDoc.insertString(styleDoc.getLength(), " " + event.get(i).eName, s);
                        }
                    
                    }
                
                } catch(BadLocationException e){
                
                Logger.getLogger(TableRenderer.class.getName()).log(Level.SEVERE, null, e);
                
                }
            
            }
            
            //if user clicks on a panel
            if(selected){
                setBackground(Color.LIGHT_GRAY);
            }
            
            
            
            
            
            
            
            
            return this;  
    }
}
