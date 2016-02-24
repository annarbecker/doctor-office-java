import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class DoctorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Doctor.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Doctor firstDoctor = new Doctor("Jane", "cardio", 1);
    Doctor secondDoctor = new Doctor("Jane", "cardio", 1);
    assertTrue(firstDoctor.equals(secondDoctor));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Doctor myDoctor = new Doctor("Jane", "cardio", 1);
    myDoctor.save();
    assertTrue(Doctor.all().get(0).equals(myDoctor));
  }

  @Test
  public void find_findDoctorInDatabase_true() {
    Doctor myDoctor = new Doctor("Jane", "cardio", 1);
    myDoctor.save();
    Doctor savedDoctor = Doctor.find(myDoctor.getId());
    assertTrue(myDoctor.equals(savedDoctor));
  }

  @Test
  public void getPatients_retrievesALlPatientsFromDatabase_patientsList() {
    Doctor myDoctor = new Doctor("Jane", "cardio", 1);
    myDoctor.save();
    Patient firstPatient = new Patient("John Smith", "2000-01-01", myDoctor.getId());
    firstPatient.save();
    Patient secondPatient = new Patient("Kelly Smith", "2000-01-01", myDoctor.getId());
    secondPatient.save();
    Patient[] patients = new Patient[] { firstPatient, secondPatient };
    assertTrue(myDoctor.getPatients().containsAll(Arrays.asList(patients)));
  }
}
