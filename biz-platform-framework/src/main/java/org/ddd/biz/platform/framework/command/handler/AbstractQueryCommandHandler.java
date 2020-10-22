package org.ddd.biz.platform.framework.command.handler;

import lombok.extern.slf4j.Slf4j;
import org.ddd.biz.platform.common.dto.QueryCommand;
import org.ddd.biz.platform.framework.command.validator.ParamValidator;

import javax.validation.ConstraintViolationException;

@Slf4j
public abstract class AbstractQueryCommandHandler<Q extends QueryCommand> implements QueryCommandHandler<Q> {
    protected final ParamValidator paramValidator = new ParamValidator();

    @Override
    public <R> R query(Q queryCommand) {
        paramValidator.validate(queryCommand);
        bizValidate(queryCommand);
        return execute(queryCommand);
    }

    protected abstract <R> R execute(Q queryCommand);

    /**
     *  业务规则校验
     * @param queryCommand
     */
    protected void bizValidate(Q queryCommand) throws ConstraintViolationException {


    }

}
