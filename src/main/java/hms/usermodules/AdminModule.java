package hms.usermodules;//user-defined Package
import java.util.List;
// import Built-in Packages
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.internal.build.AllowSysOut;
import org.hibernate.query.Query;

import hms.dao.AppointmentDao;
import hms.dao.DoctorDao;
import hms.dao.MedicalHistoryDao;
import hms.dao.PatientDao;
import hms.dao.RoomDao;
import hms.dao.UserDao;
import hms.entity.Patient;
import hms.main.MainApp;
import hms.authentication.*;
// import user-defined package
import hms.util.HibernateUtil;
// Class declaration
public class AdminModule {

	//public static void main(String[] args) {

	//Ansi colours
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";

	// opening a session to achieve CRUD operation
	Session session = HibernateUtil.getSessionFactory().openSession();

	public static void adminMenu(String uname) {

		// create a ref to required classes
		Login login = new Login();
		UserDao userdao = new UserDao();
		PatientDao patientdao = new PatientDao();
		DoctorDao doctordao = new DoctorDao();
		AppointmentDao appointmentdao = new AppointmentDao();
		MedicalHistoryDao mhdao = new MedicalHistoryDao();
		RoomDao roomdao = new RoomDao();
		// Scanner class declaration
		Scanner scanner = new Scanner(System.in);
		// passing boolean = true as the condition to while
		boolean admincounter = true;
		while (admincounter) {
			// Each line corresponds to a menu option that users
			//with administrative privileges can choose from Displaying the menu options

			System.out.print(ANSI_BLUE);//Blue color

			System.out.println("\n******Admin Menu******");
			//created by bhavani
			System.out.println("\n1. User-Type Management");
			System.out.println("2. Patient Management");
			// created by navaneetha
			System.out.println("3. Doctor Management");
			System.out.println("4. Appointment Management");
			// created by niveditha
			System.out.println("5. Medical History Management");
			System.out.println("6. Rooms Management");
			System.out.println("7. Logout");
			System.out.print("\nEnter your choice: ");
			// Read user's choice

			int choice = Validations.validateIntegerInput(scanner.nextLine());
			//Switch cases statement for the menu options provided:
			switch (choice) {
			case 1:
				// Implement User-Type Management functionality


				boolean usercounter = true;
				while (usercounter) {
					
					System.out.print(ANSI_GREEN);//Green color

					// Displaying the menu options					
					System.out.println("\n******User-Type Management******");
					System.out.println("\n1. View All Usertypes");
					System.out.println("2. Add Username and password to Doctor");
					System.out.println("3. Change password for Admin");
					System.out.println("4. Return to Admin menu");

					System.out.print(ANSI_PURPLE);//purple color

					System.out.print("\nEnter your choice: ");
					int choice1 = Validations.validateIntegerInput(scanner.nextLine());// Read users input
					//switch-case statement for the menu options provided:
					switch (choice1) {
					case 1:
						System.out.print(ANSI_BLACK);//Black color
						
						// calling a method to view all the user-types details
						userdao.getAllUsers();

						break;

					case 2:
						System.out.print(ANSI_BLACK);//Black color

						// calling a method to add username and password to the Doctor
						userdao.saveUserD();
						break;

					case 3:
						System.out.print(ANSI_BLACK);//Black color

						// Admin can change the password
						userdao.changepassword(uname);
						break;

					case 4:
						System.out.print(ANSI_GREEN);//Green color

						// Return to the admin menu
						System.out.println("Exiting from User management.....");
						usercounter = false;//set usercounter to false to exit from while loop
						break;

					default:// default prints when invalid choice is given
						
						System.out.print(ANSI_RED);//Red color

						System.out.println("Invalid choice. Please try again.");
						break;
					}
				}
				break;// case 1 break

			case 2:// Implement Patient Management functionality
				boolean patientcounter = true;
				while (patientcounter) {
					
					System.out.print(ANSI_GREEN);//Green color

					// Displaying the menu options
					System.out.println("\n******Patient Management******");
					System.out.println();
					System.out.println("1. View All Patients");
					System.out.println("2. Add Patient Details");
					System.out.println("3. Update Patient Details");
					System.out.println("4. Search Patient Details ");
					System.out.println("5. Return to Admin menu");
					
					System.out.print(ANSI_PURPLE);//purple color

					System.out.print("\nEnter your choice: ");					
					int choice2 = Validations.validateIntegerInput(scanner.nextLine());// Read users input
					//switch-case statement for the menu options provided:
					switch (choice2) {
					case 1:
						System.out.print(ANSI_BLACK);//Black color

						// Get all the patient Details
						patientdao.getAllPatients();
						break;

					case 2: 
						System.out.print(ANSI_BLACK);//Black color

						// Add the Patient Details to the Database
						patientdao.addPatient(scanner);
						break;

					case 3: 
						System.out.print(ANSI_BLACK);//Black color

						// Update the Patient Ph no and Address
						System.out.print("Enter PatientID : ");
						String pid1 = scanner.nextLine();
						patientdao.updatePatientContactInfo(pid1);
						break;

					case 4:
						System.out.print(ANSI_BLACK);//Black color

						// Search Details by Patient Id
						System.out.print("Enter PatientID : ");
						String pid2 = scanner.nextLine();
						patientdao.searchPatientById(pid2);
						break;

					case 5:
						System.out.print(ANSI_GREEN);//Green color

						// Return to the admin menu
						System.out.println("Exiting from Patient management.....");
						patientcounter = false;// set patientcounter to false to exit from while loop
						break;

					default:
						System.out.print(ANSI_RED);//Red color

						// default prints when invalid choice is given
						System.out.println("Invalid choice. Please try again.");
						break;
					}
				}
				break;// case 2 break

			case 3:
				// Implement Doctor Management functionality
				boolean doctorcounter = true;
				while (doctorcounter) {
					
					System.out.print(ANSI_GREEN);//Green color

					// Displaying the menu options					
					System.out.println("\n ******Doctor Management******");
					System.out.println();
					System.out.println("1. View All Doctors");
					System.out.println("2. Add Doctor Details");
					System.out.println("3. Update Doctor Details");
					System.out.println("4. Search Doctor Details ");
					System.out.println("5. Return to Admin menu");
					
					System.out.print(ANSI_PURPLE);//purple color

					System.out.print("\nEnter your choice: ");
					int choice3 =Validations.validateIntegerInput(scanner.nextLine());// Read users input
					//switch-case statement for the menu options provided:
					switch (choice3) {

					case 1:
						System.out.print(ANSI_BLACK);//Black color

						// Get All doctor details
						doctordao.printAllDoctors();
						break;
					case 2:
						System.out.print(ANSI_BLACK);//Black color

						// Add Doctor Details to the Database
						doctordao.addDoctor(scanner);
						
						break;

					case 3:
						
						System.out.print(ANSI_PURPLE);//Black color
						// Update Doctor Details
						System.out.print("Enter DoctorId : ");
						String did1 = scanner.nextLine();
						System.out.print(ANSI_BLACK);//Black color
						doctordao.updateDoctorDetails(did1,scanner);
						break;

					case 4:
						System.out.print(ANSI_PURPLE);

						// Search Details by Doctor Id
						System.out.print("Enter DoctorID : ");
						String did2 = scanner.nextLine();
						System.out.print(ANSI_BLACK);//Black color
						doctordao.getDoctordetailsByDid(did2);
						break;

					case 5:
						System.out.print(ANSI_GREEN);//Green color

						// Return to the admin menu
						System.out.println("Exiting from Doctor Management.....");
						doctorcounter = false;// set doctorcounter to false to exit from while loop
						break;

					default:
						System.out.print(ANSI_RED);//Red color

						// default prints when invalid choice is given
						System.out.println("Invalid choice. Please try again.");
						break;
					}
				}
				break;// case 3 break


			case 4:
				// Implement Appointment Management functionality
				boolean appointmentcounter = true;
				while (appointmentcounter) {
					
					System.out.print(ANSI_GREEN);//Green color
					
					// Displaying the menu options
					System.out.println("\n******Appointment Management******");	
					System.out.println("\n1. View All Appointments Details");					
					System.out.println("2. Schedule an Appointment to Patient ");
					System.out.println("3. Cancel the Appointment");
					System.out.println("4. Update Appointment Date and Time");
					System.out.println("5. Search for Appoinmtments ");
					System.out.println("6. Return to Admin menu");
					
					System.out.print(ANSI_PURPLE);//purple color

					System.out.print("\nEnter your choice: ");

					int choice4 = Validations.validateIntegerInput(scanner.nextLine());
					//switch-case statement for the menu options provided:
					switch (choice4) {
					case 1:
						System.out.print(ANSI_BLACK);//Black color

						// View all the available Appointments
						appointmentdao.printAllAppointments();
						break;

					case 2:
						System.out.print(ANSI_BLACK);//Black color

						appointmentdao.addAppointmentToPatient(scanner);
						break;

					case 3:
						System.out.print(ANSI_BLACK);//Black color

						// cancel the appointment
						System.out.println("Enter Appointmnet Id to Cancel the Appointmnet");
						String appid1 = scanner.nextLine();
						appointmentdao.cancelAppointment(appid1);
						break;

					case 4:
						System.out.print(ANSI_BLACK);//Black color

						// Update the Appointment date & time of the Patient
						appointmentdao.updateAppointmentDetails();
						break;
						
					case 5:
						System.out.print(ANSI_BLACK);//Black color

						// Search by appointment id
						System.out.println("Enter Appointment Id");
						String appointmentId1 = scanner.nextLine();
						appointmentdao.searchAppointmentByAppid(appointmentId1);
						break;
						
					case 6:
						System.out.print(ANSI_GREEN);//Green color

						// return to admin menu
						System.out.println("Exiting from Appointment management.....");
						appointmentcounter = false;// set appointmentcounter to false to exit from while loop
						break;
					default:
						System.out.print(ANSI_RED);//Red color

						// default prints when invalid choice is given
						System.out.println("Invalid choice. Please try again.");
						break;
					}
				}
				break;// case 4 break
			case 5:
				// Implement MedicalHistory Management functionality
				boolean medicalcounter = true;
				while(medicalcounter) {
					
					System.out.print(ANSI_GREEN);//Green color

					// Displaying the menu options
					System.out.println("\n******MedicalHistory Management******");
					System.out.println("\n1. View All MedicalHistory");
					System.out.println("2. Search patient MedicalHistory  ");
					System.out.println("3. Return to Admin menu");
					
					System.out.print(ANSI_PURPLE);//purple color

					System.out.print("\nEnter your choice: ");
					int choice4 = Validations.validateIntegerInput(scanner.nextLine());// Read users input
					//switch-case statement for the menu options provided:
					switch (choice4) {

					case 1:
						System.out.print(ANSI_BLACK);//Black color

						// View All MedicalHistory details
						mhdao.printAllMedicalHistory();	
						break;										

					case 2:
						System.out.print(ANSI_BLACK);//Black color

						// Search for patient's MedicalHistory
						System.out.print("Enter Patient id to view Medical History: ");
						String pid = scanner.nextLine();
						mhdao.getMedicalHistoryByPId(pid);
						break;

					case 3:
						System.out.print(ANSI_GREEN);//Green color

						// Return to admin menu
						System.out.println("Exiting from Medicalhistory management.....");
						medicalcounter = false;// set medicalcounter to false to exit from while loop
						break;

					default:// default prints when invalid choice is given
						
						System.out.print(ANSI_RED);//Red color

						System.out.println("Invalid choice. Please try again.");
						break;
					}
				}
				break;// case 5 break

			case 6:
				// Implement Rooms Management functionality
				boolean roomcounter = true;
				while (roomcounter) {
					
					System.out.print(ANSI_GREEN);//Green color

					// Displaying the menu options
					System.out.println("\n****** Room Management******");
					System.out.println("\n1. View All Rooms details");
					System.out.println("2. Allocate Room ");
					System.out.println("3. Update Room Period");
					System.out.println("4. Search Room by Patient Id ");
					System.out.println("5. Return to Admin Menu");

					System.out.print(ANSI_PURPLE);//purple color

					System.out.print("\nEnter your choice: ");
					int choice6 = Validations.validateIntegerInput(scanner.nextLine());// Read users input
					//switch-case statement for the menu options provided:
					switch (choice6) {

					case 1:
						System.out.print(ANSI_BLACK);//Black color

						// Get All room details
						roomdao.viewAllRooms();
						break;
						
					case 2:
						System.out.print(ANSI_BLACK);//Black color

						// Assign Room to patient
						roomdao.addRoom(scanner);
						
						break;

					case 3:System.out.print(ANSI_BLACK);//Black color

					// Update Room Period 
						System.out.print("Enter Room no : ");
						String rno = scanner.nextLine();						
						roomdao.updateRoom(rno);
						break;

					case 4:
						System.out.print(ANSI_BLACK);//Black color

						//search room by patient Id
						roomdao.searchRoomByPatientId(scanner);
						break;

					case 5:
						System.out.print(ANSI_GREEN);//Green color

						// Return to admin menu
						System.out.println("Exiting from Room management.....");
						roomcounter = false;//set roomcounter to false to exit from while loop
						break;

					default:// default prints when invalid choice is given
						
						System.out.print(ANSI_RED);//Red color

						System.out.println("Invalid choice. Please try again.");
						break;
					}
				}
				break;// case 6 break

			case 7:// Exit from the Admin menu
				
				System.out.print(ANSI_GREEN);//Green color

				System.out.println("Logging out...");
				System.out.println("Thankyou !!!!");              
				admincounter=false;// Set the admincounter to false to exit the loop
				break;
			default:// default prints when invalid choice is given
				
				System.out.print(ANSI_RED);//Red color
				
				System.out.println("Invalid choice. Please try again.");
				break;
			}// switch case break

		}// while loop close
		// Asking the user whether they want to Login again
		System.out.print("\nDo you want to Re-Login (Y/N) : ");
		char choice = scanner.next().charAt(0);
		if (choice == 'y' || choice == 'Y') {
			// Continue to the next iteration of the loop
			System.out.println(); // Clear the newline character						
			MainApp.main(null);

		} else {
			System.out.print(ANSI_BLACK);
			System.out.println("Exiting program.");
			System.exit(0);

		}

	}
}
