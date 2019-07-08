# Shift_Manager

# SOFTENG 251 Assignment 2

## Domain Model Concepts and Relationships

(Bold highlights the relationship between each concept)

* Server
	* A server for the Shift Manager System
	* Has one **roster** at a time
* Roster 
	* Has the name of the shop to which the roster applies
	* Has 7 **days** on which the shop may or may not be **open**
	* Has **staff members**
* Working Day
	* A day of the week that the shop is open
	* Has a name (Monday, Tuesday etc.)
	* Has working hours **(is a time period)**
	* Has **shifts** scheduled for that day
* Shift
	* Has a start time and end time **(is a time period)** (which must be within the working hours for the working day on which the shift is scheduled)
	* Can have up to 1 **staff member** assigned as a manager
	* Can have any number of **staff members** assigned as workers
	* Has a number of workers required
* Time Period
	* Has a start time and end time
* Staff member
	* Is registered (given that they exist as a Staff Member in the system)
	* Has a family name and a given name
	* Can have **shifts** they have been assigned to as a manager
	* Can have **shifts** they have been assigned to as a worker

## My Object-Orientated Design

Each of these above concepts is represented by a class and the bold text indicates how the objects created from those classes interact.

### Scenario to demonstrate Object Creation and Interaction in my design: 

*“A roster is created for Bunnings. The working hours for Monday are set from 6am to 4pm, and a shift is scheduled from 12:00 to 14:00. John Doe is registered and assigned to that shift as a worker. The user then tries to schedule a shift from 13:00 to 16:00 on Monday.”*

A roster object is created with a name field Bunnings.

Setting the working hours for Monday creates a WorkingDay object with the name field ‘Monday’. This WorkingDay is a time period, so a TimePeriod object is also created which stores the startTime 06:00 and endTime 16:00 as fields. This object is stored in the TreeMap in Roster with the key ‘Monday’.

Scheduling a shift creates a Shift object. A shift is a time period, so a TimePeriod object is also created which stores the startTime 12:00 and endTime 14:00. This shift is stored in the Monday WorkingDay object’s ShiftList (an encapsulated collection). 

Registering John Doe creates a StaffMember object with the name fields John and Doe. This object is stored in the Roster’s StaffList (an encapsulated collection). Assigning John Doe to the shift means the John Doe StaffMember object is added to the shift’s StaffList, and the shift is also added to John Doe object’s ShiftList.

Another shift object is created alongside a TimePeriod object with startTime 13:00 and endTime 16:00. However, the addShift method in the WorkingDay class throws an exception as this overlaps with the existing shift. This shift is hence not added to Monday’s ShiftList.

### Some Design Decision Justifications

A WorkingDay object is only created when working hours are set. This is because a WorkingDay object is a time period, and a time period is defined by a start and end time. If a day doesn’t have working hours, then it is not a time period. Also, if it doesn’t have working hours then the shop is not open on this day, and shifts cannot be scheduled on it. Therefore, the system doesn’t need a WorkingDay object to interact with until the user sets working hours. 

StaffMember objects store the Shift objects to which they are assigned. Shift objects also store the StaffMember objects that are assigned to the Shift. I did not think it would make sense for the getRosterForWorker method to need to scan through every Shift in every WorkingDay to find where this StaffMember had been assigned. Instead, this interdependence means that each StaffMember knows which Shifts they are assigned to, making this method more efficient.

There are two classes ShiftList and ShiftListWorkingDay which act as encapsulated collections. The former is used by the StaffMember class as the interaction this class does with the list is minimal: add and getStringList. The latter is used by the WorkingDay class as many more methods are required. It made sense to separate them.

There is only one StaffList encapsulated collection class. While the Roster class and Shift class interact with the list differently, there is a lot more of the same methods used by each. It made sense to leave this as one class.

## Problems Handled and Exception Use

* Input is null or empty
* A time input doesn’t match the required format of HH:mm
	* checkPatternMatch method in TimePeriod class throws an exception
* The start time is after or the same as the end time, or either time is 00:00
	* setDateFields method in TimePeriod class throws an exception
* dayOfWeek string is incorrect
	* getDay method in the Roster class throws an exception if the string isn’t a code for the hashmap in the WeekDays enum
* A Shift overlaps with another or is not within the day’s working hours
	* addShift method in the Day class throws an exception in both of these cases
* A shift already has a manager
	* addStaffMember method in the Shift class throws an exception 
* The worker trying to be assigned has already been assigned to that shift
	* addStaffMember method in the Shift class throws an exception 
* The user tries to schedule a shift for a day that the shop isn’t open
	* getDay method in the Roster class throws an exception.

The exception thrown is ShiftManException, which extends Exception. All exceptions are caught in the ShiftManServer class, and the corresponding error message is displayed to the user.
