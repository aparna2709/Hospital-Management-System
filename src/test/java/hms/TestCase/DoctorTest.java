package hms.TestCase;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hms.dao.*;
import hms.entity.Doctor;

public class DoctorTest {
	
	DoctorDao doctordao;
	
	@BeforeEach
    public void setUp() {
        
		doctordao = new DoctorDao();
     
    }
	 @Test
	    public void testDocotorCreation() {
	    	
		 Doctor doctor = new Doctor();
	        
	        int count = doctordao.getDoctorcount();
			String did = "D0" + (++count);
			doctor.setDid(did);
			doctor.setDfirstname("Shiva");
			doctor.setDlastname("Krishna");
			doctor.setDgender("Male");
			doctor.setSpecialization("Pediatrician");

	        assertTrue(doctordao.TestsaveDoctor(doctor));     
	    }  
}