import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App{
  public static void main(String[] args){
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Brand> brands = Brand.all();
      List<Store> stores = Store.all();
      model.put("stores", stores);
      model.put("brands", brands);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stores", (request, response) -> {
      String storeName = request.queryParams("storeName");
      Store newStore = new Store(storeName);
      newStore.save();
      response.redirect("/");
      return null;
    });

    post("/store_delete", (request, response) -> {
      int storeId = Integer.parseInt(request.queryParams("store_id"));
      Store deadStore = Store.find(storeId);
      deadStore.delete();
      response.redirect("/");
      return null;
    });

    post("/brands", (request, response) -> {
      String brandName= request.queryParams("brandName");
      Brand newBrand = new Brand(brandName);
      newBrand.save();
      response.redirect("/");
      return null;
    });

    post("brand_delete", (request, response) -> {
      int brandId = Integer.parseInt(request.queryParams("brand_id"));
      Brand deadBrand = Brand.find(brandId);
      deadBrand.delete();
      response.redirect("/");
      return null;
    });

    get("stores/:store_id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int storeId = Integer.parseInt(request.params("store_id"));
      Store store = Store.find(storeId);
      List<Brand> brandsInStore = store.getBrands();
      model.put("brandsInStore", brandsInStore);
      model.put("store", store);
      model.put("allBrands", Brand.all());
      model.put("template", "templates/store.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("stores/:store_id/add", (request, response) -> {
      int storeId = Integer.parseInt(request.queryParams("store_id"));
      Store store = Store.find(storeId);
      int newBrandId = Integer.parseInt(request.queryParams("brandId"));
      Brand newBrand = Brand.find(newBrandId);
      newBrand.addStore(store);
      String storeIdPath = String.format("/stores/%d", storeId);
      response.redirect(storeIdPath);
      return null;
    });

    post("stores/:store_id/edit", (request, response) -> {
      int storeId = Integer.parseInt(request.queryParams("store_id"));
      Store store = Store.find(storeId);
      String newName = request.queryParams("newStoreName");
      store.updateStore(newName);
      String path = String.format("/stores/%d", storeId);
      response.redirect(path);
      return null;
    });

    get("brands/:brand_id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int brandId = Integer.parseInt(request.params("brand_id"));
      Brand brand = Brand.find(brandId);
      List<Store> storesWithBrand = brand.getStores();
      model.put("storesWithBrand", storesWithBrand);
      model.put("brand", brand);
      model.put("allStores", Store.all());
      model.put("template", "templates/brand.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("brands/:brand_id/add", (request, response) -> {
      int brandId = Integer.parseInt(request.queryParams("brand_id"));
      Brand brand = Brand.find(brandId);
      int newStoreId = Integer.parseInt(request.queryParams("storeId"));
      Store newStore = Store.find(newStoreId);
      newStore.addBrand(brand);
      String brandIdPath = String.format("/brands/%d", brandId);
      response.redirect(brandIdPath);
      return null;
    });
  }
}
