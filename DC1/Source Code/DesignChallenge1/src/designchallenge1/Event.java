package designchallenge1;

public class Event {

	public final int month, day, year;
	public final String eName, eColor;
	public boolean recurring;

	public Event(String eDate, String eName, String eColor, boolean recurring) {
		// TODO Auto-generated constructor stub
		
		String[] dates = eDate.split("/");

        month = Integer.parseInt(dates[0]);
        day = Integer.parseInt(dates[1]);
        year = Integer.parseInt(dates[2]);
        
        this.eName = eName; 
        this.eColor = eColor;
        this.recurring = recurring;
		
	}

}
