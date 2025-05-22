package hms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import hms.authentication.Validations;
import hms.entity.Patient;
import hms.entity.User;
import hms.util.HibernateUtil;

public class UserDao {

	//Ansi colors
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";


	Scanner sc = new Scanner(System.in);
	User user = new User();

	public void saveUserP() {
		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			System.out.print("Enter Username : ");
			String username = sc.nextLine();			

			// Check if the username already exists
			boolean userExists = session.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
					.setParameter("username", username)
					.getSingleResult() > 0;

					if (userExists) {
						System.out.println("Error: Username already exists.");
						return;
					}
					user.setUsername(username);

					String password = Validations.validateUserPassword(sc);

					user.setPassword(password);
					user.setUsertype("Patient");				

					// save the student object
					session.persist(user);
					// commit transaction
					transaction.commit();

					System.out.println(username + " added successfully ");

					System.out.printf("\n%-10s %-5s%n", "Username", "User Type");
					System.out.printf("%-10s %-5s%n", user.getUsername(), user.getUsertype());


		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}
	public void saveUserD() {
		Transaction transaction = null;

		System.out.print(ANSI_BLACK);//Black color

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();						

			System.out.print("Enter Username : ");
			String username = sc.nextLine();			

			// Check if the username already exists
			boolean userExists = session.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
					.setParameter("username", username)
					.getSingleResult() > 0;

					if (userExists) {

						System.out.print(ANSI_RED);//Red color
						System.out.println("Error: Username already exists.");
						return;
					}
					user.setUsername(username);

					String password = Validations.validateUserPassword(sc);
					user.setPassword(password);
					user.setUsertype("Doctor");				

					// save the student object
					session.persist(user);

					// commit transaction
					transaction.commit();
					System.out.println(username + " added successfully ");

					System.out.printf("\n%-10s %-5s%n", "Username", "User Type");
					System.out.printf("%-10s %-5s%n", user.getUsername(), user.getUsertype());


		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	public void saveUserAdmin() {
		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			System.out.print("Enter Username : ");
			String username = sc.nextLine();			

			// Check if the username already exists
			boolean userExists = session.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
					.setParameter("username", username)
					.getSingleResult() > 0;

					if (userExists) {
						System.out.println("Error: Username already exists.");
						return;
					}
					user.setUsername(username);

					String newPassword = Validations.validateUserPassword(sc);				

					user.setPassword(newPassword);
					user.setUsertype("Admin");					
					// save the student object
					session.persist(user);

					// commit transaction
					transaction.commit();

					System.out.println(username + " added successfully ");

					System.out.printf("\n%-10s %-5s%n", "Username", "User Type");
					System.out.printf("%-10s %-5s%n", user.getUsername(), user.getUsertype());


		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}


	// Method to retrieve all patients from the patient table
	public void getAllUsers() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Try-with-resources block for automatically closing the session

			// Return all patients by executing a query
			List<User> obj = session.createQuery("from User", User.class).list();

			System.out.println("\nAll Users:");
			System.out.printf("%-10s %-5s%n", "Username", "User Type");
			for (User ele : obj) {

				System.out.printf("%-10s %-5s%n", ele.getUsername(), ele.getUsertype());

				// Assuming User class overrides toString method
			}
		}
	}
	// List to store users
	//public static 

	public static void changepassword(String uname) {
		Transaction transaction = null;
		Scanner sc = new Scanner(System.in);
		User user = new User();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			List<User> users = new ArrayList<>();
			// Placeholder logic to update a user
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter the username of the user to update: ");
			String username = scanner.nextLine();
			// Find the user with the specified username
			User userToUpdate = session.get(User.class, username);

			// If the user is found, prompt for a new password and update it
			if (userToUpdate != null && userToUpdate.getUsername().equals(uname)) {

				String newPassword = Validations.validateUserPassword(sc);
				userToUpdate.setPassword(newPassword);

				session.merge(userToUpdate);
				transaction.commit();

				System.out.println("User updated successfully: " + userToUpdate.getUsername());
			} else {
				System.out.print(ANSI_RED);
				System.out.println("User not found/Invalid crenditals to update.");
			}

		}
		catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}



}





