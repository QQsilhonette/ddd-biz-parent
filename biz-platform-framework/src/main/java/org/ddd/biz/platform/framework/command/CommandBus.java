package org.ddd.biz.platform.framework.command;

import org.ddd.biz.platform.common.dto.Command;
import org.ddd.biz.platform.common.dto.QueryCommand;
import org.ddd.biz.platform.framework.command.context.CommandContext;
import org.ddd.biz.platform.framework.command.handler.CommandHandler;
import org.ddd.biz.platform.framework.command.handler.HandlersProvider;
import org.ddd.biz.platform.framework.command.handler.QueryCommandHandler;

public class CommandBus {
    private final HandlersProvider handlersProvider;

    public CommandBus(HandlersProvider handlersProvider) {
        this.handlersProvider = handlersProvider;
    }

    public <T extends Command, R> R dispatch(T command) {
        CommandHandler<Command> handler = handlersProvider.getHandler(command);
        try {
            return handler.handle(command);
        } finally {
            CommandContext.clear();
        }

    }

    public <T extends QueryCommand, R> R query(QueryCommand queryCommand) {
        QueryCommandHandler<QueryCommand> queryHandler = handlersProvider.getQueryHandler(queryCommand);
        try {
            return queryHandler.query(queryCommand);
        } finally {
            CommandContext.clear();
        }
    }

}
