package hms.entity;  // Package declaration for the hms.entity package

//import Built-in Packages
import java.util.Date;

public class MedicalHistory {  // Declaration of the medical History class

	// Fields to represent various attributes of a medical history
	private String Mid;    
	private Date Dateofexamination;   
	private String Diagnosis;
	private String Prescription;

	private Patient patient; //many to one mapping with patient
	private Doctor doctor;   //many to one mapping with doctor
	private Appointment appointment; //many to one mapping with Appointment

	// Default constructor for the MedicalHistory class
	public MedicalHistory() {
		super();
	}
	
	// Constructor with fields for the MedicalHistory class

	public MedicalHistory(String mid, Date dateofexamination, String diagnosis, String prescription, Patient patient,
			Doctor doctor, Appointment appointment) {
		super();
		Mid = mid;
		Dateofexamination = dateofexamination;
		Diagnosis = diagnosis;
		Prescription = prescription;
		this.patient = patient;
		this.doctor = doctor;
		this.appointment = appointment;
	}
	
	// Getter and Setter methods for various attributes of the MedicalHistory class
	
	// Getter method to retrieve the Medical history ID
	public String getMid() {
		return Mid;
	}
    public void setMid(String mid) {
		Mid = mid;
	}
 
    //Getter and setter method to retrieve and set the Patient associated with the medical history
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	// Getter and setter  method to retrieve and set the Diagnosis made during examination
	public String getDiagnosis() {
		return Diagnosis;
	}
	
	public void setDiagnosis(String diagnosis) {
		Diagnosis = diagnosis;
	}
	
	// Getter and setter method to retrieve and set the Prescription given after examination
			public String getPrescription() {
			return Prescription;
		}

		public void setPrescription(String prescription) {
			Prescription = prescription;
		}

	// Getter method to retrieve the Date of examination
		public Date getDateofexamination() {
			return Dateofexamination;
		}
		// Setter method to set the Date of examination
		public void setDateofexamination(Date dateofexamination) {
			Dateofexamination = dateofexamination;
		}

  
	// Getter method to retrieve the Doctor associated with the medical history
	public Doctor getDoctor() {
		return doctor;
	}
	
	// Setter method to set the Doctor associated with the medical history
    public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
 // Getter method to retrieve the Appointment associated with the medical history
    public Appointment getAppointment() {
		return appointment;
	}

 // Setter method to set the Appointment associated with the medical history
    public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
 
    // toString method to represent the MedicalHistory object as a string

	@Override
	public String toString() {
		return "MedicalHistory [Mid=" + Mid + ", Dateofexamination=" + Dateofexamination + ", Diagnosis=" + Diagnosis
				+ ", Prescription=" + Prescription + ", patient=" + patient + ", doctor=" + doctor + ", appointment="
				+ appointment + "]";
	}
	

}
