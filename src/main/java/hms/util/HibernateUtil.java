package hms.util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
public class HibernateUtil {
	// Declaring a static SessionFactory variable
   private static SessionFactory sessionFactory;
  
   // Method to get SessionFactory
   public static SessionFactory getSessionFactory() {
       if (sessionFactory == null) {
           try {
           	
           	// Creating a new Configuration object
               Configuration configuration = new Configuration();
         
            // Creates a Properties object to hold configuration settings for Hibernate.
               Properties settings = new Properties();
              
            // Specifies the JDBC driver class to use for connecting to the MySQL database.
               settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
              
            // Sets the URL of the MySQL database.
               settings.put(Environment.URL, "jdbc:mysql://localhost:3306/hibernate_hms");
              
            // Sets the username for accessing the database.
               settings.put(Environment.USER, "root");
              
            // Sets the password for accessing the database.
               settings.put(Environment.PASS, "root");
              
               // Enables logging of SQL statements executed by Hibernate.
               settings.put(Environment.SHOW_SQL, "false");
             
            // Specifies the strategy for managing the current session in Hibernate.
               settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
              
            // Specifies how Hibernate should update the database schema based on the mapping metadata (e.g., create, update, validate).
               settings.put(Environment.HBM2DDL_AUTO, "update");
              
            // Applies the settings to the Hibernate Configuration object
               configuration.setProperties(settings);
              
            // Add entity.hbm.xml mapping files
               configuration.addResource("hms/entity/Patient.hbm.xml");
               configuration.addResource("hms/entity/Doctor.hbm.xml");
               configuration.addResource("hms/entity/Appointment.hbm.xml");
               configuration.addResource("hms/entity/MedicalHistory.hbm.xml");
               configuration.addResource("hms/entity/Room.hbm.xml");
               configuration.addResource("hms/entity/User.hbm.xml");
              
            // Creating a StandardServiceRegistryBuilder to build the StandardServiceRegistry.
            //applySettings() method is used to apply the properties from the Hibernate configuration to the service registry builder.
               ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                       .applySettings(configuration.getProperties()).build();
                   sessionFactory = configuration.buildSessionFactory(serviceRegistry);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       return sessionFactory; // Returning SessionFactory
   }
   // Method to shutdown SessionFactory
   public static void shutdown() {
       if (sessionFactory != null) {
           sessionFactory.close(); // Closing SessionFactory                    
       }
   }
}
