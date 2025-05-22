package hms.dao;//user-defined Package
import hms.authentication.Validations;
//import Built-in Packages
import hms.entity.*;
import hms.entity.Doctor;
import hms.entity.Patient;
import hms.main.MainApp;
import hms.util.HibernateUtil;
//import Built-in Packages
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

//import Built-in Packages
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// class declaration
public class AppointmentDao {

	// create reference to the classes
	PatientDao patientdao = new PatientDao();
	Scanner sc = new Scanner(System.in);
	Appointment appointment = new Appointment();
	MedicalHistory medhistory = new MedicalHistory();

	// method to schedule an appointment
	public void addAppointmentToPatient(Scanner scanner) {
		// session is null
		Session session = null;
		// transaction is null
		Transaction transaction = null;		
		try {
			// open a session 
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();	

			int count = getAppointmentcount();
			String appid = "AP0" + (++count);
			appointment.setAppid(appid); // Set auto-generated appointment id			

			Query<Patient> query = session.createQuery("SELECT p.id FROM Patient p WHERE p.appointments IS EMPTY", Patient.class);
			List<Patient> patientIds = query.getResultList();

			if(patientIds.isEmpty()) {
				System.out.println("Already assigned Appointments to the Patients.");
				return;
			} 

			System.out.print("Schedule an Appointment to Patient with Ids : ");

			System.out.println(patientIds);
			// Add appointment to the already registered patient
			System.out.print("Enter Patient Id to add the Appointment : ");
			String patientId = scanner.nextLine();

			// check if the patientid related details are present in the database
			Patient patient = session.get(Patient.class, patientId);
			if (patient == null) {
				System.out.println("Patient with ID " + patientId + " does not exist.");
				return;
			}	

			// Check if the patient has any appointments
			if (!patient.getAppointments().isEmpty()) {
				System.out.println("Patient with ID " + patientId + " already has appointments.");
				return;
			}


			appointment.setPatient(patient);// Set patient details to the appointment
			System.out.println("Patient Id   : "+patient.getPid());
			System.out.println("Patient Name : "+patient.getPfirstname()+" "+patient.getPlastname());
			System.out.println("Patient Age  : "+patient.getAge());

			Doctor d = new Doctor();
			System.out.print("Enter Specialization of the Doctor : ");
			String spec = sc.nextLine();

			Query<Doctor> query1 = session.createQuery("SELECT d FROM Doctor d WHERE d.specialization = :spec",Doctor.class)
					.setParameter("spec", spec);
			List<Doctor> relatedDoctors = query1.getResultList();

			if (relatedDoctors.isEmpty()) {
				System.out.println("No doctors found related to the patient's diagnosis.");
				return;
			}

			System.out.println("Available doctors related to the patient's diagnosis:");
			for (Doctor doctor : relatedDoctors) {
				System.out.println("Doctor ID: " + doctor.getDid() + ", Name: " + doctor.getDfirstname());
			}

			System.out.println("Enter Doctor ID to Schedule Appointment:");
			String doctorId = sc.nextLine();

			Doctor selectedDoctor = null;
			for (Doctor doctor : relatedDoctors) {
				if (doctor.getDid().equals(doctorId)) {
					selectedDoctor = doctor;
					break;
				}
			}

			if (selectedDoctor == null) {
				System.out.println("Invalid Doctor ID.");
				return;
			}
			patient.setDoctor(selectedDoctor);// Set doctor details to the patient table
			appointment.setDoctor(selectedDoctor);// Set doctor details to the appointment table
			// while loop to true			
			while(true) {
				System.out.print("Enter Appointment Date (yyyy-MM-dd): ");
				String appointmentDateStr = sc.nextLine();
				try {// validate the date format
					Date appointmentDate = parseDate(appointmentDateStr);
					appointment.setAppointmentDate(appointmentDate);// Set appointment date to appointment table			
					break;
				} catch (ParseException e) { // catch bloock to print the exception
					System.out.println("Invalid date format. Appointment not added.");
				}
			}

			System.out.print("Enter Appointment Time :");
			String time= sc.nextLine();
			appointment.setAppointmentTime(time);// set appointment time 
			appointment.setStatus("Confirmed");
			// Add the appointment to the patient's list of appointments
			//String appid, Patient patient, Doctor doctor, Date appointmentDate, String appointmentTime
			// save the appointment in the database
			session.persist(appointment);

			session.merge(patient);// Update patient in the database 
			// commit the transaction
			transaction.commit();
			System.out.println("Appointment Added successfully ");

		} catch (Exception e) { // catch bloock to print the exception
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	// Method to update appointments
	// method called by admin - case 4 -> case 3
	public void updateAppointmentDetails() {
		// open the session
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// begin the transaction
			Transaction transaction = session.beginTransaction();

			System.out.println("Enter Appointment ID to update:");
			String appointmentId = sc.nextLine();
			Appointment existingAppointment = session.get(Appointment.class, appointmentId);;		
			if (existingAppointment!=null)
			{
				searchAppointmentByAppid(appointmentId);// print the appointment details
				// Update appointment details
				// date validation format
				while(true) {
					System.out.print("Enter Appointment Date (yyyy-MM-dd): ");
					String appointmentDateStr = sc.nextLine();
					try {
						Date appointmentDate =parseDate(appointmentDateStr);
						existingAppointment.setAppointmentDate(appointmentDate);
						break;
					} catch (ParseException e) {
						System.out.println("Invalid date format. Appointment not added.");

					}
				}
				System.out.println();
				System.out.print("Enter new Appointment Time :");
				String time= sc.nextLine();
				existingAppointment.setAppointmentTime(time);
				existingAppointment.setStatus("confirmed");
				// Use the existingAppointment object as needed
				System.out.println("Appointment date updated to: " + existingAppointment.getAppointmentDate());
				System.out.println("Appointment time updated to: " + existingAppointment.getAppointmentTime());

				// Update the appointment in the database
				session.merge(existingAppointment);
				// commit the transaction
				transaction.commit();
				searchAppointmentByAppid(existingAppointment.getAppid());
				System.out.println("Appointment updated successfully");
			}else 
				System.out.println("Appointment not found ");
		}  catch (Exception e) { // catch bloock to print the exception
			e.printStackTrace();
		}
	}


	//method called by admin - case 4 view all appointments
	public void printAllAppointments()
	{	// open session
		Session session = HibernateUtil.getSessionFactory().openSession();
		// list to store all the appointments
		List<Appointment> appointments = session.createQuery("FROM Appointment", Appointment.class).list();
		// for each loop to print each appointment
		for (Appointment appointment : appointments) {
			System.out.println();
			searchAppointmentByAppid(appointment.getAppid());
		}
	}

	// method to search for appointments
	public void searchAppointmentByAppid(String Apid) {
		Appointment appointment = null;
		// open the session
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			appointment = session.get(Appointment.class, Apid);

			if(appointment!= null)	{	
				System.out.println("Appointment ID        : " + appointment.getAppid());
				System.out.println("Patient ID            : " + appointment.getPatient().getPid());
				System.out.println("Patient Name          : " + appointment.getPatient().getPfirstname() + " " + appointment.getPatient().getPlastname());
				System.out.println("Doctor Name           : " + appointment.getDoctor().getDfirstname() + " " + appointment.getDoctor().getDlastname());
				System.out.println("Doctor Specialization : " + appointment.getDoctor().getSpecialization());
				System.out.println("Appointment Date      : " + appointment.getAppointmentDate());
				System.out.println("Appointment Time      : " + appointment.getAppointmentTime());
				System.out.println("Appointment Status    : " + appointment.getStatus());
				System.out.println("-----------------------------------------");
			}
			else {
				System.out.println("Appointment with ID " +Apid + " not found.");
			}			
		} catch (Exception e) {	 // catch bloock to print the exception		
			e.printStackTrace();
		}
	}


