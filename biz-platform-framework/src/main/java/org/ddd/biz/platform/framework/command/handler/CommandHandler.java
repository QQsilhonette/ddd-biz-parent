package org.ddd.biz.platform.framework.command.handler;

import org.ddd.biz.platform.common.dto.Command;

public interface CommandHandler<C extends Command> {

    <R> R handle(C command);

}
