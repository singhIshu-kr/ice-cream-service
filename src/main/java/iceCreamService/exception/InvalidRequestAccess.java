package iceCreamService.exception;

public class InvalidRequestAccess extends BadRequestException {
    public InvalidRequestAccess(String message) {
        super(message);
    }
}
