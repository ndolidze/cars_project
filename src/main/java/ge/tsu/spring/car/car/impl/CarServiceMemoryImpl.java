package ge.tsu.spring.car.car.impl;

import ge.tsu.spring.car.car.AddCar;
import ge.tsu.spring.car.car.CarService;
import ge.tsu.spring.car.car.CarView;
import ge.tsu.spring.car.car.RecordAlreadyExistsException;
import ge.tsu.spring.car.car.RecordNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service("carMemoryImpl")
public class CarServiceMemoryImpl implements CarService {

  List<CarView> carViews = new ArrayList<>();

  @Override
  public void add(AddCar addCar) throws RecordAlreadyExistsException {
    Optional<CarView> exists = carViews
      .stream()
      .filter(it -> addCar.getManufacturer().equals(it.getManufacturer()) && it.getModel().equals(addCar.getModel()))
      .findFirst();
    if (exists.isPresent()) {
      throw new RecordAlreadyExistsException(
        String.format("Car with %s and %s already exists", addCar.getManufacturer(), addCar.getModel()));
    }
    carViews.add(new CarView(
      UUID.randomUUID().toString(),
      addCar.getManufacturer(),
      addCar.getModel(),
      addCar.getSpeed()
    ));
  }

  @Override
  public void update(String id, AddCar addCar) throws RecordAlreadyExistsException, RecordNotFoundException {
    for (CarView carView : carViews) {
      if (carView.getId().equals(id)) {
        carView.setManufacturer(addCar.getManufacturer());
        carView.setModel(addCar.getModel());
        carView.setSpeed(addCar.getSpeed());
        return;
      }
    }
    throw new RecordNotFoundException("Unable to find car with specified id");
  }

  @Override
  public List<CarView> getList(String manufacturer, String model) {
    if (manufacturer != null && model != null) {
      return carViews
        .stream()
        .filter(it -> it.getManufacturer().contains(manufacturer) && it.getModel().contains(model))
        .collect(Collectors.toList());
    }
    return carViews;
  }

  @Override
  public CarView getById(String id) throws RecordNotFoundException {
    for (CarView carView : carViews) {
      if (carView.getId().equals(id)) {
        return carView;
      }
    }
    throw new RecordNotFoundException("Unable to find car with specified id");
  }

  @Override
  public void delete(String id) throws RecordNotFoundException {
    Iterator<CarView> carViewIterator = carViews.iterator();
    while (carViewIterator.hasNext()) {
      CarView carView = carViewIterator.next();
      if (carView.getId().equals(id)) {
        carViewIterator.remove();
        return;
      }
    }
    throw new RecordNotFoundException("Unable to find car with specified id");
  }
}
