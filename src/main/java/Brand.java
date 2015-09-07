import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Brand{
  private int id;
  private String brand_name;

  public int getId(){
    return id;
  }

  public String getBrand(){
    return brand_name;
  }

  public Brand(String brand_name){
    this.brand_name = brand_name;
  }

  public static List<Brand> all(){
    String sql = "SELECT * FROM brands";
    try(Connection con = DB.sql2o.open()){
      return con. createQuery(sql).executeAndFetch(Brand.class);
    }
  }

  @Override
  public boolean equals (Object otherBrand){
    if (!(otherBrand instanceof Brand)){
      return false;
    } else {
      Brand newBrand = (Brand) otherBrand;
      return this.getId() == newBrand.getId() &&
             this.getBrand().equals(newBrand.getBrand());
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO brands (brand_name) VALUES (:brand_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("brand_name", brand_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Brand find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM brands WHERE id=:id";
      Brand brand = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Brand.class);
        return brand;
    }
  }

  public void delete(){
    try(Connection con = DB.sql2o.open()){
      String deleteQuery = "DELETE FROM brands WHERE id =:id";
      con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();

      String joinDeleteQuery = "DELETE FROm brands_stores WHERE brand_id =:brand_id";
      con.createQuery(joinDeleteQuery)
          .addParameter("brand_id", id)
          .executeUpdate();
    }
  }
  //
  // public List<Store> getStores(){
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "SELECT stores.* FROM" +
  //     " brands"+
  //     " JOIN brands_stores ON (brands.id = brands_stores.brand_id)"+
  //     " JOIN stores ON (stores.id = brands_stores.brand_id)"+
  //     " WHERE brands.id =:id";
  //   List<Store> stores = con.createQuery(sql)
  //     .addParameter("id", id)
  //     .executeAndFetch(Store.class);
  //     return stores;
  //   }
  // }

  public ArrayList<Store> getStores(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT store_id FROM brands_stores WHERE brand_id=:brand_id";
      List<Integer> storeIds = con.createQuery(sql)
        .addParameter("brand_id", this.getId())
        .executeAndFetch(Integer.class);

        ArrayList<Store> stores = new ArrayList<Store>();

        for(Integer storeId : storeIds){
          String brandQ = "SELECT * FROM stores WHERE id =:storeId";
          Store store = con.createQuery(brandQ)
            .addParameter("storeId",storeId)
            .executeAndFetchFirst(Store.class);
          stores.add(store);
        }

        return stores;
    }
  }

  public void addStore(Store store){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO brands_stores (brand_id, store_id) VALUES (:brand_id, :store_id)";
      con.createQuery(sql)
        .addParameter("brand_id", this.id)
        .addParameter("store_id", store.getId())
        .executeUpdate();
    }
  }
}
