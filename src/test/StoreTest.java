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

}
