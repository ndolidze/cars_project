package ge.tsu.spring.car.car;

public class RecordNotFoundException extends Exception {

  public RecordNotFoundException(String message) {
    super(message);
  }

  public RecordNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

}
