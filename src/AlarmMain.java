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

class GetCurrTime{
	Calendar currTime;
	String timeString;	

	GetCurrTime(){
		currTime = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/M/d H:m");
		timeString=formatter.format(currTime.getTime());
	}
	String getTimestr(){
		return timeString;	
	}
}

class AlarmLogic extends JFrame implements Runnable{
	ArrayList<Alarm> alarmlist = new ArrayList<Alarm>();
	GetCurrTime timeobj;

	void AddAlarm(Alarm a){
		alarmlist.add(a);
	}

	void MonitorAlarmsAsync(){
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run(){
		while(true){
			timeobj = new GetCurrTime();
			for(int i=0; i<alarmlist.size(); i++){
				if(this.checkAlarmList(i, timeobj.getTimestr())){
					alarmlist.remove(i);
					JOptionPane.showMessageDialog(null, "Alarm Ring!!");
					continue;
				}
			}
		}
	}

	boolean checkAlarmList(int index, String sysTime){
		boolean ans = false;
		String timestr, day, month, year, hour, minute;
		
		day = Integer.toString(alarmlist.get(index).day);
		month = Integer.toString(alarmlist.get(index).month);
		year = Integer.toString(alarmlist.get(index).year);
		hour = Integer.toString(alarmlist.get(index).hour);
		minute = Integer.toString(alarmlist.get(index).minute);
		timestr = year + "/" + month + "/" + day + " " + hour + ":" + minute;
		
		if (timestr.equals(sysTime)){
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
		
		month = new JComboBox();
		for(int i=1; i<=12; i++)
			month.addItem(i);
		
		year = new JComboBox();
		for(int i=2012; i<=2012; i++)
			year.addItem(i);
		
		hour = new JComboBox();
		for(int i=0; i<=23; i++)
			hour.addItem(i);
		
		minute = new JComboBox();
		for(int i=0; i<=59; i++)
			minute.addItem(i);
		
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