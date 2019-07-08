package shiftman.server;

/**
 * Represents a single working shift
 * A shift is a time period
 * @author Denise Jarry
 *
 */
public class Shift extends TimePeriod{
	
	private String _dayOfWeek;
	private int _minimumWorkers;
	private StaffMember _manager = null;
	private final StaffList _shiftWorkers = new StaffList();
	
	public Shift(String dayOfWeek, String startTime, String endTime, String minimumWorkers) throws ShiftManException {
		super(startTime, endTime);
		_dayOfWeek = dayOfWeek;
		
		try {
			_minimumWorkers = Integer.parseInt(minimumWorkers);
		}
		catch (NumberFormatException e) {
			throw new ShiftManException("Minimum workers is not an integer");
		}
	}
	
	public void addStaffMember(StaffMember staffMember, boolean isManager) throws ShiftManException{
		if(isManager) {
			if(_manager != null){
				throw new ShiftManException("This shift already has a manager assigned to it");
			}
			else {
				_manager = staffMember;				
			}
		}
		else {
			if(_shiftWorkers.alreadyContains(staffMember)) {
				throw new ShiftManException("This staff member has already been assigned as a worker for this shift");
			}
			else {
				_shiftWorkers.add(staffMember);
			}	
		}
	}
	
	public boolean hasManager() {
		if(_manager == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Returns the difference between the minimum number of workers and the current number of workers
	 * @return a negative value if the shift is overstaffed, a positive value if understadded, and 0 if the staffing is correct
	 */
	public int getStaffingLevel() {
		return _minimumWorkers - _shiftWorkers.length();
	}
	
	@Override
	public String toString() {
		return _dayOfWeek + super.toString();
	}
	
	/**
	 * A full version of the toString method that returns all associated info of the shift
	 * @return
	 */
	public String toStringFull() {
		String shiftString = "";
		shiftString += this.toString();
		if (_manager == null) {
			shiftString += " [No manager assigned]";
		}
		else {
			shiftString += " Manager:";
			shiftString += _manager.reverseToString();
		}
		_shiftWorkers.sort();
		if (_shiftWorkers.isEmpty()) {
			shiftString += " [No workers assigned]";
		}
		else {
			shiftString +=" [";
			shiftString += _shiftWorkers.toString();
			shiftString +="]";
		}
		
		return shiftString;
	}	
}
