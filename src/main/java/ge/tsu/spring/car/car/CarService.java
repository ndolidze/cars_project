package ge.tsu.spring.car.car;


import java.io.IOException;
import java.util.List;

public interface CarService {

  void add(AddCar addCar) throws RecordAlreadyExistsException, IOException, RecordNotFoundException;

  void update(String id, AddCar addCar)
    throws RecordAlreadyExistsException, RecordNotFoundException, IOException;

  List<CarView> getList(String manufacturer, String model)
    throws IOException, RecordNotFoundException;

  CarView getById(String id) throws RecordNotFoundException, IOException;

  void delete(String id) throws RecordNotFoundException, IOException;
}
