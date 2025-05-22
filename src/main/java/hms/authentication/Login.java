//Write a java program to check user given username and password with login table and give successful and failure message.
package hms.authentication;

import org.hibernate.Session;

import hms.entity.User;
import hms.usermodules.AdminModule;
import hms.usermodules.DoctorModule;
import hms.usermodules.PatientModule;
import hms.util.HibernateUtil;
import java.util.*;

public class Login {
			
	public  void login()
	{		
		Session session = null;
		session = HibernateUtil.getSessionFactory().openSession();		
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.print("Enter Username : ");
		String uname = sc.next();
		System.out.print("Enter Password : ");
		String pword = sc.next();			

		// Create session
		try {
			// Retrieve user from database
			User user = session.get(User.class, uname);
			if (user != null &&  user.getPassword().equals(pword)) {

				System.out.println("Login successfull");  
				// here admin, patient, doctor is not case sensitive
				if (user.getUsertype().equals("Admin"))
				{
					AdminModule.adminMenu(uname);
				}
				else if (user.getUsertype().equals("Patient"))
				{
					PatientModule.patientMenu(uname);
				}
				else if (user.getUsertype().equals("Doctor"))
				{
					DoctorModule.doctorMenu(uname);
				}

			} else {
				System.out.println("Invalid credentials!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
		public static boolean Testlogin(String username, String password)
		{		
			boolean result = true;

			Session session = null;
			session = HibernateUtil.getSessionFactory().openSession();	
					

			// Create session
			try {
				// Retrieve user from database
				User user = session.get(User.class, username);
				if (user != null &&  user.getPassword().equals(password)) 
					result = true;
				else
					result = false;


			}catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	}
