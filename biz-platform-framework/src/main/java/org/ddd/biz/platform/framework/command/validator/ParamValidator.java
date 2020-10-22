package org.ddd.biz.platform.framework.command.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import static org.ddd.biz.platform.framework.constants.FrameworkConstants.FRAMEWORK_VALIDATOR;

@Slf4j
public class ParamValidator {

    public <C extends Object> void validate(C command) throws ConstraintViolationException {
        Set<ConstraintViolation<C>> constraintViolations = FRAMEWORK_VALIDATOR.validate(command);
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
