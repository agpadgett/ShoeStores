import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class BrandTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst(){
    assertEquals(0, Brand.all().size());
  }

  @Test
  public void override_returnsTrueIfInputIsSame(){
    Brand brand1 = new Brand ("brand");
    Brand brand2 = new Brand ("brand");
    assertEquals(brand1, brand2);
  }

  @Test
  public void save_savesCorrectlyIntoDatabases(){
    Brand myBrand = new Brand("brand");
    myBrand.save();
    assertEquals(myBrand, Brand.all().get(0));
  }

  @Test
  public void getId_returnsId(){
    Brand myBrand = new Brand ("brand");
    myBrand.save();
    Brand savedBrand = Brand.all().get(0);
    assertEquals(savedBrand.getId(), myBrand.getId());
  }

  @Test
  public void getBrand_returnsBrand(){
    Brand myBrand = new Brand ("brand");
    myBrand.save();
    assertEquals("brand", myBrand.getBrand());
  }

  @Test
  public void find_findsBrandInDatabase(){
    Brand myBrand = new Brand ("brand");
    myBrand.save();
    assertEquals(Brand.find(myBrand.getId()), myBrand);
  }

  @Test
  public void delete_DeletesFromDatabase(){
    Brand myBrand = new Brand("brand");
    myBrand.save();
    myBrand.delete();
    assertEquals(false, myBrand.equals(Brand.find(myBrand.getId())));
  }

  @Test
  public void getStores_returnsStores(){
    Brand myBrand = new Brand("brand");
    myBrand.save();
    Store myStore = new Store("store");
    myStore.save();
    myBrand.addStore(myStore);
    assertEquals(myBrand.getStores().get(0), myStore);
  }
}
