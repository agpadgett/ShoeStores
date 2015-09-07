import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/shoe_stores_test", "Andrea", "Horsehorse2");
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteStoreQuery = "DELETE FROM stores *;";
      String deleteBrandsQuery = "DELETE FROM brands *;";
      con.createQuery(deleteStoreQuery).executeUpdate();
      con.createQuery(deleteBrandsQuery).executeUpdate();
    }
  }
}
