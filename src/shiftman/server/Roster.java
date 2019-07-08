package shiftman.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A Roster for 1 week (Monday through Sunday)
 * The roster has a list of registered staff and a TreeMap of working days
 * @author Denise Jarry
 *
 */
public class Roster {

	private final String _shopName;
	private final StaffList _registeredStaff = new StaffList();
	private final Map<String, WorkingDay> _WorkingDaysMap = new TreeMap<String, WorkingDay>();

	public Roster(String shopName) {
		_shopName = shopName;
	}

	/**
	 * Creates a WorkingDay object with a start and end time
	 * T object is stored in the _WorkingDaysMap with the string as the key
	 * @param dayOfWeek must match a name value from the enum WeekDays
	 * @param startTime
	 * @param endTime
	 * @throws ShiftManException
	 */
	public void setDayWorkingHours(String dayOfWeek, String startTime, String endTime) throws ShiftManException {

		if(!isAWeekDayName(dayOfWeek)) {
			throw new ShiftManException("Day of week string is invalid");
		}

		if (_WorkingDaysMap.containsKey(dayOfWeek)) {
			throw new ShiftManException("The working hours for that day have already been set");
		}

		WorkingDay day = new WorkingDay(dayOfWeek, startTime, endTime);
		_WorkingDaysMap.put(dayOfWeek, day);	

	}
	

	public void addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers) throws ShiftManException {
		getDay(dayOfWeek).addShift(startTime, endTime, minimumWorkers);
	}

	
	public void addStaffMember(String givenName, String familyName) throws ShiftManException{
		StaffMember staffMember = new StaffMember(givenName, familyName);

		if(_registeredStaff.alreadyContains(staffMember)) {
			throw new ShiftManException("A staff member with this name has already been registered");
		}

		_registeredStaff.add(staffMember);
	}

	/**
	 * Assigns a staff member to the specified shift as either a manager or a worker
	 * The staff member also gets the shift added to their list of shifts
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
	 * @param givenName
	 * @param familyName
	 * @param isManager
	 * @throws ShiftManException
	 */
	public void assignStaff(String dayOfWeek, String startTime, String endTime, String givenName, String familyName, boolean isManager) throws ShiftManException {

		StaffMember staffMember = _registeredStaff.getStaffMember(givenName, familyName);
		Shift shift = getDay(dayOfWeek).getShift(startTime, endTime);

		shift.addStaffMember(staffMember, isManager);
		//If addStaffMember was successful, then the shift can be added to the staff member's list
		staffMember.addShift(shift, isManager);
	}

	public List<String> getRegisteredStaff(){
		_registeredStaff.sort();
		return _registeredStaff.getStringList();
	}

	public List<String> getUnassignedStaff(){
		_registeredStaff.sort();
		return _registeredStaff.getUnassignedStringList();
	}

	public List<String> getShiftsWithoutManagers(){
		List<String> stringList = new ArrayList<>();

		for (WorkingDay day : _WorkingDaysMap.values()) {
			stringList.addAll(day.getShiftsWithoutManagers());
		}

		return stringList;
	}

	public List<String> getUnderstaffedShifts(){
		List<String> stringList = new ArrayList<>();

		for (WorkingDay day : _WorkingDaysMap.values()) {
			stringList.addAll(day.getUnderstaffedShifts());
		}

		return stringList;

	}

	public List<String> getOverstaffedShifts(){
		List<String> stringList = new ArrayList<>();

		for (WorkingDay day : _WorkingDaysMap.values()) {
			stringList.addAll(day.getOverstaffedShifts());
		}

		return stringList;

	}

	public List<String> getRosterForDay(String dayOfWeek) throws ShiftManException{
		List<String> dayRoster = new ArrayList<>(); 
		WorkingDay day;
		
		//Returns an empty string if the day requested has not been set as a day with working hours
		try {
			day = getDay(dayOfWeek);
		}
		catch(ShiftManException e) {
			return dayRoster;
		}

		
		dayRoster = day.getRoster();
		dayRoster.add(0, _shopName);
		return dayRoster;
	}

	public List<String> getRosterForWorker(String givenName, String familyName) throws ShiftManException{
		StaffMember staffMember = _registeredStaff.getStaffMember(givenName, familyName);
		List<String> workerShiftList = staffMember.getWorkedShiftsList();
		if (workerShiftList.isEmpty()) {
			return workerShiftList;
		}
		else {
			workerShiftList.add(0, staffMember.reverseToString());
			return workerShiftList;
		}
	}

	public List<String> getShiftsManagedBy(String givenName, String familyName) throws ShiftManException{
		StaffMember staffMember = _registeredStaff.getStaffMember(givenName, familyName);
		List<String> managerShiftList = staffMember.getManagedShiftsList();
		if (managerShiftList.isEmpty()) {
			return managerShiftList;
		}
		else {
			managerShiftList.add(0, staffMember.reverseToString());
			return managerShiftList;
		}
	}

	private WorkingDay getDay(String dayOfWeek) throws ShiftManException{

		if(!isAWeekDayName(dayOfWeek)) {
			throw new ShiftManException("Day of week string is invalid");
		}
		
		WorkingDay day = _WorkingDaysMap.get(dayOfWeek);
		
		if(day == null) {
			throw new ShiftManException("This day does not have working hours");
		}
		
		return day;		
	}
	
	/**
	 * Checks if the provided string matches a name value in the WeekDays enum
	 * @param dayOfWeek
	 * @return true or false
	 */
	private boolean isAWeekDayName(String dayOfWeek) {

		for (WeekDays day : WeekDays.values()) {
			if (day.getName().equals(dayOfWeek)) {
				return true;
			}
		}
		return false;
	}

}



