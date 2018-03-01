package designchallenge1;

import java.util.*;
import java.awt.Color;
import facebook.FBView;

public class FBObserver extends Observer {
	
	private FBView fbView;
	private ArrayList<Event> events = new ArrayList<>();

	public FBObserver(FBView fbView, CalendarProgram calProg) {
		// TODO Auto-generated constructor stub
		
		super(calProg);
		this.fbView = fbView;
			
	}

	@Override
	public void update(Event e) {
		// TODO Auto-generated method stub
		
			if(!events.contains(e)) {
				events.add(e);
				switch((e.eColor.toLowerCase()).replaceAll("\\s", "")) {
				case "red":
					fbView.showNewEvent(e.eName, e.month, e.day, Integer.parseInt(calProg.cmbYear.getSelectedItem().toString()), Color.red);
					break;
				case "blue":
					fbView.showNewEvent(e.eName, e.month, e.day, Integer.parseInt(calProg.cmbYear.getSelectedItem().toString()), Color.blue);
					break;
				case "green":
					fbView.showNewEvent(e.eName, e.month, e.day, Integer.parseInt(calProg.cmbYear.getSelectedItem().toString()), Color.green);
					break;
				default:
					fbView.showNewEvent(e.eName, e.month, e.day, Integer.parseInt(calProg.cmbYear.getSelectedItem().toString()), Color.black);
					break;
				
				}
			}
			
			
			
		}

}