	// method to get the count of the appointments in the database
	public int getAppointmentcount() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// returns the count of the appointments
			return session.createQuery("from Appointment", Appointment.class).list().size();

		}
	}

	// method to validate the date format
	private static Date parseDate(String dateString) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String DATE_PATTERN = "^(\\d{4})-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$";
		Pattern pattern = Pattern.compile(DATE_PATTERN);
		Matcher matcher = pattern.matcher(dateString);

		if (!matcher.matches()) {
			System.out.println("Invalid Date format. Please enter valid date.");
			throw new ParseException("Invalid date format", 0);
		}

		return dateFormat.parse(dateString);
	}

	// method to confirm the appointment
	public void confirmAppointment(String appId) {
		Transaction transaction = null;
		// open session
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// begin the transaction
			transaction = session.beginTransaction();

			Appointment appointment = session.get(Appointment.class, appId);

			if (appointment != null) {				
				appointment.setStatus("confirmed");// set the status for appointment
				System.out.println("Appointment confirmed successfully.");
			} else {
				System.out.println("Appointment not found with ID: " + appId);
				return;
			}

			session.merge(appointment);// update the appointment in database
			transaction.commit();// commit the transaction
		} catch (Exception e) { // catch bloock to print the exception
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	// method to cancel the appointment
	public void cancelAppointment(String appId) {
		Transaction transaction = null;
		// open the session
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// begin the transaction
			transaction = session.beginTransaction();

			Appointment appointment = session.get(Appointment.class, appId);
			if (appointment != null) {

				System.out.println("Patient Id   : "+appointment.getPatient().getPid());
				System.out.println("Patient Name : "+appointment.getPatient().getPfirstname()+" "+appointment.getPatient().getPlastname());

				System.out.print("\nDo you want to Cancel the Appointment (Y/N) : ");
				char choice = sc.next().charAt(0);
				if (choice == 'y' || choice == 'Y') {
					// Continue to the next iteration of the loop
					appointment.setStatus("cancelled");

					System.out.println("Appointment canceled successfully.");
				} else {					
					return;
				}

			} else {
				System.out.println("Appointment not found with ID: " + appId);
				return;
			}
			session.merge(appointment);// update appointment in database
			transaction.commit();// commit the transaction
		} catch (Exception e) { // catch bloock to print the exception
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	public void searchAppByPid(String pid) {
		// open the session
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Retrieve the patient from the database
			Patient patient = session.get(Patient.class, pid);

			// Check if the patient exists
			if (patient != null) {
				// Query appointments associated with the patient ID
				String hql = "FROM Appointment WHERE patient.id = :pid";
				Query<Appointment> query = session.createQuery(hql, Appointment.class);
				query.setParameter("pid", pid);
				List<Appointment> appointments = query.getResultList();
				if(!appointments.isEmpty()){
					// Process the appointments
					for (Appointment appointment : appointments) {
						// Do something with the appointment
						searchAppointmentByAppid(appointment.getAppid());
						// Print other appointment details as needed
					}


				}else
				{
					System.out.println("Patient has no appointments.");
				}
			} else {
				System.out.println("Patient with ID " + pid + " not found.");
			}
		} catch (Exception e) {
			e.printStackTrace(); // Print the exception
		}
	}
}






