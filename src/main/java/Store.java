import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Store{

  private int id;
  private String store_name;

  public int getId(){
    return id;
  }

  public String getStore(){
    return store_name;
  }

  public Store(String store_name){
    this.store_name = store_name;
  }

  public static List<Store> all(){
    String sql = "SELECT * FROM stores";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(Store.class);
    }
  }

  @Override
  public boolean equals (Object otherStore){
    if (!(otherStore instanceof Store)){
      return false;
    } else {
      Store newStore = (Store) otherStore;
      return this.getId() == newStore.getId() &&
             this.getStore().equals(newStore.getStore());
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO stores (store_name) VALUES (:store_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter(store_name, store_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Store find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM stores WHERE id =:id";
      Store store = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Store.class);
        return store;
    }
  }

  public void updateStore(String new_store_name){
    try(Connection con = DB.sql2o.open()){
      String sql = "UPSDATE stores SET store_name =:store_name WHERE id =:id";
      con.createQuery(sql)
        .addParameter("store_name", new_store_name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete(){
    try(Connection con = DB.sql2o.open()){
      String deleteQuery = "DELETE FROM stores WHERE id=:id";
      con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();

      String joinDeleteQuery = "DELETE FROM brands_stores WHERE store_id =:store_id";
      con.createQuery(joinDeleteQuery)
        .addParameter("store_id", id)
        .executeUpdate();
    }
  }
  //
  // public List<Brand> getBrands(){
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "SELECT brands.* FROM" +
  //       " stores"+
  //       " JOIN brands_store ON (stores.id = brands_store.store_id" +
  //       " JOIN brands ON (brands.id = brands_stores.brand_id)" +
  //       " WHERE stores.id =:id";
  //     List<Brand> brands = con.createQuery(sql)
  //         .addParameter("id", id)
  //         .executeAndFetch(Brands.class);
  //         return brands;
  //   }
  // }




}
