package org.ddd.biz.platform.common.exception;


import java.util.Arrays;

/**
 * Exception code for council.
 *
 * @author Ritchie Cai 2017/12/06
 */
public enum GlobalErrorCode implements ErrorEnum {

    /**
     * 系统异常
     */
    INVALID_PARAMS("ERROR.INVALID_PARAMS", "无效的参数:{0}, {1}"),
    BIZ_ERROR("ERROR.BIZ_ERROR", "业务异常"),
    UNAUTHORIZED("ERROR.UNAUTHORIZED", "未登录"),
    PERMISSION_DENIED("ERROR.PERMISSION_DENIED", "权限异常"),
    SERVER_ERROR("ERROR.SERVER_ERROR", "服务器开了个小差"),

    ;

    private String errorCode;
    private String message;

    GlobalErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }


    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }


    public Boolean isGlobalError(ErrorEnum errorCode) {
        return Arrays.stream(GlobalErrorCode.values())
                .filter(globalErrorCode -> globalErrorCode.equals(errorCode.getErrorCode()))
                .findAny()
                .isPresent();
    }
}
