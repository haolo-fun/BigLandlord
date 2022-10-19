package fun.haolo.bigLandlord.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author haolo
 * @since 2022-10-19 15:15
 */
public class TokenLogoutException extends AuthenticationException {
    public TokenLogoutException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TokenLogoutException(String msg) {
        super(msg);
    }
}
