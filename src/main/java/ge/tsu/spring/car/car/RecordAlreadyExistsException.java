package ge.tsu.spring.car.car;

public class RecordAlreadyExistsException extends Exception {

  public RecordAlreadyExistsException(String message) {
    super(message);
  }

  public RecordAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

}
