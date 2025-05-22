package hms.usermodules;//user-defined Package
//import Built-in Packages
import java.util.Scanner;
import hms.authentication.*;
//import user-defined packages
import hms.dao.DoctorDao;
import hms.dao.MedicalHistoryDao;
import hms.dao.UserDao;
import hms.main.MainApp;

// Class declared
public class DoctorModule {

	//Ansi colours
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	
	//public static void main(String[] args) {
	
		public static void doctorMenu(String uname) {

		Scanner sc = new Scanner(System.in);
		// create a reference for the required classes
		DoctorDao doctordao = new DoctorDao();		
		MedicalHistoryDao medicaldao = new MedicalHistoryDao();
		UserDao userdao = new UserDao();
		Login login = new Login();
		
		// passing boolean = true as the condition to while
		boolean loop=true;
		while(loop){
			
			// Displaying the menu options
			
			System.out.print(ANSI_BLUE);//Blue color

			System.out.println("\n**Welcome to Doctor Module*");
			System.out.println("1. Personal Information");
			System.out.println("2. View Appointments");
			System.out.println("3. View Assigned Patients List");
			System.out.println("4. Treat Patient");
			System.out.println("5. Change Password ");
			System.out.println("6. Logout");
			
			System.out.print(ANSI_PURPLE);//purple color
			
			System.out.print("\nEnter your choice: ");

			int choice =  Validations.validateIntegerInput(sc.nextLine());// Read user's choice
			//switch-case statement for the menu options provided:
			switch(choice){
			case 1:
				System.out.print(ANSI_GREEN);//Green color
				
				// Doctor can view personal information
				System.out.print("Enter Doctor ID to view Details: ");
				String id = sc.nextLine();	
				
				System.out.print(ANSI_BLACK);//Black color
				
				doctordao.getDoctordetailsByDid(id);				
				break;

			case 2: 
				System.out.print(ANSI_GREEN);//Green color
			
				//Doctor can view available appointments
				System.out.print("Enter Doctor ID to view Appoinment: ");
				String did = sc.nextLine();	
				
				System.out.print(ANSI_BLACK);//Black color

				doctordao.getAppointmentByDid(did);		
				break;

			case 3:
				System.out.print(ANSI_GREEN);//Green color

				// View Patients list assigned to Doctor
				System.out.print("Enter Doctor ID to view list of patients: ");
				String did1 = sc.nextLine();
				
				System.out.print(ANSI_BLACK);//Black color

				medicaldao.getPatientByDoctorId(did1);
				break;

			case 4: 
				System.out.print(ANSI_BLACK);//Black color

				//add diagnosis and prescription
				medicaldao.addMedicalHistory();	
				break;

			case 5:
				System.out.print(ANSI_BLACK);//Black color

				//doctor can change the password
				userdao.changepassword(uname);
				break;

			case 6:
				System.out.print(ANSI_GREEN);//Green color

				// exiting from the doctor module
				System.out.println("Logging out...");
				System.out.println("Thankyou !!!!");
				loop = false;//set loop to false to exit from while loop
				break;

			default:
				System.out.print(ANSI_RED);//Red color
				// default prints when invalid choice is given
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
		// Asking the user whether they want to Login again
		System.out.print(ANSI_GREEN);//Green color
		System.out.print("\nDo you want to Re-Login (Y/N) : ");
		char choice = sc.next().charAt(0);
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

