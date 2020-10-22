package org.ddd.all.common.template;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.ddd.biz.platform.common.dto.QueryCommand;
import org.ddd.biz.platform.framework.command.handler.AbstractQueryCommandHandler;

@Slf4j
public abstract class AbstractQuarkQueryCmdHandler<R, Q extends QueryCommand> extends AbstractQueryCommandHandler<Q> {
    public R doQuery(Q queryCommand) {
        log.info("【命令执行】-【in】-【{}】", JSON.toJSONString(queryCommand));
        this.paramValidator.validate(queryCommand);
        this.bizValidate(queryCommand);
        R r = this.doExecute(queryCommand);
        log.info("【命令执行】-【out】-【{}】", JSON.toJSONString(r));
        return r;
    }

    protected abstract R doExecute(Q var1);

    @Override
    @Deprecated
    protected <R> R execute(Q q) {
        return null;
    }
}
