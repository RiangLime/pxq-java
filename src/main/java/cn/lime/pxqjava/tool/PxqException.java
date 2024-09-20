package cn.lime.pxqjava.tool;

public class PxqException extends Exception{
    /**
     * 错误码
     */
    private final int code;

    public PxqException(int code, String message) {
        super(message);
        this.code = code;
    }

    public PxqException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public PxqException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
