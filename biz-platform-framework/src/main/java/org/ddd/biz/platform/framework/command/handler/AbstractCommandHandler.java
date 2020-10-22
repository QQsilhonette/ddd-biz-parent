package org.ddd.biz.platform.framework.command.handler;

import lombok.extern.slf4j.Slf4j;
import org.ddd.biz.platform.common.dto.Command;
import org.ddd.biz.platform.framework.command.context.CommandContext;
import org.ddd.biz.platform.framework.command.validator.ParamValidator;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.CompletableFuture;

@Slf4j
public abstract class AbstractCommandHandler<C extends Command> implements CommandHandler<C> {
    private final ParamValidator paramValidator = new ParamValidator();

    @Override
    public <R> R handle(C command) {
        paramValidate(command);
        boolean idempotentCheckHit = idempotentCheck(command);
        if (idempotentCheckHit) {
            return CommandContext.getIdempotentReturn();
        }
        bizValidate(command);
        preExecute(command);
        R result = execute(command);
        CompletableFuture.runAsync(() -> postExecuteAsync(command));
        return result;
    }

    /**
     *  参数校验
     * @param command
     */
    protected void paramValidate(C command) throws ConstraintViolationException {
        paramValidator.validate(command);
    }

    /**
     *  业务规则校验
     * @param command
     */
    protected void bizValidate(C command) {


    }

    /**
     * 幂等处理
     * @return 是否触发幂等处理
     */
    protected boolean idempotentCheck(C command) {
        return false;
    }


    protected void preExecute(C command) {

    }

    public abstract <R> R execute(C command);

    protected void postExecuteAsync(C command) {

    }

}
