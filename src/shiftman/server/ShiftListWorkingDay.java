package shiftman.server;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Encapsulated collection - a list of shifts
 * Has extra methods that are only needed by the Day class
 * @author Denise Jarry
 *
 */
public class ShiftListWorkingDay {
	
	private final List<Shift> _shifts = new ArrayList<>();
	
	public boolean shiftOverlapsWithExisting(Shift shift) {
		
		for (Shift existingShift : _shifts) {
			if(existingShift.timePeriodOverlaps(shift)) {
				return true;
			}
		}
		return false;
	}
	
	public List<String> getStringList(){
		List<String> stringList = new ArrayList<>();
		for (Shift shift : _shifts) {
			stringList.add(shift.toStringFull());
		}
		return stringList;	
	}
	
	public List<String> getShiftsWithoutManagersStringList(){
		List<String> noManagersList = new ArrayList<>();
		for (Shift shift : _shifts) {
			if (!shift.hasManager()){
				noManagersList.add(shift.toString());
			}
		}
		return noManagersList;
	}
	
	public List<String> getUnderstaffedShiftsStringList(){
		List<String> underStaffedList = new ArrayList<>();
		for (Shift shift : _shifts) {
			if (shift.getStaffingLevel() > 0){
				underStaffedList.add(shift.toString());
			}
		}
		return underStaffedList;
	}
	
	public List<String> getOverstaffedShiftsStringList(){
		List<String> underStaffedList = new ArrayList<>();
		for (Shift shift : _shifts) {
			if (shift.getStaffingLevel() < 0){
				underStaffedList.add(shift.toString());
			}
		}
		return underStaffedList;
	}
	
	public Shift getShift(String startTime, String endTime) throws ShiftManException {
		
		for (Shift existingShift : _shifts) {
			if(existingShift.hasSameTime(startTime, endTime)) {
				return existingShift;
			}
		}
		
		throw new ShiftManException("This shift does not exist");
	}
	
	public void add(Shift shift) {
		_shifts.add(shift);
	}
	
	public void sort() {
		Collections.sort(_shifts);
	}
	
	public boolean isEmpty() {
		return _shifts.isEmpty();
	}
}
