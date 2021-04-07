package ge.tsu.spring.car.car.impl;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ge.tsu.spring.car.car.AddCar;
import ge.tsu.spring.car.car.CarService;
import ge.tsu.spring.car.car.CarView;
import ge.tsu.spring.car.car.RecordAlreadyExistsException;
import ge.tsu.spring.car.car.RecordNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service("carFileImpl")
public class CarServiceFileImpl implements CarService {

  private final String JSON_DATA = Objects
    .requireNonNull(getClass().getClassLoader().getResource("data/cars.json")).getPath();


  @Override
  public void add(AddCar addCar)
    throws RecordAlreadyExistsException, IOException {
    List<CarView> carViews = getList(null, null);
    if (carViews != null && !carViews.isEmpty()) {
      Optional<CarView> exists = carViews
        .stream()
        .filter(it -> addCar.getManufacturer().equals(it.getManufacturer()) && it.getModel()
          .equals(addCar.getModel()))
        .findFirst();
      if (exists.isPresent()) {
        throw new RecordAlreadyExistsException(
          String.format("Car With Manufacturer: '%s' and Model: '%s' already exists",
            addCar.getManufacturer(), addCar.getModel()));
      }
    }
    CarView carView = new CarView
      (UUID.randomUUID().toString(),
        addCar.getManufacturer(),
        addCar.getModel(),
        addCar.getSpeed());
    carViews.add(carView);
    File file = new File(JSON_DATA);
    FileWriter fileWriter = new FileWriter(file);
    ObjectMapper mapper = new ObjectMapper();
    JsonGenerator g = mapper.getFactory().createGenerator(fileWriter);
    g.writeObject(carViews);
  }

  @Override
  public void update(String id, AddCar addCar)
    throws RecordNotFoundException, IOException {
    List<CarView> carViews = getList(null, null);
    if (carViews != null && !carViews.isEmpty()) {
      int sizeOfList = carViews.size();
      for (CarView carView : carViews) {
        if (carView.getId().equals(id)) {
          carViews.remove(carView);
          break;
        } else if (carViews.get(sizeOfList - 1).equals(carView)) {
          throw new RecordNotFoundException("Unable to find car with specified id");
        }
      }
      carViews.add(new CarView(id, addCar.getManufacturer(), addCar.getModel(), addCar.getSpeed()));
      File file = new File(JSON_DATA);
      FileWriter fileWriter = new FileWriter(file);
      ObjectMapper mapper = new ObjectMapper();
      JsonGenerator g = mapper.getFactory().createGenerator(fileWriter);
      g.writeObject(carViews);
    } else {
      throw new RecordNotFoundException("Unable to find car with specified id");
    }

  }

  @Override
  public List<CarView> getList(String manufacturer, String model)
    throws IOException {
    File file = new File(JSON_DATA);
    if (file.length() == 0) {
      return new ArrayList<>();
    }
    ObjectMapper objectMapper = new ObjectMapper();
    List<CarView> cars = objectMapper.readValue(file, new TypeReference<List<CarView>>() {
    });

    if (manufacturer != null) {
      cars
        .stream()
        .filter(it -> it.getManufacturer().contains(manufacturer))
        .collect(Collectors.toList());
    }
    if (model != null) {
      cars
        .stream()
        .filter(it -> it.getModel().contains(model))
        .collect(Collectors.toList());
    }
    return cars;
  }

  @Override
  public CarView getById(String id) throws RecordNotFoundException, IOException {
    List<CarView> carViews = getList(null, null);
    for (CarView car : carViews) {
      if (car.getId().equals(id)) {
        return car;
      }
    }
    throw new RecordNotFoundException(String.format("Car With Id: '%s' can't be found", id));
  }

  @Override
  public void delete(String id) throws RecordNotFoundException, IOException {
    List<CarView> carViews = getList(null, null);
    if (carViews != null && !carViews.isEmpty()) {
      int sizeOfList = carViews.size();
      for (CarView carView : carViews) {
        if (carView.getId().equals(id)) {
          carViews.remove(carView);
          break;
        } else if (carViews.get(sizeOfList - 1).equals(carView)) {
          throw new RecordNotFoundException("Unable to find car with specified id");
        }
      }
      File file = new File(JSON_DATA);
      FileWriter fileWriter = new FileWriter(file);
      ObjectMapper mapper = new ObjectMapper();
      JsonGenerator g = mapper.getFactory().createGenerator(fileWriter);
      g.writeObject(carViews);
    } else {
      throw new RecordNotFoundException("Unable to find car with specified id");
    }
  }
}
