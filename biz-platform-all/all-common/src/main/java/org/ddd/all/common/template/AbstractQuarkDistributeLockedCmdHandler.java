package org.ddd.all.common.template;


import org.ddd.biz.platform.common.dto.Command;
import org.ddd.biz.platform.framework.command.context.CommandContext;
import org.ddd.biz.platform.framework.command.handler.AbstractDistributeLockedCommandHandler;

import java.util.concurrent.CompletableFuture;

public abstract class AbstractQuarkDistributeLockedCmdHandler<R, C extends Command> extends AbstractDistributeLockedCommandHandler<C> {

    public R doHandle(C command) {
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
            return result;
        }
    }


    public abstract R doExecute(C var1);

    @Override
    @Deprecated
    public <R> R execute(C c) {
        return null;
    }
}
