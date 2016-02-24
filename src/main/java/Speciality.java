import java.util.List;
import org.sql2o.*;


public class Speciality {
  private int id;
  private String type;

  public Speciality(String type) {
  this.type = type;
  }

  public String getType () {
    return type;
  }

  public int getId() {
    return id;
  }

  public static List<Speciality> all() {
    String sql = "SELECT id, type FROM specialities";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Speciality.class);
    }
  }

  @Override
  public boolean equals(Object otherSpeciality) {
    if (!(otherSpeciality instanceof Speciality)) {
      return false;
    } else {
      Speciality newSpeciality = (Speciality) otherSpeciality;
      return this.getType().equals(newSpeciality.getType()) &&
      this.getId() == newSpeciality.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO specialities (type) VALUES (:type)";
      this.id = (int) con.createQuery(sql, true)
       .addParameter("type", this.type)
       .executeUpdate()
       .getKey();
    }
  }

  public static Speciality find(int id) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM specialities WHERE id=:id";
    Speciality specialities = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Speciality.class);
    return specialities;
    }
  }

  public List<Doctor> getDoctors() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM doctors WHERE specialityId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Doctor.class);
    }
  }
}
