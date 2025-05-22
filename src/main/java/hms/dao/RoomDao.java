package hms.dao;
import hms.entity.Patient;
//import hms.entity.Patient;// Importing the Patient entity class
import hms.entity.Room;// Importing the Room entity class
import hms.util.HibernateUtil;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
public class RoomDao {
	//method to save room
	
	// Method to add new Room
	public void addRoom(Scanner scanner) {
	    Transaction transaction = null;
	    Room room = new Room();
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();

	        // Get patient ID
	        System.out.print("Enter Patient ID : ");
	        String patientId = scanner.nextLine();
	        
	        // Retrieve the patient from the database
	        Patient patient = session.get(Patient.class, patientId);
	        
	        if(patient==null)
	        {
	        	System.out.println("Patient not found");
	        	return;
	        }

	        room.setPatient(patient);
	        // Check if the patient already has a room allocated
	        Room existingRoom = session.createQuery("FROM Room WHERE P_id = :patientId", Room.class)
	                                   .setParameter("patientId", patientId)
	                                   .uniqueResult();

	        if (existingRoom != null) {
	            System.out.println("Patient already has a room allocated. Room allocation canceled.");
	            return;
	        }

	        // Create a new room instance
	       
	        // Set room details
	        int count = getRoomcount();
	        String rno = "R0" + (++count);
	        room.setRno(rno);

	        // Check if the patient exists and has medical history
	        if ( !patient.getMedhistories().isEmpty()) {
	            room.setP_id(patientId);
	            
	            System.out.print("Enter Room Type  : ");
		        room.setRoomtype(scanner.nextLine());

		        System.out.print("Enter Period     : ");
		        room.setPeriod(scanner.nextLine());

	            // Save the room object
	            session.persist(room);

	            // Commit transaction
	            transaction.commit();

	            System.out.println("Room added successfully!");
	        } else {
	            System.out.println("Patient does not have medical history. Room allocation canceled.");
	            return;
	        }
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    }
	}



	// Method to retrieve all rooms from the rooms table
	public List<Room> getAllRooms() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Try-with-resources block for automatically closing the session
			// return all patients
			return session.createQuery("from Room", Room.class).list();
		}
	}
	//Method to update room
	public void updateRoom(Room room) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Try-with-resources block for automatically closing the session
			// start a transaction
			transaction = session.beginTransaction();
			// update the room object
			session.merge(room);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	public void viewAllRooms() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Create a query to select all rooms
			Query<Room> query = session.createQuery("FROM Room", Room.class);

			// Execute the query and get the list of rooms
			List<Room> rooms = query.list();

			// Check if any rooms are found
			if (rooms != null && !rooms.isEmpty()) {
				// Print the found rooms
				System.out.println("All Rooms:");
				for (Room room : rooms) {
					if (room != null) {
						System.out.println("\nRoom No       : " + room.getRno());
						System.out.println("Patient Id    : " + room.getP_id());
						String pid = room.getP_id();
						Patient patient = session.get(Patient.class, pid);
						System.out.println("Patient name  : " + patient.getPfirstname()+" "+patient.getPlastname());
						System.out.println("Patient Age   : " +patient.getAge()+" yrs");
						System.out.println("Room Type     : " + room.getRoomtype());
						System.out.println("Period        : " + room.getPeriod());
						System.out.println("---------------------------------------");
					}
				} 
			}else {
				System.out.println("No rooms found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Method to update room
	public void updateRoom(String rno ) {

		Scanner sc = new Scanner(System.in);
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Retrieve the room from the database based on room number
			Room room = session.get(Room.class, rno);
			if (room == null) {
				System.out.println("Room not found!");
				return;
			}

			// Store previous data            

			System.out.println("\nPrevious Data: ");
			getRoomByRno(rno);

			// Update room type and period
			System.out.print("Enter Room Period : ");
			String roomperiod = sc.nextLine();
			room.setPeriod(roomperiod);

			// Update the room object in the database
			updateRoom(room);

			// Print previous and updated data
			System.out.println("Room Period updated successfully!");

			System.out.println("\nUpdated Data: ");
			if (room != null) {
				System.out.println("Room No       : " + room.getRno());
				System.out.println("Patient Id    : " + room.getP_id());
				String pid = room.getP_id();
				Patient patient = session.get(Patient.class, pid);
				System.out.println("Patient name  : "+ patient.getPfirstname()+" "+patient.getPlastname());
				System.out.println("Patient Age   : "+patient.getAge()+" yrs");
				System.out.println("Room Type     : " + room.getRoomtype());
				System.out.println("Period        : " + room.getPeriod());
				System.out.println("---------------------------------------");
			}
		} catch (Exception e) {

			System.out.println("Failed to update Room Peroid. Please try again.");
			e.printStackTrace();
		}
	}

	public void getRoomByRno(String rno) {
		Session session = HibernateUtil.getSessionFactory().openSession(); 			

		Room room = session.get(Room.class, rno);

		if (room != null) {
			System.out.println("Room No       : " + room.getRno());
			System.out.println("Patient Id    : " + room.getP_id());
			String pid = room.getP_id();
			Patient patient = session.get(Patient.class, pid);
			System.out.println("Patient Name  : "+ patient.getPfirstname()+" "+patient.getPlastname());
			System.out.println("Patient Age   : "+patient.getAge()+" yrs");
			System.out.println("Room Type     : " + room.getRoomtype());
			System.out.println("Period        : " + room.getPeriod());
			System.out.println("---------------------------------------");

		} else {
			System.out.println("Room with "+ rno+" not found.");
		}						
	}

	// Method to search for a room by patient ID
	public void searchRoomByPatientId(Scanner scanner) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Get patient ID
			System.out.print("Enter Patient ID: ");
			String patientId = scanner.nextLine();

			// Retrieve the room associated with the given patient ID
			Query<Room> query = session.createQuery("FROM Room WHERE P_id = :patientId", Room.class);
			query.setParameter("patientId", patientId);
			List<Room> rooms = query.list();

			if (rooms.isEmpty()) {
				System.out.println("No room found for patient ID: " + patientId);
			} else {
				System.out.println("\nRooms found for patient ID: " + patientId);
				for (Room room : rooms) {
					if (room != null) {
						System.out.println("Room No       : " + room.getRno());
						System.out.println("Patient Id    : " + room.getP_id());
						String pid = room.getP_id();
						Patient patient = session.get(Patient.class, pid);
						System.out.println("Patient Name  : "+ patient.getPfirstname()+" "+patient.getPlastname());
						System.out.println("Patient Age   : "+patient.getAge()+" yrs");
						System.out.println("Room Type     : " + room.getRoomtype());
						System.out.println("Period        : " + room.getPeriod());
						System.out.println("---------------------------------------");
					}		
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRoomcount() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Room", Room.class).list().size();

		}
	}

}



