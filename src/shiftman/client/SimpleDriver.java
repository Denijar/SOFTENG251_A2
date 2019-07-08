package shiftman.client;

import java.util.List;

import shiftman.server.ShiftMan;
import shiftman.server.ShiftManServer;

public class SimpleDriver {
	
	public static void main(String[] args) {
		
		ShiftMan scheduler = new ShiftManServer();
		
		String shopName = "Bunnings";
		
		String message = scheduler.newRoster(shopName);
		
		System.out.println(message);
		
		//Working hours from 6:30am to 4pm
		message = scheduler.setWorkingHours("Tuesday", "06:30", "16:00");
		
		//Working hours from 6:30am to 4pm
		message = scheduler.setWorkingHours("Wednesday", "06:30", "16:00");
		
		System.out.println(message);
		
		//11am to 1pm shift
		message = scheduler.addShift("Tuesday", "11:00", "13:00", "0");
		
		//11am to 1pm shift
		message = scheduler.addShift("Wednesday", "11:00", "13:00", "6");
		
		System.out.println(message);
		
		//12:00pm to 12:30pm shift (OVERLAPS)
		message = scheduler.addShift("Tuesday", "12:00", "12:30", "6");
				
		System.out.println(message);
		
		//2pm to 4pm shift
		message = scheduler.addShift("Tuesday", "14:00", "16:00", "1");
						
		System.out.println(message);
		
		message = scheduler.registerStaff("Jared", "James");
		
		System.out.println(message);
		
		message = scheduler.registerStaff("Jacky", "James");
		
		System.out.println(message);
		
		message = scheduler.registerStaff("Xharlie", "Xhap");
		
		System.out.println(message);
		
		message = scheduler.registerStaff("Billy", "Bones");
		
		System.out.println(message);
		
		message = scheduler.assignStaff("Tuesday", "14:00", "16:00", "Jared", "James", true);
		
		System.out.println(message);
		
		message = scheduler.assignStaff("Tuesday", "14:00", "16:00", "Billy", "Bones", true);
		
		System.out.println(message);
		
		message = scheduler.assignStaff("Wednesday", "11:00", "13:00", "Billy", "Bones", false);
		
		System.out.println(message);
		
		message = scheduler.assignStaff("Wednesday", "11:00", "13:00", "Xharlie", "Xhap", false);
		
		System.out.println(message);
		
		message = scheduler.assignStaff("Tuesday", "14:00", "16:00", "Billy", "Bones", true);
		
		System.out.println(message);
		
		message = scheduler.assignStaff("Tuesday", "14:00", "16:00", "Xharlie", "Xhap", false);
		
		System.out.println(message);
		
		List<String> stringList = scheduler.getRegisteredStaff();
		
		System.out.println(stringList);
		
		stringList = scheduler.getUnassignedStaff();
		
		System.out.println(stringList);
		
		stringList = scheduler.overstaffedShifts();
		
		System.out.println(stringList);
		
		stringList = scheduler.getRosterForDay("Tuesday");
		
		System.out.println(stringList);
		
		message = scheduler.displayRoster();
		
		System.out.println(message);
		
		
	}

}
