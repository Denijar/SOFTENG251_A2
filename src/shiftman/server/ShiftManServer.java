package shiftman.server;

import java.util.Collections;
import java.util.List;

/**
 * The implementation of the provided ShiftMan interface for SOFTENG 215 Assignment 2
 * Due Date: Monday 29 April
 * @author Denise Jarry
 *
 */
public class ShiftManServer implements ShiftMan {

	private Roster _roster;

	public String newRoster(String shopName) {
		
		if (shopName == null) {
			return errorMessage("Shop name can't be null");
		}
		if (shopName.isEmpty()) {
			return errorMessage("Shop name can't be empty");
		}

		_roster = new Roster(shopName);
		
		return "";
	}

	
	public String setWorkingHours(String dayOfWeek, String startTime, String endTime) {
		
		if (startTime == null || endTime == null) {
			return errorMessage("Time can't be null");
		}
		
		try {
			_roster.setDayWorkingHours(dayOfWeek, startTime, endTime);
		}
		catch(ShiftManException e) {
			return errorMessage(e.getMessage());
		}
		
		return "";
	}
	
	public String addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers){
		
		if (startTime == null || endTime == null) {
			return errorMessage("Time can't be null");
		}
		if (minimumWorkers == null) {
			return errorMessage("Minimum workers can't be null");
		}
		if (minimumWorkers.isEmpty()) {
			return errorMessage("Minimum workers can't be empty");
		}
		
		try {
			_roster.addShift(dayOfWeek, startTime, endTime, minimumWorkers);
		}
		catch(ShiftManException e) {
			return errorMessage(e.getMessage());
		}
		
		return "";
	}
	
	public String registerStaff(String givenname, String familyName) {
		
		if (givenname == null || familyName == null) {
			return errorMessage("Name can't be null");
		}
		if (givenname.isEmpty() || familyName.isEmpty()) {
			return errorMessage("Name can't be empty");
		}
			
		try {
			_roster.addStaffMember(givenname, familyName);
		}
		catch(ShiftManException e) {
			return errorMessage(e.getMessage());
		}
		
		return"";
	}
	
	public String assignStaff(String dayOfWeek, String startTime, String endTime, String givenName, String familyName, boolean isManager) {
		
		if (startTime == null || endTime == null) {
			return errorMessage("Time can't be null");
		}
		if (givenName == null || familyName == null) {
			return errorMessage("Name can't be null");
		}
		if (givenName.isEmpty() || familyName.isEmpty()) {
			return errorMessage("Name can't be empty");
		}
		
		try {
			_roster.assignStaff(dayOfWeek, startTime, endTime, givenName, familyName, isManager);
		}
		catch(ShiftManException e) {
			return errorMessage(e.getMessage());
		}
		
		return "";
	}
	
	public List<String> getRegisteredStaff(){
		return _roster.getRegisteredStaff();
	}
	
	public List<String> getUnassignedStaff(){
		return _roster.getUnassignedStaff();
	}
	
	public List<String> shiftsWithoutManagers(){
		return _roster.getShiftsWithoutManagers();
	}
	
	public List<String> understaffedShifts(){
		return _roster.getUnderstaffedShifts();
	}
	
	public List<String> overstaffedShifts() {
		return _roster.getOverstaffedShifts();
	}
	
	public List<String> getRosterForDay(String dayOfWeek) {
		try {
			return _roster.getRosterForDay(dayOfWeek);
		}
		catch(ShiftManException e) {
			//List<String> stringList = new ArrayList<>();
			//return stringList; 
			return listError(e.getMessage());
		}
	}
	
	public List<String> getRosterForWorker(String workerName){
		if (workerName == null) {
			return listError("Worker name can't be null");
		}
		if (workerName.isEmpty()) {
			return listError("Worker name can't be empty");
		}
		try {
			String[] names = workerName.split(" ");
			return _roster.getRosterForWorker(names[0], names[1]);
		}
		catch(ShiftManException e) {
			return listError(e.getMessage());
		}
	}
	
	public List<String> getShiftsManagedBy(String managerName){
		if (managerName == null) {
			return listError("Manager name can't be null");
		}
		if (managerName.isEmpty()) {
			return listError("Manager name can't be empty");
		}
		try {
			String[] names = managerName.split(" ");
			return _roster.getShiftsManagedBy(names[0], names[1]);
		}
		catch(ShiftManException e) {
			return listError(e.getMessage());
		}
	}
	
	public String reportRosterIssues(){
		return "";
	}
	
	public String displayRoster(){
		return "";
	}
	
	/**
	 * Error message formatter
	 * @param message
	 * @return full error message
	 */
	private String errorMessage(String message) {
		return "ERROR " + message;
	}
	
	/**
	 * List error message formatter
	 * @param message
	 * @return
	 */
	 private List<String> listError(String message) {
	        return Collections.singletonList(errorMessage(message));
	}

}
