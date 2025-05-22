package hms.TestCase;
import hms.entity.*;
import hms.dao.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hms.entity.Patient;

public class PatientTest {
	
	PatientDao patientdao ;

	
	@BeforeEach
    public void setUp() {
        
		patientdao = new PatientDao();
     
    }
	
    @Test
    public void testPatientCreation() {
    	
        Patient patient = new Patient();
        
        int count = patientdao.getPatientcount();
		String pid = "P0" + (++count);
		patient.setPid(pid);
        patient.setPfirstname("Nita");
        patient.setPlastname("Nita");
        patient.setAge(40);
        patient.setPgender("Female");
        patient.setBloodGroup("B+");
        patient.setPhoneNumber("7894561236");
        patient.setPaddress("Hyd");
       

        assertTrue(patientdao.TestsavePatient(patient));      
    }  
}