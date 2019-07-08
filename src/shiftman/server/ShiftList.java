package shiftman.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulated collection - a list of shifts
 * Only provides the basic functionality of adding shifts and returning a string list of the shifts
 * @author Denise Jarry
 *
 */
public class ShiftList {
	
	private final List<Shift> _shifts = new ArrayList<>();
	
	public void add(Shift shift) {
		_shifts.add(shift);
	}
	
	public List<String> getStringList(){
		List<String> stringList = new ArrayList<>();
		for (Shift shift : _shifts) {
			stringList.add(shift.toString());
		}
		return stringList;	
	}

}
