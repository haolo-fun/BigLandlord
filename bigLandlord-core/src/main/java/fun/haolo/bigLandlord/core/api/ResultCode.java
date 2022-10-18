package fun.haolo.bigLandlord.core.api;

/**
 * @Author haolo
 * @Date 2022-10-12 22:04
 * @Description
 */
public enum ResultCode {
    SUCCESS(200, "success"),
    FAILED(500, "failed"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "未登录或登录已过期"),
    FORBIDDEN(403, "无权限");

    private final Integer code;
    private final String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
