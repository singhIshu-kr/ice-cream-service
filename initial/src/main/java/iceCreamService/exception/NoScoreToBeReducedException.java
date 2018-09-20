package iceCreamService.exception;

public class NoScoreToBeReducedException extends BadRequestException {
    public NoScoreToBeReducedException(String message) {
        super(message);
    }
}
