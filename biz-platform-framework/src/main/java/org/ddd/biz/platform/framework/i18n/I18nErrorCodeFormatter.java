package org.ddd.biz.platform.framework.i18n;

import org.apache.commons.lang3.StringUtils;
import org.ddd.biz.platform.common.exception.BizErrorEnum;
import org.ddd.biz.platform.common.exception.ErrorEnum;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class I18nErrorCodeFormatter {
    private final MessageSource applicationMessageSource;

    public I18nErrorCodeFormatter(MessageSource applicationMessageSource) {
        this.applicationMessageSource = applicationMessageSource;
    }

    public String getErrorMessage(ErrorEnum errorCode, Object... params) {
        String errorMessage = "";
        if (errorCode instanceof BizErrorEnum) {
            errorMessage = applicationMessageSource.getMessage(errorCode.getErrorCode(), params, errorCode.getErrorMessage(), LocaleContextHolder.getLocale());
        }
        if(StringUtils.isBlank(errorMessage)) {
            errorMessage = errorCode.formatMessage(params);
        }
        return errorMessage;
    }

}
