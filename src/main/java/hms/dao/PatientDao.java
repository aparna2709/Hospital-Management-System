package hms.dao;
import hms.authentication.Validations;
import hms.entity.Patient; // Importing the Patient entity class

import hms.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatientDao {

	//Ansi colours
		public static final String ANSI_GREEN = "\u001B[32m";
		public static final String ANSI_BLUE = "\u001B[34m";
		public static final String ANSI_PURPLE = "\u001B[35m";
	    public static final String ANSI_BLACK = "\u001B[30m";
		public static final String ANSI_RED = "\u001B[31m";
		
	Scanner sc = new Scanner(System.in);
	Patient patient = new Patient();

	// Method to save a patient
	public void savePatient(Patient patient) {

		Transaction transaction = null; 

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Try-with-resources block for automatically closing the session

			// Start a transaction
			transaction = session.beginTransaction();

			// Save the patient object
			session.persist(patient);

			// Commit transaction close
			transaction.commit();									
		} 
		catch (Exception f) { 
			if (transaction != null) {
				transaction.rollback(); 
			}
			f.printStackTrace(); 
		}
	}


	// Method to save(insert) a patient
	public void addPatient(Scanner scanner) {

		Patient patient = new Patient();
		int count = getPatientcount();
		String pid = "P0" + (++count);
		patient.setPid(pid);
		System.out.println();
		System.out.print("Enter First Name : ");
		String fname = scanner.nextLine();
		patient.setPfirstname(fname);

		System.out.print("Enter LastName : ");
		String lname = scanner.nextLine();
		patient.setPlastname(lname);

		String gender = Validations.validateGender(sc);
		patient.setPgender(gender);

		System.out.print("Enter Age : ");
		int age = Validations.validateIntegerInput(sc.nextLine());
		patient.setAge(age);

		String bloodgroup = Validations.validateBloodGroup(scanner);
		patient.setBloodGroup(bloodgroup);

		String phoneNumber = Validations.validatePhoneNumber(sc);			
		patient.setPhoneNumber(phoneNumber);						

		System.out.print("Enter Address : ");
		String address = scanner.nextLine();
		patient.setPaddress(address);

		//Patient patient = new Patient(pid,fname,lname,gender,age,address);

		savePatient(patient);

		System.out.println("Patient Added Successfully...");

		searchPatientById(patient.getPid());
	}



	// Method to retrieve all patients from the patient table
	public void getAllPatients() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Try-with-resources block for automatically closing the session

			// Return all patients by executing a query
			List<Patient> patients = session.createQuery("from Patient", Patient.class).list();

			for (Patient patient : patients) {
				System.out.println();
				searchPatientById(patient.getPid());
			}
		}
	}

	//Method to update the patient
	public void updatePatient(Patient patient) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Try-with-resources block for automatically closing the session

			// Start a transaction
			transaction = session.beginTransaction();

			// Update the patient object
			session.merge(patient);

			// Commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void updatePatientContactInfo(String patientId) {

		Session session = HibernateUtil.getSessionFactory().openSession() ;

		// Retrieve the Patient entity by its ID
		Patient patient = session.get(Patient.class, patientId);
		
		boolean continueUpdating = true;
		while (continueUpdating) {
		    if (patient != null) {
		    	System.out.println("Current Patient Details:");
		        searchPatientById(patient.getPid());
		        System.out.println("\nChoose what to update:");
		        System.out.println("1. Phone number");
		        System.out.println("2. Address");
		        System.out.println("3. Phone number and Address");
		        System.out.println("4. Finish updating");
		        System.out.print("Choose (1/2/3/4): ");
		        int choice = sc.nextInt();
		        switch (choice) {
		            case 1:
		                String newPhoneNumber = Validations.validatePhoneNumber(sc);
		                patient.setPhoneNumber(newPhoneNumber);
		                updatePatient(patient);
		                System.out.println("Patient Phone number Updated successfully.");
		                break;
		            case 2:
		                System.out.println("Enter New address: ");
		                String newAddress = sc.next();
		                patient.setPaddress(newAddress);
		                updatePatient(patient);
		                System.out.println("Patient Address Updated successfully.");
		                break;
		            case 3:
		                String newPhoneNumber1 = Validations.validatePhoneNumber(sc);
		                System.out.println("Enter New address: ");
		                String newAddress1 = sc.next();
		                patient.setPhoneNumber(newPhoneNumber1);
		                patient.setPaddress(newAddress1);
		                updatePatient(patient);
		                System.out.println("Patient Phone number and Address Updated successfully.");
		                break;
		            case 4:
		                continueUpdating = false; // Exit the loop
		                break;
		            default:
		            	
		                System.out.println("Invalid choice. Please try again.");
		                break;
		        }
		        		       
		    } else {
		        System.out.println("Patient not found.");
		        continueUpdating = false; // Exit the loop
		    }
		    
		}
		System.out.println("\nUpdated Patient Details:");
		searchPatientById(patient.getPid());
	}




	public void searchPatientById(String Pid) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {			

			// get an instructor object
			Patient patient = session.get(Patient.class, Pid);
			if(patient != null) {

				System.out.println("Patient Id          :  "+patient.getPid());
				System.out.println("Patient First Name  :  "+patient.getPfirstname());
				System.out.println("Patient Last Name   :  "+patient.getPlastname());
				System.out.println("Patient Age         :  "+patient.getAge()+" yrs");
				System.out.println("Patient Blood Group :  "+patient.getBloodGroup());
				System.out.println("Patient Gender      :  "+patient.getPgender());
				System.out.println("Patient Address     :  "+patient.getPaddress());
				System.out.println("Patient Phone Number:  "+patient.getPhoneNumber());
				System.out.println("----------------------------------------------");
			}
			else {
				System.out.println("Patient with id "+Pid+" not found");
			}
		}catch (Exception e) {			
			e.printStackTrace();
		}

//		return patient;
	}


	// close the session
	public void close() {
		HibernateUtil.getSessionFactory().close();
	}



	public int getPatientcount() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Patient", Patient.class).list().size();

		}
	}
	public boolean TestsavePatient(Patient patient) {

		Transaction transaction = null; 

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Try-with-resources block for automatically closing the session

			// Start a transaction
			transaction = session.beginTransaction();

			// Save the patient object
			session.persist(patient);

			// Commit transaction close
			transaction.commit();									
		} 
		catch (Exception f) { 
			if (transaction != null) {
				transaction.rollback(); 
			}
			f.printStackTrace(); 
		}
		return (true);
	}

}




