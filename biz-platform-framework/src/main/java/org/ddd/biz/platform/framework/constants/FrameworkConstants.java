package org.ddd.biz.platform.framework.constants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.ddd.biz.platform.framework.i18n.LocalizedMessageInterpolator;

import javax.validation.Validation;
import javax.validation.Validator;

public class FrameworkConstants {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public static final String PROJECT_BIZ_FRAMEWORK = "biz_framework";

    public static final Validator FRAMEWORK_VALIDATOR = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new LocalizedMessageInterpolator())
            .buildValidatorFactory()
            .getValidator();
}
