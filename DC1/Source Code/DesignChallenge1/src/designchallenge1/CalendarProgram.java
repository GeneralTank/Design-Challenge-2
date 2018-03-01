
package designchallenge1;

/**
 *
 * @author Arturo III 
 */

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalendarProgram{
	
	//added list of events
	public ArrayList<Event> events = new ArrayList<>();
	
	
	
	//added calendar itself
	public CalendarProgram calendar;
	
	
	//added list of observers
	private ArrayList<Observer> observers = new ArrayList<>();
	
	/**** Day Components ****/
	public int yearBound, monthBound, dayBound, yearToday, monthToday;

	/**** Swing Components ****/
    public JLabel monthLabel, yearLabel;
    public JButton btnPrev, btnNext;
    public JComboBox cmbYear;
    public JFrame frmMain;
	public Container pane;
	public JScrollPane scrollCalendarTable;
	public JPanel calendarPanel;
	
	
	//gui stuff added
	public JCheckBox chckbxRecurring;
	public JComboBox ColorBox;
    private JLabel lblYear, lblMonth, lblDay, lblEvent;
    private JTextField EventTextField; 
        
    /**** Calendar Table Components ***/
	public JTable calendarTable;
    public DefaultTableModel modelCalendarTable;
    
    public void notifyObservers() {
    	for(int i = 0; i < events.size(); i++){
            if(events.get(i).day == dayBound && events.get(i).month-1 == monthToday && events.get(i).year == yearToday)
                for(int j = 0; j < observers.size(); j++)
                    observers.get(j).update(events.get(i));
            if(events.get(i).day == dayBound && events.get(i).month-1 == monthToday && events.get(i).year <= yearToday && events.get(i).recurring)
                for(int k = 0; k < observers.size(); k++)
                    observers.get(k).update(events.get(i));
        }
    }
   
    
    //added observer connector/attacher
    public void connectObserver(Observer o) {
    	
    	observers.add(o);
    	notifyObservers();
    }
       
    public void refreshCalendar(int month, int year){
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som, i, j;
				
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= yearBound-10)//Can we add a zero pls;
			btnPrev.setEnabled(false);
		if (month == 11 && year >= yearBound+100)
	        btnNext.setEnabled(false);
	                
		monthLabel.setText(months[month]);
		monthLabel.setBounds(320-monthLabel.getPreferredSize().width/2, 50, 360, 50);
	                
		cmbYear.setSelectedItem(""+year);
			
		for (i = 0; i < 6; i++)
			for (j = 0; j < 7; j++)
				modelCalendarTable.setValueAt(null, i, j);
			
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		//nod = number of days, som = start of month
		
		for (i = 1; i <= nod; i++){
				
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			modelCalendarTable.setValueAt(i, row, column);
				
		}
		for(i = 0; i < 6; i++){ //for checking if days are the same
            for(j = 0; j < 7; j++){
                if(modelCalendarTable.getValueAt(i, j) != null){
                    String[] parts = modelCalendarTable.getValueAt(i, j).toString().split(" ");
                        for(int k = 0; k < events.size(); k++){
                            if(events.get(k).recurring == false)
                                if(Integer.parseInt(parts[0]) == events.get(k).day && month + 1 == events.get(k).month && year == events.get(k).year)
                                    modelCalendarTable.setValueAt(modelCalendarTable.getValueAt(i, j) + " " + events.get(k).eName, i, j);
                            if(events.get(k).recurring == true)
                                if(Integer.parseInt(parts[0]) == events.get(k).day && month + 1 == events.get(k).month && year >= events.get(k).year)
                                    modelCalendarTable.setValueAt(modelCalendarTable.getValueAt(i, j) + " " + events.get(k).eName, i, j);
                        }
                }
            }
		}
		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer(events));
		
	}
        
	public CalendarProgram()
        {
		try {
             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
		catch (Exception e) {}
        
		calendar = this;
		
		frmMain = new JFrame ("Calendar Application");
        frmMain.setSize(660, 750);
		pane = frmMain.getContentPane();
		pane.setLayout(null);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		monthLabel = new JLabel ("January");
		yearLabel = new JLabel ("Change year:");
		cmbYear = new JComboBox();
		
		//add gui stuff here
		
		
		
		//^
		
		btnPrev = new JButton ("<<");
		btnNext = new JButton (">>");
		modelCalendarTable = new DefaultTableModel()
        {
	        public boolean isCellEditable(int rowIndex, int mColIndex)
	        {
	        	return false; //was changed from true
            }
        };
                
		calendarTable = new JTable(modelCalendarTable);
        calendarTable.addMouseListener(new MouseAdapter()   
        {  
        	public void mouseClicked(MouseEvent evt){ 
        		
            int col = calendarTable.getSelectedColumn();  
            int row = calendarTable.getSelectedRow();
            String[] parts = modelCalendarTable.getValueAt(row, col).toString().split(" ");
                        
            lblYear.setText("Year: " + cmbYear.getSelectedItem());
            lblMonth.setText("Month: " + monthLabel.getText());
            lblDay.setText("Day: " + parts[0]);
            //observer updates
            }
        
        });
                
		scrollCalendarTable = new JScrollPane(calendarTable);
		calendarPanel = new JPanel(null);

		calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
		
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		cmbYear.addActionListener(new cmbYear_Action());
		
		pane.add(calendarPanel);
		calendarPanel.add(monthLabel);
		calendarPanel.add(yearLabel);
		calendarPanel.add(cmbYear);
		calendarPanel.add(btnPrev);
		calendarPanel.add(btnNext);
		calendarPanel.add(scrollCalendarTable);
		//add necessary gui adjustments
		
        calendarPanel.setBounds(0, 0, 640, 721);
        monthLabel.setBounds(320-monthLabel.getPreferredSize().width/2, 50, 200, 50);
		yearLabel.setBounds(20, 610, 160, 40);
		cmbYear.setBounds(89, 610, 160, 40);
		btnPrev.setBounds(20, 50, 100, 50);
		btnNext.setBounds(520, 50, 100, 50);
		scrollCalendarTable.setBounds(20, 100, 600, 500);
                
		frmMain.setResizable(false);
		frmMain.setVisible(true);
		
		GregorianCalendar cal = new GregorianCalendar();
		dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
		monthBound = cal.get(GregorianCalendar.MONTH);
		yearBound = cal.get(GregorianCalendar.YEAR);
		monthToday = monthBound; 
		yearToday = yearBound;
		
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<7; i++){
			modelCalendarTable.addColumn(headers[i]);
		}
		
		calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background

		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		calendarTable.setRowHeight(76);
		
		JButton btnAddEvent = new JButton("Add Event");
		btnAddEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String filename = "";
				
				if(lblYear.getText() != null && lblMonth != null && lblDay != null) {
					int col = calendarTable.getSelectedColumn();
					int row = calendarTable.getSelectedRow();
					
					modelCalendarTable.setValueAt(modelCalendarTable.getValueAt(row, col) + " " + EventTextField.getText(), row, col);
					
					String[] mParts = lblMonth.getText().split(" ");
					
					int month = monthConverter(mParts[1]);
					
					String[] dParts = lblDay.getText().split(" ");
					String[] yParts = lblYear.getText().split(" ");
					
					String d = month + "/" + dParts[1] + "/" + yParts[1];
					
					if(chckbxRecurring.isSelected()) 
						events.add(new Event(d, EventTextField.getText(), ColorBox.getSelectedItem().toString(), true));
					else
						events.add(new Event(d, EventTextField.getText(), ColorBox.getSelectedItem().toString(), false));
					
					CSVDataParser CDataParser = new CSVDataParser(calendar);
					PSVDataParser PDataParser = new PSVDataParser(calendar);
					
					if(chckbxRecurring.isSelected())
						CDataParser.writeData(events);
					else
						PDataParser.writeData(events);
					
					notifyObservers();
				}
			}
		});
		btnAddEvent.setBounds(531, 619, 89, 23);
		calendarPanel.add(btnAddEvent);
		
		lblMonth = new JLabel("Month: ");
		lblMonth.setBounds(259, 623, 160, 27);
		calendarPanel.add(lblMonth);
		
		lblEvent = new JLabel("Event Name: ");
		lblEvent.setBounds(30, 664, 76, 14);
		calendarPanel.add(lblEvent);
		
		EventTextField = new JTextField();
		EventTextField.setBounds(116, 661, 108, 20);
		calendarPanel.add(EventTextField);
		EventTextField.setColumns(10);
		
		lblDay = new JLabel("Day: ");
		lblDay.setBounds(259, 648, 160, 30);
		calendarPanel.add(lblDay);
		
		chckbxRecurring = new JCheckBox("Recurring");
		chckbxRecurring.setBounds(529, 649, 71, 23);
		calendarPanel.add(chckbxRecurring);
		
		ColorBox = new JComboBox();
		ColorBox.setModel(new DefaultComboBoxModel(new String[] {"Red", "Blue", "Green"}));
		ColorBox.setBounds(531, 675, 89, 20);
		calendarPanel.add(ColorBox);
		
		lblYear = new JLabel("Year: ");
		lblYear.setBounds(259, 675, 160, 35);
		calendarPanel.add(lblYear);
		modelCalendarTable.setColumnCount(7);
		modelCalendarTable.setRowCount(6);
		
		for (int i = yearBound-100; i <= yearBound+100; i++){
			cmbYear.addItem(String.valueOf(i));
		}
		
		//initialize events here
		initEvents();
		
		refreshCalendar (monthBound, yearBound); //Refresh calendar
	}
	

	private void initEvents() {
		
		int i, month, year;
		
		//csvparser
		//psvparser 
	    CSVDataParser csvDataParser = new CSVDataParser(this);
	    PSVDataParser psvDataParser = new PSVDataParser(this);
	    
	    //csv data reader
		//psv data reader
	    csvDataParser.readData("Philippine Holidays");
	    psvDataParser.readData("DLSU Unicalendar");
	    
	    //csv processor
		//psv processor    
	    csvDataParser.processData(this);
	    psvDataParser.processData(this);
		
		for(i = 0; i < events.size(); i++) {
			
			month = events.get(i).month;
			year = events.get(i).year;
			refreshCalendar(month - 1, year);
			
		}
		
	}
	
	
	private int monthConverter(String month) {
		
		switch(month.toLowerCase()) {
		
		case "january":
			return 1;
			
		case "jan":
			return 1;
			
		case "february":
            return 2;
            
		case "feb":
			return 2;
            
        case "march":
            return 3;
            
        case "mar":
        	return 3;
            
        case "april":
            return 4;
            
        case "may":
            return 5;
            
        case "june":
            return 6;
            
        case "july":
            return 7;
            
        case "august":
            return 8;
            
        case "aug":
        	return 8;
            
        case "september":
            return 9;
            
        case "sept":
        	return 9;
            
        case "october":
            return 10;
            
        case "oct":
        	return 10;
            
        case "november":
            return 11;
            
        case "nov":
        	return 11;
            
        case "december":
            return 12;
            
        case "dec":
        	return 12;
            
        default: 
            return 0;
		
		}
		
	}
	
	
	class btnPrev_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (monthToday == 0)
                        {
				monthToday = 11;
				yearToday -= 1;
			}
			else
                        {
				monthToday -= 1;
			}
			refreshCalendar(monthToday, yearToday);
		}
	}
	class btnNext_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (monthToday == 11)
                        {
				monthToday = 0;
				yearToday += 1;
			}
			else
                        {
				monthToday += 1;
			}
			refreshCalendar(monthToday, yearToday);
		}
	}
	class cmbYear_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (cmbYear.getSelectedItem() != null)
                        {
				String b = cmbYear.getSelectedItem().toString();
				yearToday = Integer.parseInt(b);
				refreshCalendar(monthToday, yearToday);
			}
		}
	}
}
