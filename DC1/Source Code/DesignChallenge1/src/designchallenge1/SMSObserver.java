package designchallenge1;

import java.util.ArrayList; 
import java.util.Calendar;

import java.awt.Color;

import sms.SMS;
import sms.SMSView;

public class SMSObserver extends Observer {

	private SMSView smsView;
    private ArrayList<Event> events;
    private Calendar calendar;
	
	public SMSObserver(SMSView smsv, CalendarProgram calProg) {
		// TODO Auto-generated constructor stub
		super(calProg);
        events = new ArrayList<>();
        smsView = smsv;
	}


	@Override
	void update(Event e) {
		// TODO Auto-generated method stub
		if(!events.contains(e)) {
			events.add(e);
			calendar = Calendar.getInstance();
			calendar.set(Integer.parseInt(calProg.cmbYear.getSelectedItem().toString()), e.month - 1, e.day);
			
			switch((e.eColor.toLowerCase()).replaceAll("\\s", "")) {
			case "red":
				smsView.sendSMS(new SMS(e.eName, calendar, Color.RED));
				break;
			case "blue":
				smsView.sendSMS(new SMS(e.eName, calendar, Color.BLUE));
				break;
			case "green":
				smsView.sendSMS(new SMS(e.eName, calendar, Color.GREEN));
				break;
			default:
				smsView.sendSMS(new SMS(e.eName, calendar, Color.BLACK));
				break;
		}
		
		
		
		}
		
	}

}
