package org.ddd.biz.platform.common.exception;

import java.io.Serializable;
import java.text.MessageFormat;

public interface ErrorEnum extends Serializable {

    String getErrorCode();

    String getErrorMessage();

    default String formatMessage(Object... params) {
        return MessageFormat.format(getErrorMessage(), params);
    }

}
