package org.ddd.biz.platform.framework.command.handler;

import org.ddd.biz.platform.common.dto.Command;
import org.ddd.biz.platform.common.dto.QueryCommand;

public interface HandlersProvider {
    CommandHandler<Command> getHandler(Command command);

    QueryCommandHandler<QueryCommand> getQueryHandler(QueryCommand queryCommand);
}
