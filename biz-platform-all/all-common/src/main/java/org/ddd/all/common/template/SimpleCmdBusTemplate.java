package org.ddd.all.common.template;

import org.ddd.biz.platform.common.dto.Command;
import org.ddd.biz.platform.common.dto.QueryCommand;
import org.ddd.biz.platform.framework.command.context.CommandContext;
import org.springframework.stereotype.Component;

@Component
public class SimpleCmdBusTemplate {
    public <R, Q extends QueryCommand> R query(AbstractQuarkQueryCmdHandler<R, Q> queryCommandHandler, Q queryCommand) {
        R var3;
        try {
            var3 = queryCommandHandler.doQuery(queryCommand);
        } finally {
            CommandContext.clear();
        }
        return var3;
    }

    public <R, C extends Command> R handle(AbstractQuarkCmdHandler<R, C> cudCommandHandler, C cudCmd) {
        R var3;
        try {
            var3 = cudCommandHandler.doHandle(cudCmd);
        } finally {
            CommandContext.clear();
        }
        return var3;
    }

}
