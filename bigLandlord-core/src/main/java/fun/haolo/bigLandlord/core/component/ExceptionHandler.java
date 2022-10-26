package fun.haolo.bigLandlord.core.component;

import fun.haolo.bigLandlord.core.api.ResponseResult;
import fun.haolo.bigLandlord.db.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * @author haolo
 * @since 2022-10-26 10:42
 */
@RestControllerAdvice
public class ExceptionHandler {

    @Autowired
    HttpServletResponse response;

    @org.springframework.web.bind.annotation.ExceptionHandler(value = UnauthorizedException.class)
    public ResponseResult<String> unauthorizedHandler(UnauthorizedException e) {
        response.setStatus(403);
        return ResponseResult.forbidden(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseResult<String> errorHandler(Exception e) {
        response.setStatus(500);
        return ResponseResult.failed(e.getMessage());
    }
}
