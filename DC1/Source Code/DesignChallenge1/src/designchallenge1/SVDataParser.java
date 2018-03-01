package designchallenge1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


//Template Pattern
abstract public class SVDataParser{ 
    
    protected ArrayList<Event> events = new ArrayList<>();
    protected CalendarProgram calendar;
    protected String name;
    
    abstract void readData(String name);
    public void processData(CalendarProgram c){
        System.out.println("Looping through loaded CSV file...");
        for(int i = 0; i < events.size(); i++){
            calendar.events.add(events.get(i));
        }
    }
    
    abstract void writeData(ArrayList<Event> events);
}

