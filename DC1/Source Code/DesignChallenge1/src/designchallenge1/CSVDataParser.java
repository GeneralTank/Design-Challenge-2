
package designchallenge1;

import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVDataParser extends SVDataParser{
    
    //public ArrayList<Event> events = new ArrayList<>(); we technically dont need this i think
    
    public CSVDataParser(CalendarProgram calendar){
        super.calendar = calendar;
    }
    
    @Override
    void readData(String name){
        System.out.println("Reading from CSV file...");
        String filename = name + ".csv";
        File filePH = new File(filename);
        
        List<String> temp = new ArrayList<String>();
        String line = "";
        String delimiter = ",";
        String[] seperated;
        
         try{
            BufferedReader br = new BufferedReader(new FileReader(filePH));
            
            while((line = br.readLine()) != null){
                seperated = line.split(delimiter);
                temp = Arrays.asList(seperated);
                super.events.add(new Event(temp.get(0), temp.get(1), temp.get(2), true));
            }
            
        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("csv done reading");
    }

    @Override
    void writeData(ArrayList<Event> events) {
        FileWriter w;
        
        try {
            w = new FileWriter("Philippine Holidays.csv");
            
            for(int i = 0; i < events.size(); i++){
                if(events.get(i).recurring == true){
                    w.write(events.get(i).month+ "/" +events.get(i).day+ "/" +events.get(i).year);
                    w.append(",");
                    w.write(events.get(i).eName);
                    w.append(",");
                    w.write(events.get(i).eColor);
                    w.append("\n");
                    System.out.println("wrote csv");
                }
            }
            w.close();
            
        } catch (IOException ex) {
            Logger.getLogger(CSVDataParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
