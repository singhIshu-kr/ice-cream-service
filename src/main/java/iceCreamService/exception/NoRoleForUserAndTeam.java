package iceCreamService.exception;

public class NoRoleForUserAndTeam extends ResourceNotFoundException {

    public NoRoleForUserAndTeam(String message) {
        super(message);
    }
}
