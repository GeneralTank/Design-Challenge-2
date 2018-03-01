
package designchallenge1;

import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PSVDataParser extends SVDataParser{
    
    //public ArrayList<Event> events = new ArrayList<>(); we technically dont need this i think
    
    public PSVDataParser(CalendarProgram calendar){
        super.calendar = calendar;
    }
    
    @Override
    void readData(String name){
        System.out.println("Reading from PSV file...");
        String filename = name + ".psv";
        File filePH = new File(filename);
        
        List<String> temp = new ArrayList<String>();
        String line = "";
        String delimiter = " [|] ";
        String[] seperated;
        
         try{
            BufferedReader br = new BufferedReader(new FileReader(filePH));
            
            while((line = br.readLine()) != null){
                seperated = line.split(delimiter);
                temp = Arrays.asList(seperated);
                super.events.add(new Event(temp.get(1), temp.get(0), temp.get(2), false));
            }
            
        } catch(Exception e){
        	System.out.println("Fail");
            e.printStackTrace();
        }
        System.out.println("done reading from psv");
    }

    @Override
    void writeData(ArrayList<Event> events) {
        FileWriter w;
        
        try {
            w = new FileWriter("DLSU Unicalendar.psv");
            
            for(int i = 0; i < events.size(); i++){
                if(events.get(i).recurring == false){
                    w.write(events.get(i).eName);
                    w.write(" | ");
                    w.write(events.get(i).month+ "/" +events.get(i).day+ "/" +events.get(i).year);
                    w.write(" | ");
                    w.write(events.get(i).eColor);
                    w.append("\n");
                    System.out.println("wrote psv");
                }
            }
            w.close();
            
        } catch (IOException ex) {
            Logger.getLogger(CSVDataParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
