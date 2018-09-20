package iceCreamService.exception;

public class InvalidMemberOrTeamIdException extends BadRequestException {
    public InvalidMemberOrTeamIdException(String message) {
        super(message);
    }

}


