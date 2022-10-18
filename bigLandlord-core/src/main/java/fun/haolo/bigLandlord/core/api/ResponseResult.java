package fun.haolo.bigLandlord.core.api;

/**
 * @Author haolo
 * @Date 2022-10-12 21:52
 * @Description
 */
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 查询到的结果数据，
     */
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> ResponseResult<T> success(String msg, T data) {
        return new ResponseResult<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResponseResult<T> failed() {
        return new ResponseResult<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg(), null);
    }

    public static <T> ResponseResult<T> failed(T data) {
        return new ResponseResult<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg(), data);
    }

    public static <T> ResponseResult<T> validateFailed() {
        return new ResponseResult<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMsg(), null);
    }

    public static <T> ResponseResult<T> validateFailed(T data) {
        return new ResponseResult<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMsg(), data);
    }

    public static <T> ResponseResult<T> unauthorized() {
        return new ResponseResult<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg(), null);
    }

    public static <T> ResponseResult<T> unauthorized(T data) {
        return new ResponseResult<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg(), data);
    }

    public static <T> ResponseResult<T> forbidden(T data) {
        return new ResponseResult<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg(), data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
