package hms.entity; // Package declaration for the hms.entity package

import java.util.Set;

public class Doctor {  // Declaration of the Doctor class
	
	// Fields to represent various attributes of a doctor
	private String Did;	
	private String Dfirstname;
	private String Dlastname;	
	private String Dgender;
	private String specialization;
	private String email;
	
 private Set<Patient> patients;  //one to many mapping with patient
 private Set<Appointment> appointments; //one to many mapping with Appointments
 private Set<MedicalHistory> medhistories; //one to many mapping with medical history
 
	//Default constructor
	public Doctor() {
		super();
	}
  //constructor using fields
	public Doctor(String did, String dfirstname, String dlastname, String dgender, String specialization, String email,
			Set<Patient> patients, Set<Appointment> appointments, Set<MedicalHistory> medhistories) {
		super();
		Did = did;
		Dfirstname = dfirstname;
		Dlastname = dlastname;
		Dgender = dgender;
		this.specialization = specialization;
		this.email = email;
		this.patients = patients;
		this.appointments = appointments;
		this.medhistories = medhistories;
	}
  
	
	// Getter and Setter methods for attributes of the Doctor class
	
	// Getter and setter  method to retrieve the Doctor ID
	public String getDid() {
		return Did;
	}
	public void setDid(String did) {
		Did = did;
	}
	
	// Getter and setter method to retrieve the Doctor's first name
	public String getDfirstname() {
		return Dfirstname;
	}
	public void setDfirstname(String dfirstname) {
		Dfirstname = dfirstname;
	}
	
	// Getter and setter  method to retrieve the Doctor's last name
	public String getDlastname() {
		return Dlastname;
	}
	public void setDlastname(String dlastname) {
		Dlastname = dlastname;
	}
	
	// Getter and setter  method to retrieve the Doctor's gender
	public String getDgender() {
		return Dgender;
	}
	public void setDgender(String dgender) {
		Dgender = dgender;
	}
	
	// Getter and setter  method to retrieve the Doctor's specialization
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	
	// Getter and setter  method to retrieve the Doctor's email address
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	// Getter method to retrieve the set of patients assigned to the doctor
	public Set<Patient> getPatients() {
		return patients;
	}
	// Setter method to set the set of patients assigned to the doctor
	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}
	// Getter method to retrieve the set of appointments assigned to the doctor
	public Set<Appointment> getAppointments() {
		return appointments;
	}
	// Setter method to set the set of appointments assigned to the doctor
	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	// Getter method to retrieve the set of medical histories associated with the doctor
		public Set<MedicalHistory> getMedhistories() {
			return medhistories;
		}
		// Setter method to set the set of medical histories associated with the doctor
		public void setMedhistories(Set<MedicalHistory> medhistories) {
			this.medhistories = medhistories;
		}
		
	// toString method to represent object as string
	@Override
	public String toString() {
		return "Doctor [Did=" + Did + ", Dfirstname=" + Dfirstname + ", Dlastname=" + Dlastname + ", Dgender=" + Dgender
				+ ", specialization=" + specialization + ", email=" + email + ", patients=" + patients
				+ ", appointments=" + appointments + ", medhistories=" + medhistories + "]";
	}
		
}
