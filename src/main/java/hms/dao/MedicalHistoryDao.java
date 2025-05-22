package hms.dao;// user-defined package 
// import user-defined package
import hms.entity.Appointment;
import hms.entity.Doctor;
import hms.entity.MedicalHistory;
import hms.entity.Patient;
import hms.util.HibernateUtil;
//import Built-in Packages
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
//import Built-in Packages
import java.util.List;
import java.util.Scanner;
import java.util.Set;
// class declaration
public class MedicalHistoryDao {

	Scanner sc = new Scanner(System.in);
	Appointment appointment = new Appointment();
	MedicalHistory mh = new MedicalHistory();

	// calling by admin

	public void addMedicalHistory() {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			int count1 = getMedicalrecordcount();
			String mid = "M0" + (++count1);
			mh.setMid(mid);

			System.out.print("Enter doctor id : ");
			String did = sc.nextLine();
			Doctor doctor = session.get(Doctor.class, did);

			if(doctor==null)
			{
				System.out.println("Doctor not found");
				
				return;
			}

			Query<Appointment> query = session.createQuery("FROM Appointment WHERE doctor.id = :doctorId AND Status = 'confirmed'", Appointment.class);
			query.setParameter("doctorId", did);
			List<Appointment> appointments = query.list();


			if (!appointments.isEmpty())
			{
				for (Appointment ele : appointments) {


					Patient patient = ele.getPatient();

					mh.setPatient(patient);

					Doctor doc = ele.getDoctor();

					mh.setDoctor(doc);

					mh.setAppointment(ele);
					mh.setDateofexamination(ele.getAppointmentDate());

					// display patient details	
					System.out.println("Doctor Id      : "+mh.getDoctor().getDid());
					System.out.println("Doctor Name    : "+mh.getDoctor().getDfirstname());
					System.out.println("Specialization : "+mh.getDoctor().getSpecialization());
					System.out.println("Patient Id     : "+mh.getPatient().getPid());
					System.out.println("Patient Name   : "+mh.getPatient().getPfirstname()+" "+ mh.getPatient().getPlastname());
					System.out.println("Patient Gender : "+mh.getPatient().getPgender());
					System.out.println("Patient Age    : "+mh.getPatient().getAge());

					if(mh.getDiagnosis()==null && mh.getPrescription()==null) {


						// add  diagnosis to the patient
						System.out.println("Enter Diagnosis for Patient "+mh.getPatient().getPfirstname()+" "+mh.getPatient().getPlastname());
						String diagnosis = sc.nextLine();
						mh.setDiagnosis(diagnosis);

						// add prescription for the diagnosis
						System.out.println("Enter Prescription for Patient "+mh.getPatient().getPfirstname()+" "+mh.getPatient().getPlastname());
						String prescription = sc.nextLine();
						mh.setPrescription(prescription);

						saveMedicalHistory(mh);
						
						System.out.println("Medical record added Successfully");
					}
					else {
						System.out.println("Already added medical History to the Patient");
					}
				}
			}else {
				System.out.println("Doctor has no confirmed appointments.");
			}


		} catch (Exception e) { // catch block to print the exception

			e.printStackTrace();
		}

	}
	// Method to retrieve all medical history from the medicalhistory table
	public void printAllMedicalHistory() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			List<MedicalHistory> medicalHistories = session.createQuery("from MedicalHistory", MedicalHistory.class).list();// Retrieve all doctors

			if (medicalHistories != null && !medicalHistories.isEmpty()) {
				for (MedicalHistory mh : medicalHistories) {
					System.out.println();
					System.out.println("MedicalHistory Id : "+mh.getMid());
					System.out.println("Appointment ID    : "+mh.getAppointment().getAppid());
					System.out.println("Patient Id        : "+mh.getPatient().getPid());// Print each medicalhistory
					System.out.println("Patient Name      : "+mh.getPatient().getPfirstname()+" "+ mh.getPatient().getPlastname());
					System.out.println("Doctor Id         : "+mh.getDoctor().getDid());
					System.out.println("Doctor Name       : "+mh.getDoctor().getDfirstname()+" "+mh.getDoctor().getDlastname());
					System.out.println("Diagnosis         : "+mh.getDiagnosis());
					System.out.println("Prescription      : "+mh.getPrescription());
					System.out.println("---------------------------------------------");
				}
			} 
		}catch (Exception e) { // catch block to print Exception
			e.printStackTrace();
		}
	}

	public void saveMedicalHistory(MedicalHistory medicalHistory) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Try-with-resources block for automatically closing the session


			// start a transaction
			transaction = session.beginTransaction();
			// save the medical history object
			session.persist(medicalHistory);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}


	// called by doctor
	// method to print the patient details assigned to doctor
	public void getPatientByDoctorId(String doctorId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Define the query to fetch medical histories by Doctor ID
			Doctor doctor = session.get(Doctor.class, doctorId);
			if(doctor == null)
			{
				System.out.println("Doctor not found");
				return;
			}

			Query<MedicalHistory> query = session.createQuery("FROM MedicalHistory mh WHERE mh.doctor.id = :doctorId", MedicalHistory.class);
			query.setParameter("doctorId", doctorId);
			List<MedicalHistory> medicalHistories = query.getResultList();

			if (medicalHistories.isEmpty()) {
				// If no medical histories are found, print a message
				System.out.println("No patients assigned for this Doctor Id: " + doctorId);
			} else {

				// Iterate over medical histories and print prescriptions
				for (MedicalHistory ele : medicalHistories) {	
					System.out.println("Doctor Id           : "+ele.getDoctor().getDid());
					System.out.println("Doctor Name         : "+ele.getDoctor().getDfirstname()+" "+ele.getDoctor().getDlastname());
					System.out.println("Patient Id          : "+ele.getPatient().getPid());
					System.out.println("Patient Name        : "+ele.getPatient().getPfirstname()+" "+ ele.getPatient().getPlastname());
					System.out.println("Date Of Examination : "+ele.getDateofexamination());
					System.out.println("Diagnosis           : "+ele.getDiagnosis());
					System.out.println("Prescription        : "+ ele.getPrescription());
					System.out.println("----------------------------------------------");
				}
			}
		}catch (Exception e) { // catch block to print exception
			e.printStackTrace();
		}
	}

	// called by patient
	// method to display the all the medical record of the patient
	public static void getMedicalHistoryByPId(String Pid) {
		// open the session from session factory
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// fetch the patient details by patient id
			Patient patient = session.get(Patient.class, Pid);
			// patient record is not null
			if (patient != null) {
				Set<MedicalHistory> medhistories = patient.getMedhistories();

				// Check if there are any medical history records
				if (!medhistories.isEmpty()) {
					// Iterate over the appointments
					for (MedicalHistory ele : medhistories) {
						// Access individual data members of the Medicalhistory entity
						System.out.println("Patient Id            : " + ele.getPatient().getPid());
						System.out.println("Patient Name          : " + ele.getPatient().getPfirstname() + " " + ele.getPatient().getPlastname());
						System.out.println("Doctor Name           : " + ele.getDoctor().getDfirstname() + " " + ele.getDoctor().getDlastname());
						System.out.println("Doctor Specialization : " + ele.getDoctor().getSpecialization());
						System.out.println("Diagnosis             : " + ele.getDiagnosis());
						System.out.println("Prescription          : " + ele.getPrescription());
						System.out.println("----------------------------------------------");
					}
				} else {
					System.out.println("No Medical Histories Found!!");
				}
			} else {
				System.out.println("Patient not found. Enter valid Patient ID");
			}
		} catch (Exception e) { // catch block to print the exception
			e.printStackTrace();
		}
	}

	// method to return the count of medical record in database
	public int getMedicalrecordcount() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from MedicalHistory", MedicalHistory.class).list().size();

		}
	}

}




