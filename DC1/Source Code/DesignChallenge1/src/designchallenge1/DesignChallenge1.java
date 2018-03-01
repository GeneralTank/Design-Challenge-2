
package designchallenge1;

import facebook.FBView; //added  
import sms.SMSView; //added

/**
 *
 * @author Arturo III
 */


public class DesignChallenge1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CalendarProgram cp = new CalendarProgram();
        
        cp.connectObserver(new SMSObserver(new SMSView(), cp));
        cp.connectObserver(new FBObserver(new FBView(), cp));

    }
}
