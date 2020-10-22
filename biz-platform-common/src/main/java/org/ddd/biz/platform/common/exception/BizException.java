package org.ddd.biz.platform.common.exception;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.ddd.biz.platform.common.exception.GlobalErrorCode.BIZ_ERROR;

/**
 * Exception class for council.
 *
 * @author Ritchie Cai 2017/12/06
 */
public class BizException extends RuntimeException {
    protected ErrorEnum errorEnum;
    protected Object[] params;
    protected Map<String, String> paramErrors;

    public BizException() {
        super();
        this.errorEnum = BIZ_ERROR;
        this.params = Collections.emptyMap().values().toArray();
        this.paramErrors = Collections.emptyMap();
    }

    public BizException(ErrorEnum errorEnum) {
        this(errorEnum, Collections.emptyMap());
    }

    public BizException(ErrorEnum errorCode, Object... params) {
        this(errorCode, errorCode.getErrorMessage(), params);
    }

    public BizException(ErrorEnum errorEnum, String localizedMessage, Object... params) {
        super(localizedMessage);
        this.errorEnum = errorEnum;
        this.params = params;
        this.paramErrors = Collections.emptyMap();
    }

    public BizException(ErrorEnum errorEnum, Map<String, String> paramErrors) {
        this(errorEnum, errorEnum.getErrorMessage(), paramErrors);
    }

    public BizException(ErrorEnum errorEnum, String localizedMessage, Map<String, String> paramErrors) {
        super(localizedMessage);
        this.errorEnum = errorEnum;
        this.params = paramErrors.keySet().toArray();
        this.paramErrors = paramErrors;
    }

    public BizException(ErrorEnum errorEnum, String localizedMessage, Map<String, String> paramErrors, Object... params) {
        super(localizedMessage);
        this.errorEnum = errorEnum;
        this.params = params;
        this.paramErrors = paramErrors;
    }

    public String getErrorCode() {
        return errorEnum.getErrorCode();
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public Object[] getParams() {
        return params;
    }

    public Map<String, String> getParamErrors() {
        return paramErrors;
    }

    public BizException setErrorEnum(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
        return this;
    }

    public BizException setParams(Object[] params) {
        this.params = params;
        return this;
    }

    public BizException setParamErrors(Map<String, String> paramErrors) {
        this.paramErrors = paramErrors;
        return this;
    }

    public Boolean isParamException() {
        return GlobalErrorCode.INVALID_PARAMS.equals(errorEnum);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BizException{");
        sb.append("message=").append(getMessage());
        sb.append(", errorEnum=").append(errorEnum);
        sb.append(", params=").append(params == null ? "null" : Arrays.asList(params).toString());
        sb.append(", paramErrors=").append(paramErrors);
        sb.append('}');
        return sb.toString();
    }
}
