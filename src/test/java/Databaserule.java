import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctor_office_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deletePatientQuery = "DELETE FROM patients *;";
      String deleteDoctorQuery = "DELETE FROM doctors *;";
      String deleteSpecialtyQuery = "DELETE FROM specialties *;";
      con.createQuery(deletePatientQuery).executeUpdate();
      con.createQuery(deleteDoctorQuery).executeUpdate();
      con.createQuery(deleteSpecialtyQuery).executeUpdate();

    }
  }
}
