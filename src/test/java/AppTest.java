import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest{

  public WebDriver webDriver= new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver(){
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest(){
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("shoe stores + brands");
  }

  @Test
  public void addStoreWorks(){
    goTo("http://localhost:4567/");
    fill("#storeName").with("target");
    submit(".btn");
    assertThat(pageSource()).contains("target");
  }

  @Test
  public void addBrandWorks(){
    goTo("http://localhost:4567/");
    fill("#brandName").with("zappos");
    submit(".btn");
    assertThat(pageSource()).contains("zappos");
  }

  @Test
  public void addBrandtoStore(){
    Store myStore = new Store("store");
    myStore.save();
    Brand myBrand = new Brand("brand");
    myBrand.save();
    myStore.addBrand(myBrand);
    String path = String.format("http://localhost:4567/stores/%d", myStore.getId());
    goTo(path);
    assertThat(pageSource()).contains("brand");
  }

  @Test
  public void addStoretoBrand(){
    Store myStore = new Store("store");
    myStore.save();
    Brand myBrand = new Brand("brand");
    myBrand.save();
    myBrand.addStore(myStore);
    String path = String.format("http://localhost:4567/brands/%d", myBrand.getId());
    goTo(path);
    assertThat(pageSource()).contains("store");
  }

  @Test
  public void editStoreName(){
    Store myStore = new Store("store");
    myStore.save();
    myStore.updateStore("BEST STORE");
    String path = String.format("http://localhost:4567/stores/%d", myStore.getId());
    goTo(path);
    assertThat(pageSource()).contains("BEST STORE");
  }

}
