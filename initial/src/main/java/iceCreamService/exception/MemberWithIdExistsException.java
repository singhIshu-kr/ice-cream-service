package iceCreamService.exception;


public class MemberWithIdExistsException extends BadRequestException {
    public MemberWithIdExistsException(String message) {
        super(message);
    }
}
