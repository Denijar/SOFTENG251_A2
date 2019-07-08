package shiftman.server;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a single weekday on which shifts can be scheduled.
 * A day is a time period.
 * A day has a list of shifts that have been scheduled for that day
 * @author Denise Jarry
 *
 */
public class WorkingDay extends TimePeriod {

	private final String _dayName;
	private final ShiftListWorkingDay _shifts = new ShiftListWorkingDay();

	public WorkingDay(String dayName, String startTime, String endTime) throws ShiftManException {
		super(startTime, endTime);
		_dayName = dayName;
	}

	/**
	 * Adds a shift to the list of shifts after checking that it is valid (non-overlapping and within working hours)
	 * @param startTime
	 * @param endTime
	 * @param minimumWorkers
	 * @throws ShiftManException
	 */
	public void addShift(String startTime, String endTime, String minimumWorkers) throws ShiftManException{
		Shift shift = new Shift(_dayName, startTime, endTime, minimumWorkers);

		//Check the shift is within the working hours (that is the Day's time period encloses the Shift's)
		if (!timePeriodEncloses(shift)) {
			throw new ShiftManException("The shift is not within the day's working hours");
		}
		
		//Check that this shift doesn't overlap with an existing one
		if(_shifts.shiftOverlapsWithExisting(shift)) {
			throw new ShiftManException("This shift overlaps with an existing shift");
		}

		_shifts.add(shift);
	}

	/**
	 * Returns the shift object identified by its start and end time
	 * @param startTime
	 * @param endTime
	 * @return a Shift object
	 * @throws ShiftManException if this shift is not found
	 */
	public Shift getShift(String startTime, String endTime) throws ShiftManException {
		return _shifts.getShift(startTime, endTime);
	}
	
	/**
	 * Returns the shifts that don't have a StaffMember assigned as a Manager
	 * Each entry is the shift with string format dayofweek[starttime-endtime]
	 * @return a list where each entry is a shift with string format dayofweek[starttime-endtime]
	 */
	public List<String> getShiftsWithoutManagers(){
		_shifts.sort();
		return _shifts.getShiftsWithoutManagersStringList();
	}
	
	/**
	 * Returns the shifts where the number of workers assigned is less that the specified minimum workers needed
	 * @return a list where each entry is a shift with string format dayofweek[starttime-endtime]
	 */
	public List<String> getUnderstaffedShifts(){
		_shifts.sort();
		return _shifts.getUnderstaffedShiftsStringList();
	}
	
	/**
	 * Similar to {@link #getUnderstaffedShifts()} but returns overstaffed shifts
	 */
	public List<String> getOverstaffedShifts(){
		_shifts.sort();
		return _shifts.getOverstaffedShiftsStringList();
	}
	
	/**
	 * Returns a string version of the roster
	 * @return
	 */
	public List<String> getRoster(){
		List<String> rosterStringList = new ArrayList<>();
		//Return empty list if there are no shifts on this day
		if (_shifts.isEmpty()) {
			return rosterStringList;
		}
		else {
			//Add the day info first (name and working hours), then sort shifts and add them 
			rosterStringList.add(this.toString());
			_shifts.sort();
			rosterStringList.addAll(_shifts.getStringList());
		}
		
		return rosterStringList;
	}
	
	@Override
	public String toString() {
		return _dayName + " " + super.toStringNoBracket();
	}
}