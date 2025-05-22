package hms.usermodules;//user-defined Package
//import Built-in Packages
import java.util.Scanner;
//import user-defined packages
import hms.dao.AppointmentDao;
import hms.dao.MedicalHistoryDao;
import hms.dao.PatientDao;
import hms.dao.UserDao;
import hms.main.MainApp;
import hms.authentication.*;

// class declar
public class PatientModule {

	//	public static void main(String[] args) {

	//Ansi colours
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";


	public static  void patientMenu(String uname){
		Scanner sc = new Scanner(System.in);

		PatientDao patientdao = new PatientDao();
		AppointmentDao appointmentdao = new AppointmentDao();
		MedicalHistoryDao medicaldao = new MedicalHistoryDao();
		Login login = new Login();

		// passing boolean = true as the condition to while
		boolean loop= true;
		while(loop) {
			// Displaying the menu options

			System.out.print(ANSI_GREEN);//Green color

			System.out.println("\n *  Welcome to Patient Module  * ");
			System.out.println("1. Personal Infomation");
			System.out.println("2. View my Appointments");
			System.out.println("3. View My Medical History");			
			System.out.println("4. Update Patient details  ");
			System.out.println("5. Change password ");
			System.out.println("6. Main Menu");

			System.out.println(ANSI_PURPLE);//purple color
			System.out.print("\nEnter your choice :  ");

			int choice = Validations.validateIntegerInput(sc.nextLine());// Read user's choice
			//switch-case statement for the menu options provided.
			switch(choice) {
			case 1:
				System.out.print(ANSI_GREEN);//Green color

				// Patient can view personal information
				System.out.print("Enter patient id: ");
				String id = sc.nextLine();
				System.out.println();

				System.out.print(ANSI_BLACK);//Black color

				patientdao.searchPatientById(id);
				break;

			case 2:
				System.out.print(ANSI_GREEN);//Green color

				// view the appointment details
				System.out.print(" Enter Patient Id: ");
				String pid = sc.nextLine();

				System.out.print(ANSI_BLACK);//Black color

				appointmentdao.searchAppByPid(pid);
				break;

			case 3:
				System.out.print(ANSI_GREEN);//Green color

				//view personal Medical history
				System.out.print("Enter Patient id to view Medical History:");
				String pid1=sc.nextLine();

				System.out.print(ANSI_BLACK);//Black color

				medicaldao.getMedicalHistoryByPId(pid1);
				break;

			case 4:
				System.out.print(ANSI_GREEN);//Green color

				// update Details by Patient Id
				System.out.print("Enter PatientID : ");
				String pid2 = sc.nextLine();

				System.out.print(ANSI_BLACK);//Black color

				patientdao.updatePatientContactInfo(pid2);
				break;

			case 5:	
				System.out.print(ANSI_GREEN);//Green color

				//change password	
				UserDao.changepassword(uname);
				break;

			case 6:
				System.out.print(ANSI_GREEN);//Green color

				// exiting from the Patient module
				System.out.println("Logging out from Patient Module...");
				System.out.println(" Thank you! ");
				loop=false;//set loop to false to exit from while loop
				break;

			default:// default prints when invalid choice is given
				System.out.print(ANSI_RED);//Red color
				System.out.println("Enter vaild choice!");
				break;
			}
		}
		System.out.print(ANSI_GREEN);//Green color
		// Asking the user whether they want to Login again
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
