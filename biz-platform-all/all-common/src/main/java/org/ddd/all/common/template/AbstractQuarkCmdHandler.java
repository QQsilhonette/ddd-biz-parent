package org.ddd.all.common.template;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.ddd.biz.platform.common.dto.Command;
import org.ddd.biz.platform.framework.command.context.CommandContext;
import org.ddd.biz.platform.framework.command.handler.AbstractCommandHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
public abstract class AbstractQuarkCmdHandler<R, C extends Command> extends AbstractCommandHandler<C> {

    public volatile static boolean OPEN_VIOLATION_LOG = false;

    public R doHandle(C command) {
        try {
            log.info("【命令执行】-【in】-【{}】", JSON.toJSONString(command));
            this.paramValidate(command);
            boolean idempotentCheckHit = this.idempotentCheck(command);
            if (idempotentCheckHit) {
                return CommandContext.getIdempotentReturn();
            } else {
                this.bizValidate(command);
                this.preExecute(command);
                R result = this.doExecute(command);
                CompletableFuture.runAsync(() -> {
                    this.postExecuteAsync(command);
                });
                log.info("【命令执行】-【out】-【{}】", JSON.toJSONString(result));
                return result;
            }
        } catch (ConstraintViolationException e) {
            if (OPEN_VIOLATION_LOG) {
                Optional.ofNullable(e.getConstraintViolations())
                        .orElse(Collections.emptySet())
                        .stream()
                        .forEach(constraintViolation -> {
                            log.error("path={} msg={}", constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                        });
            }
            throw e;
        }
    }

    public abstract R doExecute(C var1);

    @Override
    @Deprecated
    public <R> R execute(C c) {
        return null;
    }
}
