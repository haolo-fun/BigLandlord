package fun.haolo.bigLandlord.core.component;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
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
    private HttpServletResponse response;

    private static final Log log = LogFactory.get();

    @org.springframework.web.bind.annotation.ExceptionHandler(value = UnauthorizedException.class)
    public ResponseResult<String> unauthorizedHandler(UnauthorizedException e) {
        response.setStatus(403);
        return ResponseResult.forbidden(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseResult<String> errorHandler(Exception e) {
//        response.setStatus(500);
        if (e.getMessage()==null){
            log.error(e);
        }else {
            log.error(e.getMessage());
        }
        return ResponseResult.failed(e.getMessage());
    }
}
