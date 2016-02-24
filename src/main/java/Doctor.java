import java.util.List;
import org.sql2o.*;


public class Doctor {
  private int specialityId;
  private int id;
  private String name;
  private String speciality;

  public Doctor(String name, String speciality, int specialityId) {
  this.name = name;
  this.speciality = speciality;
  this.specialityId = specialityId;
  }

  public String getName () {
    return name;
  }

  public int getId() {
    return id;
  }

  public int getSpecialityId() {
    return specialityId;
  }

  public String getSpeciality() {
    return speciality;
  }

  public static List<Doctor> all() {
    String sql = "SELECT id, name, speciality, specialityId FROM doctors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Doctor.class);
    }
  }

  @Override
  public boolean equals(Object otherDoctor) {
    if (!(otherDoctor instanceof Doctor)) {
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getName().equals(newDoctor.getName()) &&
      this.getId() == newDoctor.getId() &&
      this.getSpeciality().equals(newDoctor.getSpeciality()) &&
      this.getSpecialityId() == newDoctor.getSpecialityId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO doctors (name, speciality, specialityId) VALUES (:name, :speciality, :specialityId)";
      this.id = (int) con.createQuery(sql, true)
       .addParameter("name", this.name)
       .addParameter("speciality", this.speciality)
       .addParameter("specialityId", this.specialityId)
       .executeUpdate()
       .getKey();
    }
  }

  public static Doctor find(int id) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM doctors WHERE id=:id";
    Doctor doctors = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Doctor.class);
    return doctors;
    }
  }

  public List<Patient> getPatients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients WHERE doctorId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Patient.class);
    }
  }
}
