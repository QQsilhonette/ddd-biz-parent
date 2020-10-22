package org.ddd.biz.platform.framework.command.handler;

import org.ddd.biz.platform.common.dto.QueryCommand;

/**
 * 查询执行器
 * @param <Q>
 */
public interface QueryCommandHandler<Q extends QueryCommand> {

    <R> R query(Q queryCommand);
}
