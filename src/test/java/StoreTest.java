import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class StoreTest{
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst(){
    assertEquals(0, Store.all().size());
  }

  @Test
  public void equals_returnsTrueIfInputIsTheSame(){
    Store store1 = new Store("Store");
    Store store2 = new Store("Store");
    assertTrue(store1.equals(store2));
  }

  @Test
  public void save_savesInfoIntDatabase(){
    Store myStore = new Store("Store");
    myStore.save();
    assertEquals(myStore, Store.all().get(0));
  }

  @Test
  public void getId_returnsId(){
    Store myStore = new Store ("Store");
    myStore.save();
    assertEquals(Store.all().get(0).getId(), myStore.getId());
  }

  @Test
  public void getStore_returnsStoreName(){
    Store myStore = new Store ("Store");
    myStore.save();
    assertEquals("Store", myStore.getStore());
  }

  @Test
  public void find_findStoreInDatabas(){
    Store myStore = new Store("Store");
    myStore.save();
    assertEquals(Store.find(myStore.getId()), myStore);
  }

  @Test
   public void updateStore_changesName(){
    Store myStore = new Store("Store");
    myStore.save();
    myStore.updateStore("place");
    assertEquals("place", myStore.getStore());
  }

  @Test
  public void delete_DeletesFromDatabase(){
    Store myStore = new Store("Store");
    myStore.save();
    myStore.delete();
    assertEquals(false, myStore.equals(Store.find(myStore.getId())));
  }

  @Test
  public void getBrands_returnsBrands(){
    Store myStore = new Store("store");
    myStore.save();
    Brand myBrand = new Brand("shoes");
    myBrand.save();
    myStore.addBrand(myBrand);
    assertEquals(myStore.getBrands().get(0), myBrand);
  }

}
