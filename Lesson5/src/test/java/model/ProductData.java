package model;
import java.util.Random;

public class ProductData {
  private String name;
  private int qty;
  private float price;

  public ProductData(String name, int qty, float price) {
    this.name = name;
    this.qty = qty;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public int getQty() {
    return qty;
  }

  public float getPrice() {
    return price;
  }


  public static ProductData generate() {
    Random random = new Random();
    return new ProductData(
            "New Product " + System.currentTimeMillis(),
            random.nextInt(100) + 1,
            (float) Math.round(random.nextInt(100_00) + 1) / 100);
  }
}
