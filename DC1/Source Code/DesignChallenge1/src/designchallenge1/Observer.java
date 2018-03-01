package designchallenge1;

// @authors Julian De Castro & Kyle Sim

abstract class Observer {
	
	//connector
	protected CalendarProgram calProg;
	
	abstract void update(Event e);
	
	public Observer(CalendarProgram calProg) {

		this.calProg = calProg;
		
	}

	

}
