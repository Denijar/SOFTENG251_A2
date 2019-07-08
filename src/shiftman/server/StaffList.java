package shiftman.server;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Encapsulated collection - a list of staff
 * @author Denise Jarry
 *
 */
public class StaffList {

	private final List<StaffMember> _staff = new ArrayList<>();

	/**
	 * Checks if the specified staffMember has already been registered
	 * @param staffMember
	 * @return
	 */
	public boolean alreadyContains(StaffMember staffMember) {

		for (StaffMember existingStaffMember : _staff) {
			if(staffMember.equals(existingStaffMember)) {
				return true;
			}
		}

		return false;
	}

	public void add(StaffMember staffMember) {
		_staff.add(staffMember);	
	}

	public StaffMember getStaffMember(String givenName, String familyName) throws ShiftManException {

		for (StaffMember existingStaffMember : _staff) {
			if(existingStaffMember.hasName(givenName, familyName)) {
				return existingStaffMember;
			}
		}
		throw new ShiftManException("Staff member not found");
	}

	/**
	 * Sort the staffList in alphabetical first name order
	 */
	public void sort(){
		Collections.sort(_staff);		
	}

	/**
	 * Returns a list of all staff members in string format
	 * @return
	 */
	public List<String> getStringList(){
		List<String> stringList = new ArrayList<>();
		for (StaffMember staffMember : _staff) {
			stringList.add(staffMember.toString());
		}
		return stringList;
	}

	/**
	 * Returns a list of all staff members that have not been assigned to a shift in string format
	 * A staff member counts as assigned if they are a manager or a worker for at least 1 shift
	 * @return
	 */
	public List<String> getUnassignedStringList(){
		List<String> unassignedList = new ArrayList<>();
		for (StaffMember staffMember : _staff) {
			if (!staffMember.isAssigned()){
				unassignedList.add(staffMember.toString());
			}
		}
		return unassignedList;
	}	

	public boolean isEmpty() {
		return _staff.isEmpty();
	}

	public int length() {
		return _staff.size();
	}

	@Override
	public String toString() {
		String string = "";
		for (StaffMember staffMember : _staff) {
			if (string.length() != 0) {
		        string += ", ";
		    }
			string += staffMember.toString();
		}
		return string;
	}
}