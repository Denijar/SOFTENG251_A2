package shiftman.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Represents a period in time (that starts at one time and ends at another)
 * @author Denise Jarry
 *
 */
public class TimePeriod implements Comparable<TimePeriod> {

	//Static fields because every TimePeriod object will refer to these same patterns/formats
	private static final Pattern TIME_PATTERN = Pattern.compile("\\d{2}:\\d{2}");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
	
	static {
		TIME_FORMAT.setLenient(false);
	}

	private String _startTime;
	private String _endTime;
	private Date _startTimeDate;
	private Date _endTimeDate;

	//Default constructor
	public TimePeriod() {
	}

	public TimePeriod(String startTime, String endTime) throws ShiftManException {
		
		checkPatternMatch(startTime, endTime);
		setDateFields(startTime, endTime);

		_startTime = startTime;
		_endTime = endTime;
	}

	/**
	 * Checks if the string time inputs match the required format of XX:XX where X is a digit
	 * @param startTime
	 * @param endTime
	 * @throws ShiftManException
	 */
	private void checkPatternMatch(String startTime, String endTime) throws ShiftManException {

		if (!TIME_PATTERN.matcher(startTime).matches() | !TIME_PATTERN.matcher(endTime).matches()){
			throw new ShiftManException("Time does not match required format of XX:XX");
		}
	}
	
	/**
	 * Sets the object fields that are of type Date after parsing the string inputs to Date objects
	 * @param startTime
	 * @param endTime
	 * @throws ShiftManException
	 */
	private void setDateFields(String startTime, String endTime) throws ShiftManException{
		
		Date startTimeDate;
		Date endTimeDate;
		
		//00:00 for the endTime is also invalid, but this will be caught when attempting to parse
		if (startTime.equals("00:00")){
			throw new ShiftManException ("Invalid start time");
		}
		
		//An exception is thrown if the parsing fails, which can only occur if the times provided are invalid
		try {
			startTimeDate = TIME_FORMAT.parse(startTime);
		} catch (ParseException e) {
			throw new ShiftManException ("Invalid start time");
		}
		
		try {
			endTimeDate = TIME_FORMAT.parse(endTime);
		} catch (ParseException e) {
			throw new ShiftManException ("Invalid end time");
		}
		
		if (startTimeDate.compareTo(endTimeDate) >= 0) {
			throw new ShiftManException ("The start time is after or equal to the end time");
		}
		
		//Must be valid if this point is reached, can set fields
		_startTimeDate = startTimeDate;
		_endTimeDate = endTimeDate;
	}
	
	/**
	 * Checks if this time period encloses the parameter time period
	 * Note that this enclosing is inclusive (as in if both time periods start and end at the same time, then it is enclosed)
	 * @param enclosed
	 * @return
	 */
	public boolean timePeriodEncloses(TimePeriod enclosed){
		
		if(this._startTimeDate.before(enclosed._startTimeDate) || this._startTimeDate.equals(enclosed._startTimeDate)) {
			if(this._endTimeDate.after(enclosed._endTimeDate) || this._endTimeDate.equals(enclosed._endTimeDate)) {
				return true;
			}
		}
		return false;			
	}
	
	/**
	 * Checks if this time period overlaps with the parameter time period
	 * Note that this overlapping is exclusive (as in if one time period starts when the other finishes, they overlap)
	 * @param other
	 * @return
	 */
	public boolean timePeriodOverlaps(TimePeriod other) {
		return !this._startTimeDate.after(other._endTimeDate) && !other._startTimeDate.after(this._endTimeDate);
	}
	
	public boolean hasSameTime(String startTime, String endTime) throws ShiftManException {
		checkPatternMatch(startTime, endTime);
		
		if(_startTime.equals(startTime) && _endTime.equals(endTime)) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public int compareTo(TimePeriod secondTimePeriod) {
		return this._startTimeDate.compareTo(secondTimePeriod._startTimeDate);
	}
	
	public String toStringNoBracket() {
		return _startTime + "-" + _endTime;
	}
	
	@Override
	public String toString() {
		return "[" + _startTime + "-" + _endTime + "]";
	}

}
