import java.util.List;
import org.sql2o.*;


public class Patient {
  private int doctorId;
  private int id;
  private String name;
  private String birthDate;

  public Patient(String name, String birthDate) {
  this.name = name;
  this.birthDate = birthDate;
  // this.doctorId = doctorId;
  }

  public String getName () {
    return name;
  }

  public int getId() {
    return id;
  }

  public int getDoctorId() {
    return doctorId;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public static List<Patient> all() {
    String sql = "SELECT id, name, birthdate FROM patients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patient.class);
    }
  }

  @Override
  public boolean equals(Object otherPatient) {
    if (!(otherPatient instanceof Patient)) {
      return false;
    } else {
      Patient newPatient = (Patient) otherPatient;
      return this.getName().equals(newPatient.getName()) &&
      this.getId() == newPatient.getId() &&
      this.getBirthDate().equals(newPatient.getBirthDate());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patients (name, birthdate) VALUES (:name, :birthdate)";
      this.id = (int) con.createQuery(sql, true)
       .addParameter("name", this.name)
       .addParameter("birthdate", this.birthDate)
       .executeUpdate()
       .getKey();
    }
  }

  public static Patient find(int id) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM patients WHERE id=:id";
    Patient patient = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Patient.class);
    return patient;
    }
  }

}
