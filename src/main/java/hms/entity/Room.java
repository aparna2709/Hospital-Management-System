package hms.entity;  // Package declaration for the hms.entity package

public class Room {  // Declaration of the Room class

	// Fields to represent various attributes of a room
	private String P_id;	   
	private String Rno;	   
    private String Roomtype;	   
	private String Period;
	private Patient patient; //one to one mapping with patient
	
	// Default constructor for the Room class
	public Room() {

	}
	// Constructor with parameters for the Room class
	public Room(String p_id, String rno, Patient patient, String roomtype, String period) {
		super();
		P_id = p_id;
		Rno = rno;
		this.patient = patient;
		Roomtype = roomtype;
		Period = period;
	}
	
	// Getter and setter methods for various attributes of the Room class
	
	
	// Getter and setter method to retrieve the Patient ID associated with the room
	public String getP_id() {
		return P_id;
	}
	public void setP_id(String p_id) {
		P_id = p_id;
	}
	
	// Getter method to retrieve the room number
	public String getRno() {
		return Rno;
	}
	// Setter method to set the room number
	public void setRno(String rno) {
		Rno = rno;
	}
	
	// Getter method to retrieve the Patient assigned to the room
	public Patient getPatient() {
		return patient;
	}
	// Setter method to set the Patient assigned to the room
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	// Getter method to retrieve the type of room
	public String getRoomtype() {
		return Roomtype;
	}
	// Setter method to set the type of room
	public void setRoomtype(String roomtype) {
		Roomtype = roomtype;
	}
	// Getter method to retrieve the period of stay in the room
	public String getPeriod() {
		return Period;
	}
	// Setter method to set the period of stay in the room
	public void setPeriod(String period) {
		Period = period;
	}
	// toString method to represent the Room object as a string
	@Override
	public String toString() {
		return "Room [P_id=" + P_id + ", Rno=" + Rno + ", patient=" + patient + ", Roomtype=" + Roomtype + ", Period="
				+ Period + "]";
	}
}
