package hms.dao;// user defined package
//import user defined package
import hms.authentication.Validations;
import hms.entity.Appointment;
import hms.entity.Doctor;
import hms.entity.MedicalHistory;
import hms.util.HibernateUtil;
//import Built-in Packages
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;
// class declaration
public class DoctorDao {

	//Method to add the doctor 
	public void addDoctor(Scanner scanner) {
		Doctor doctor1 = new Doctor();
		// auto-generated doctor id
		int count = getDoctorcount();
		String did = "D0" + (++count);
		doctor1.setDid(did);

		System.out.print("Enter First Name : ");					 	
		doctor1.setDfirstname(scanner.nextLine());

		System.out.print("Enter Last Name : ");
		doctor1.setDlastname(scanner.nextLine());

		String gender = Validations.validateGender(scanner);
		doctor1.setDgender(gender);

		System.out.print("Enter Specialization of the Doctor : ");
		doctor1.setSpecialization(scanner.nextLine());

		System.out.print("Enter your email address: ");
		String email;
		do {
		    email = scanner.nextLine();
		    if (Validations.isValidEmail(email)) {
		        doctor1.setEmail(email); // Set the email to doctor
		    } else {
		        System.out.println("Invalid email address. Please try again.");
		        System.out.print("Re-enter: ");
		    }
		} while (!Validations.isValidEmail(email));
		
		saveDoctor(doctor1);// calling the save doctor method
		System.out.println("Doctor Details Added successfully  ");
		System.out.print(" ");
		getDoctordetailsByDid(doctor1.getDid());	// print all the updated details
	}

	// method to save the doctor to the database
	public void saveDoctor(Doctor doctor)
	{	// transaction is null
		Transaction transaction = null;	

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Try-with-resources block for automatically closing the session

			// start a transaction
			transaction = session.beginTransaction();

			// save the doctor object
			session.persist(doctor);			

			// commit transaction
			transaction.commit();
		} catch (Exception e) { // catch bloock to print the exception
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	// method to print all the doctor details
	public void printAllDoctors() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		// List of all the Doctors
		List<Doctor> doctors = session.createQuery("from Doctor", Doctor.class).list();// Retrieve all doctors
		// doctors is not null and not empty
		if (doctors != null && !doctors.isEmpty()) {
			for (Doctor doctor : doctors) {
				System.out.println();
				getDoctordetailsByDid(doctor.getDid()); // Print each doctor
			}
		} else {
			System.out.println("No Doctors found.");
		}
	}

	// Method to Update Doctor 

	public  void updateDoctorDetails(String did,Scanner sc) {
		// open the session from session factory		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// begin the transaction
			Transaction transaction = session.beginTransaction();
			// Fetch the Doctor details by ID
			Doctor doctor = session.get(Doctor.class, did);
			if (doctor == null) {
				System.out.println("Doctor not found with id : "+did);
				return;
			}

			System.out.println();
			System.out.println("Current Doctor Details:");
			getDoctordetailsByDid(doctor.getDid());// print current details

			System.out.print("Enter New Email address : ");
			String email = sc.nextLine();

			while (!email.isEmpty()) {
				if (Validations.isValidEmail(email)) {
					doctor.setEmail(email);
					break;
				} else {
					System.out.println("Invalid email address. Please try again.");
					System.out.print("Re-enter Email : ");
					email = sc.nextLine();
				}
			}
			// update the doctor to database
			session.merge(doctor);
			// commit the transaction
			transaction.commit();

			System.out.println("Doctor details updated successfully!");
			System.out.println();
			getDoctordetailsByDid(doctor.getDid()); // print updated details

		} catch (Exception e) { // catch bloock to print the exception
			e.printStackTrace();
		}
	}
	//method to search the details by ID(primary key)
	// Doctor Module - case 1
	public void getDoctordetailsByDid(String Did) {
		// open the session from session factory
		Session session = HibernateUtil.getSessionFactory().openSession(); 			
		// Fetch the Doctor details by ID
		Doctor doctor = session.get(Doctor.class, Did);
		// doctor is not null
		if (doctor != null) {
			System.out.println("Doctor Id     : " + doctor.getDid());
			System.out.println("Doctor Name   : " + doctor.getDfirstname()+" "+doctor.getDlastname());
			System.out.println("Gender        : " + doctor.getDgender());
			System.out.println("Specialization: " + doctor.getSpecialization());
			System.out.println("Email         : " + doctor.getEmail());
			System.out.println("---------------------------------------");

		} else {
			System.out.println("Doctor with "+ Did+" not found.");
		}						
	}

	// Doctor Module - case 2
	// method to get the appointment details of doctor
	public void getAppointmentByDid(String did) {
		// open the session from session factory
		Session session = HibernateUtil.getSessionFactory().openSession();	      
		// Fetch the Doctor details by ID
		Doctor doctor = session.get(Doctor.class, did);
		// doctor obj is not null
		if (doctor != null) {
			if (!doctor.getAppointments().isEmpty()) {
				// Appointments are not not empty
				for (Appointment appointment : doctor.getAppointments()) {
//					if ("confirmed".equals(appointment.getStatus())) { // Check if status is confirmed
						System.out.println("Appointment ID     : " + appointment.getAppid());
						System.out.println("Patient ID         : " + appointment.getPatient().getPid());
						System.out.println("Patient Name       : " + appointment.getPatient().getPfirstname());
						System.out.println("Appointment Date   : " + appointment.getAppointmentDate());
						System.out.println("Appointment Time   : " + appointment.getAppointmentTime());

						// Iterate through associated medical histories
						for (MedicalHistory medicalHistory : appointment.getMedicalHistories()) {
							System.out.println("Diagnosis          : " + medicalHistory.getDiagnosis());
							System.out.println("Prescription       : " + medicalHistory.getPrescription());						
						}
						System.out.println("Appointment Status : " + appointment.getStatus());
						System.out.println("------------------------------------------------");
//					}
				}
			}else
				System.out.println("Doctor with ID " + did + "  has No appointments.");

		} else {
			System.out.println("Doctor with "+ did+" not found.");
		}	       
	}

	// method to get the count of the medical histories
	public int getDoctorcount() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Doctor", Doctor.class).list().size();

		}
	}	
	public boolean TestsaveDoctor(Doctor doctor)
	{	// transaction is null
		Transaction transaction = null;	

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Try-with-resources block for automatically closing the session

			// start a transaction
			transaction = session.beginTransaction();

			// save the doctor object
			session.persist(doctor);			

			// commit transaction
			transaction.commit();
		} catch (Exception e) { // catch bloock to print the exception
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return(true);
	}
	
}


