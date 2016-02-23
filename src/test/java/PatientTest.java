import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class PatientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyATFirst() {
    assertEquals(0, Patient.all().size());
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame() {
    Patient firstPatient = new Patient("John Smith", "1969-01-01");
    Patient secondPatient = new Patient("John Smith", "1969-01-01");
    assertTrue(firstPatient.equals(secondPatient));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Patient myPatient = new Patient("John Smith", "2000-01-01");
    myPatient.save();
    assertTrue(Patient.all().get(0).equals(myPatient));
  }

  @Test
  public void save_assignsIdToObject() {
    Patient myPatient = new Patient("John Smith", "2000-01-01");
    myPatient.save();
    Patient savedPatient = Patient.all().get(0);
    assertEquals(myPatient.getId(), savedPatient.getId());
  }

  @Test
  public void find_findsPatientsInDatabase_true() {
    Patient myPatient = new Patient("John Smith", "2000-01-01");
    myPatient.save();
    Patient savedPatient = Patient.find(myPatient.getId());
    assertTrue(myPatient.equals(savedPatient));
  }

}
