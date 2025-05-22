package hms.entity;  // Package declaration for the hms.entity package
import java.util.*;

public class Appointment {  // Declaration of the Appointment class
	
	// Fields to represent various attributes of an appointment
	private String Appid;
	private Date AppointmentDate;
	private String AppointmentTime;	
	private String Status;
	
	private Patient patient;	//many to one mapping with patient 		
	private Doctor doctor;      //many to one mapping with Doctor
	private Set<MedicalHistory> medicalHistories; //one to many mapping with Medical history
	
	// Default constructor for the Appointment class
	public Appointment() {
		super();
	}
	
	// Constructor with fields for the Appointment class
 public Appointment(String appid, Date appointmentDate, String appointmentTime, String status, Patient patient,
			Doctor doctor, Set<MedicalHistory> medicalHistories) {
		super();
		Appid = appid;
		AppointmentDate = appointmentDate;
		AppointmentTime = appointmentTime;
		Status = status;
		this.patient = patient;
		this.doctor = doctor;
		this.medicalHistories = medicalHistories;
	}
		
//Getter and Setter methods for various attributes of the Appointment class
 
//Getter and setter method to retrieve the Appointment ID
	public String getAppid() {
		return Appid;
	}
	
    public void setAppid(String appid) {
		Appid = appid;
	}
    
 // Getter and setter method to retrieve the Date of the appointment
	public Date getAppointmentDate() {
		return AppointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		AppointmentDate = appointmentDate;
	}
	
	// Getter and setter method to retrieve the Time of the appointment
	public String getAppointmentTime() {
		return AppointmentTime;
	}
	public void setAppointmentTime(String appointmentTime) {
		AppointmentTime = appointmentTime;
	}
	
	// Getter and setter method to retrieve the Status of the appointment
	public String getStatus() {
		return Status;
	}
    public void setStatus(String status) {
		Status = status;
	}
	
	// Getter method to retrieve the Patient associated with the appointment
	public Patient getPatient() {
		return patient;
	}
	// Setter method to set the Patient associated with the appointment
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	// Getter method to retrieve the Doctor associated with the appointment
	public Doctor getDoctor() {
		return doctor;
	}
	// Setter method to set the Doctor associated with the appointment
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	// Getter method to retrieve the set of medical histories related to the appointment
	public Set<MedicalHistory> getMedicalHistories() {
		return medicalHistories;
	}
	// Setter method to set the set of medical histories related to the appointment
	public void setMedicalHistories(Set<MedicalHistory> medicalHistories) {
		this.medicalHistories = medicalHistories;
	}

	// toString method to represent object as string
	@Override
	public String toString() {
		return "Appointment [Appid=" + Appid + ", AppointmentDate=" + AppointmentDate + ", AppointmentTime="
				+ AppointmentTime + ", Status=" + Status + ", patient=" + patient + ", doctor=" + doctor
				+ ", medicalHistories=" + medicalHistories + "]";
	}
	
	}
