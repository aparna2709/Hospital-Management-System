//write a program to insert new user details to the login table

package hms.authentication;// Package Declaration
//Built-in Package Declaration
import java.util.*;

import hms.dao.PatientDao;
import hms.dao.UserDao;

public class RegisterPatient {
	
	//public static void main(String[] args) {

	public  void register()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("\nRegistration Form\n");
		
		PatientDao patientdao = new PatientDao();
		patientdao.addPatient(sc);

		UserDao userdao = new UserDao();
		userdao.saveUserP();

		System.out.println("Registered succesfully");

		

}
}






