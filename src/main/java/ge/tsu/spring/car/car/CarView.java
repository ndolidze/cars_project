package ge.tsu.spring.car.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CarView {

  private String id;
  private String manufacturer;
  private String model;
  private float speed;

  @JsonCreator
  public CarView( @JsonProperty("id") String id, @JsonProperty("manufacturer") String manufacturer,
    @JsonProperty("model") String model,  @JsonProperty("speed") float speed) {
    this.id = id;
    this.manufacturer = manufacturer;
    this.model = model;
    this.speed = speed;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }
}
