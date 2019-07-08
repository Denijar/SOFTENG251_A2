package shiftman.server;

public enum WeekDays {
	MONDAY("Monday"),
	TUESDAY("Tuesday"),
	WEDNESDAY("Wednesday"),
	THURSDAY("Thursday"),
	FRIDAY("Friday"),
	SATURDAY("Saturday"),
	SUNDAY("Sunday");
	
	private final String _name;
	
	WeekDays(String name){
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
	
}
