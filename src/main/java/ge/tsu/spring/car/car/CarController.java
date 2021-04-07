package ge.tsu.spring.car.car;


import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

  @Autowired
  @Qualifier("carFileImpl")
  private CarService carService;

  @GetMapping("/cars")
  public List<CarView> searchCars(
    @RequestParam(required = false) String manufacturer,
    @RequestParam(required = false) String model) throws IOException, RecordNotFoundException {
    return carService.getList(manufacturer, model);
  }

  @GetMapping("/cars/{carId}")
  public CarView getCars(@PathVariable String carId) throws RecordNotFoundException, IOException {
    return carService.getById(carId);
  }

  @PostMapping("/cars")
  public void addCar(@RequestBody AddCar addCar)
    throws RecordAlreadyExistsException, IOException, RecordNotFoundException {
    carService.add(addCar);
  }

  @PutMapping("/cars/{carId}")
  public void updateCar(@RequestBody AddCar addCar, @PathVariable String carId)
    throws RecordAlreadyExistsException, RecordNotFoundException, IOException {
    carService.update(carId, addCar);
  }

  @DeleteMapping("/cars/{carId}")
  public void deleteCar(@PathVariable String carId) throws RecordNotFoundException, IOException {
    carService.delete(carId);
  }
}
