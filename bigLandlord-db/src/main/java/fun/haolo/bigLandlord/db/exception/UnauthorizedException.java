package fun.haolo.bigLandlord.db.exception;

/**
 * @author haolo
 * @since 2022-10-21 16:02
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
