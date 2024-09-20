package cn.lime.pxqjava.tool;

/**
 * 自定义错误码
 */
public enum ErrorCode {

    ALREADY_BOUGHT(27902319,"has bought")

    ;

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}