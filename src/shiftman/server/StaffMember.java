package shiftman.server;

import java.util.List;

/**
 * Represents a staff member
 * @author Denise Jarry
 *
 */
public class StaffMember implements Comparable<StaffMember> {

	private final String _givenName;
	private final String _familyName;
	private final ShiftList _shiftsManaged = new ShiftList();
	private final ShiftList _shiftsWorked = new ShiftList();
	private boolean _isAssigned;


	public StaffMember(String givenName, String familyName) {
		_givenName = givenName;
		_familyName = familyName;
		_isAssigned = false;
	}

	/**
	 * Records a shift that the staff member has been assigned to
	 * @param shift
	 * @param isManager
	 */
	public void addShift(Shift shift, boolean isManager) {
		if (isManager) {
			_shiftsManaged.add(shift);
		}
		else {
			_shiftsWorked.add(shift);
			
		}
		_isAssigned = true;
	}

	/**
	 * Checks if this staff member has the specified name
	 * @param givenName
	 * @param familyName
	 * @return true or false
	 */
	public boolean hasName(String givenName, String familyName) {
		return this._givenName.equalsIgnoreCase(givenName) && this._familyName.equalsIgnoreCase(familyName);	
	}
	
	public boolean isAssigned() {
		return _isAssigned;
	}
	
	public List<String> getWorkedShiftsList(){
		return _shiftsWorked.getStringList();
	}
	
	public List<String> getManagedShiftsList(){
		return _shiftsManaged.getStringList();
	}

	@Override
	public boolean equals(Object object) {
		StaffMember staffMember = (StaffMember)object;
		return this._givenName.equalsIgnoreCase(staffMember._givenName) && this._familyName.equalsIgnoreCase(staffMember._familyName);		
	}

	@Override
	public String toString() {
		return _givenName + " " + _familyName;
	}
	
	public String reverseToString() {
		return _familyName + ", " + _givenName;
		
	}
	
	@Override
	public int compareTo(StaffMember secondStaffMember) {
		return this._familyName.compareTo(secondStaffMember._familyName);
	}


}
