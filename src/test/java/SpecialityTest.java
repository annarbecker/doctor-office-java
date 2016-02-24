import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class SpecialityTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Speciality.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Speciality firstSpeciality = new Speciality("cardio");
    Speciality secondSpeciality = new Speciality("cardio");
    assertTrue(firstSpeciality.equals(secondSpeciality));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Speciality mySpeciality = new Speciality("cardio");
    mySpeciality.save();
    assertTrue(Speciality.all().get(0).equals(mySpeciality));
  }

  @Test
  public void find_findSpecialityInDatabase_true() {
    Speciality mySpeciality = new Speciality("cardio");
    mySpeciality.save();
    Speciality savedSpeciality = Speciality.find(mySpeciality.getId());
    assertTrue(mySpeciality.equals(savedSpeciality));
  }

  @Test
  public void getDoctors_retrievesALlDoctorsFromDatabase_doctorsList() {
    Speciality mySpeciality = new Speciality("cardio");
    mySpeciality.save();
    Doctor firstDoctor = new Doctor("John Smith", "cardio", mySpeciality.getId());
    firstDoctor.save();
    Doctor secondDoctor = new Doctor("Kelly Smith", "cardio", mySpeciality.getId());
    secondDoctor.save();
    Doctor[] doctors = new Doctor[] { firstDoctor, secondDoctor };
    assertTrue(mySpeciality.getDoctors().containsAll(Arrays.asList(doctors)));
  }
}
