import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

class Alarm{
	int day, month, year, hour, minute;

	Alarm(int day, int month, int year, int hour, int minute){
		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = hour;
		this.minute = minute;
	}
}

class AlarmLogic extends JFrame implements Runnable{
	ArrayList<Alarm> alarmlist = new ArrayList<Alarm>();
	String currdate[], currtime[];
	Date date;
	DateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
	DateFormat timef = new SimpleDateFormat("HH:mm:ss");
	
	AlarmLogic(){
		date = new Date();
		currdate = (datef.format(date)).split("/");
		currtime = (timef.format(date)).split(":");
	}

	public int getDay() {
		return Integer.parseInt(currdate[0]);
	}

	public int getMonth() {
		return Integer.parseInt(currdate[1]);
	}
	
	public int getYear() {
		return Integer.parseInt(currdate[2]);
	}
	
	public int getHours() {
		return Integer.parseInt(currtime[0]);
	}
	
	public int getMinutes() {
		return Integer.parseInt(currtime[1]);
	}

	void AddAlarm(Alarm a){
		alarmlist.add(a);
	}

	void MonitorAlarmsAsync(){
		Thread t = new Thread(this);
		t.start();
	}

	public void run(){
		try{	
		while(true){
			date = new Date();
			currdate = (datef.format(date)).split("/");
			currtime = (timef.format(date)).split(":");
			for(int i=0; i<alarmlist.size();i++){
				if(this.checkAlarmList(i, currdate, currtime)){
					alarmlist.remove(i);
//					JOptionPane.showMessageDialog(null, "Alarm Ring!!");
					continue;
				}
			}
			Thread.sleep((60 - Integer.parseInt(currtime[2])) * 1000);
		}
		}catch(Exception e){
			System.out.println("Exception found: " + e);
		}
	}

	boolean checkAlarmList(int index, String currdate[], String currtime[]){
		boolean ans = false;
		if(alarmlist.get(index).day == Integer.parseInt(currdate[0]) &&
		   alarmlist.get(index).month == Integer.parseInt(currdate[1]) &&
		   alarmlist.get(index).year == Integer.parseInt(currdate[2]) &&
		   alarmlist.get(index).hour == Integer.parseInt(currtime[0]) &&
		   alarmlist.get(index).minute == Integer.parseInt(currtime[1])){
							ans = true;
						}
		return(ans);
	}
	
}

class GUI extends JFrame implements ActionListener{
	
	int dayData, monthData, yearData, hourData, minuteData;
	JComboBox day, month, year, hour, minute;
	JLabel dd, MM, yyyy, HH, mm;
	JButton addAlarm;
	AlarmLogic alogic = new AlarmLogic();

	GUI(){
		this.setSize (400, 400);
		this.setTitle("Alarm App");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.createUI();
	}

	void createUI(){
		this.setLayout(null);
		
		day = new JComboBox();
		for(int i=1; i<=31; i++)
			day.addItem(i);
		day.setSelectedItem(alogic.getDay());
		
		month = new JComboBox();
		for(int i=1; i<=12; i++)
			month.addItem(i);
		month.setSelectedItem(alogic.getMonth());
		
		year = new JComboBox();
		for(int i=2012; i<=2012; i++)
			year.addItem(i);
		year.setSelectedItem(alogic.getYear());
		
		hour = new JComboBox();
		for(int i=1; i<=24; i++)
			hour.addItem(i);
		hour.setSelectedItem(alogic.getHours());
		
		minute = new JComboBox();
		for(int i=1; i<=59; i++)
			minute.addItem(i);
		minute.setSelectedItem(alogic.getMinutes());
		
		addAlarm = new JButton("Set Alarm");
		
		dd = new JLabel("Day");
		MM = new JLabel("Month");
		yyyy = new JLabel("Year");
		HH = new JLabel("Hour");
		mm = new JLabel("Minute");

		day.setSize(50,30);
		dd.setSize(50, 30);
		month.setSize(50,30);
		MM.setSize(50,30);
		year.setSize(70,30);
		yyyy.setSize(70, 30);
		hour.setSize(50,30);
		HH.setSize(50, 30);
		minute.setSize(50,30);
		mm.setSize(50, 30);
		addAlarm.setSize(110,30);

		day.setLocation(10,10);
		dd.setLocation(10, 40);
		month.setLocation(70,10);
		MM.setLocation(70, 40);
		year.setLocation(130,10);
		yyyy.setLocation(130, 40);
		hour.setLocation(210,10);
		HH.setLocation(210, 40);
		minute.setLocation(270,10);
		mm.setLocation(270, 40);
		addAlarm.setLocation(120,90);

		this.add(day);
		this.add(dd);
		this.add(month);
		this.add(MM);
		this.add(year);
		this.add(yyyy);
		this.add(hour);
		this.add(HH);
		this.add(minute);
		this.add(mm);
		this.add(addAlarm);

		addAlarm.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e){
		this.setAlarm();
	}
	
	void setAlarm(){
		dayData = (int) (day.getSelectedItem());
		monthData = (int) (month.getSelectedItem());
		yearData = (int) (year.getSelectedItem());
		hourData = (int) (hour.getSelectedItem());
		minuteData = (int) (minute.getSelectedItem());
		Alarm a = new Alarm(dayData, monthData, yearData, hourData, minuteData);
		alogic.AddAlarm(a);
		JOptionPane.showMessageDialog(null, "Alarm Added!!");		
	}
	
	void monitorAlarm(){
		alogic.MonitorAlarmsAsync();
	}
}

class UserInteraction{

	void start(){
		GUI g = new GUI();
		g.monitorAlarm();
		g.setVisible(true);
	}
}

public class AlarmMain{
	public static void main(String args[]){
		UserInteraction ui = new UserInteraction();
		ui.start();
	}
}