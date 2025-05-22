package hms.entity;   // Package declaration for the hms.entity package

import java.util.Set;

public class Patient {  // Declaration of the Patient class
	
	private String Pid;
	private String Pfirstname;
	private String Plastname;
	private int age;
	private String Pgender;
	private String BloodGroup;
	private String Paddress;
	private String phoneNumber;
	
	private Set<Appointment> appointments;  // one to many mapping with appointment
	private Set<MedicalHistory> medhistories; // one to many mapping with medical hishory
    private Doctor doctor;   // many to one mapping with doctor
	
	//Default constructor
	public Patient() {
		super();
	}
	
	//constructor using fields
		
	public Patient(String pid, String pfirstname, String plastname, int age, String pgender, String bloodGroup,
			String paddress, String phoneNumber, Set<Appointment> appointments, Set<MedicalHistory> medhistories,
			Doctor doctor) {
		super();
		Pid = pid;
		Pfirstname = pfirstname;
		Plastname = plastname;
		this.age = age;
		Pgender = pgender;
		BloodGroup = bloodGroup;
		Paddress = paddress;
		this.phoneNumber = phoneNumber;
		this.appointments = appointments;
		this.medhistories = medhistories;
		this.doctor = doctor;
	}

	// Getter and setter methods for the Patient ID (Pid)
	public String getPid() {
		return Pid;
	}
	public void setPid(String pid) {
		Pid = pid;
	}
	// Getter and setter methods for the Patient's first name
	public String getPfirstname() {
		return Pfirstname;
	}
	public void setPfirstname(String pfirstname) {
		Pfirstname = pfirstname;
	}
	
	// Getter and setter methods for the Patient's last name
	public String getPlastname() {
		return Plastname;
	}
	public void setPlastname(String plastname) {
		Plastname = plastname;
	}
	
	// Getter and setter methods for the Patient's age
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	// Getter and setter methods for the Patient's gender
	public String getPgender() {
		return Pgender;
	}
	public void setPgender(String pgender) {
		Pgender = pgender;
	}
	
	// Getter and setter methods for the Patient's blood group
	public String getBloodGroup() {
		return BloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		BloodGroup = bloodGroup;
	}

	// Getter and setter methods for the Patient's address
	public String getPaddress() {
		return Paddress;
	}
	public void setPaddress(String paddress) {
		Paddress = paddress;
	}
	
	// Getter and setter methods for the Patient's phone number
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	// Getter and setter methods for the Doctor assigned to the patient
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	// Getter method to retrieve the appointments scheduled for the patient
	public Set<Appointment> getAppointments() {
		return appointments;
	}
	// Setter method to set the Appointment of the patient
	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	// Getter method to retrieve the medical histories associated with the patient
	public Set<MedicalHistory> getMedhistories() {
		return medhistories;
	}
	
	// Setter method to set the medical histories of the patient
	public void setMedhistories(Set<MedicalHistory> medhistories) {
		this.medhistories = medhistories;
	}

	// toString method to represent object as string
	@Override
	public String toString() {
		return "Patient [Pid=" + Pid + ", Pfirstname=" + Pfirstname + ", Plastname=" + Plastname + ", age=" + age
				+ ", Pgender=" + Pgender + ", BloodGroup=" + BloodGroup + ", Paddress=" + Paddress + ", phoneNumber="
				+ phoneNumber + ", appointments=" + appointments + ", medhistories=" + medhistories + ", doctor="
				+ doctor + "]";
	}

  }
