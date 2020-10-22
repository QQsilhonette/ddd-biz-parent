package org.ddd.biz.platform.framework.command.handler;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.ddd.biz.platform.common.dto.Command;

import javax.annotation.Resource;

@Slf4j
public abstract class AbstractDistributeLockedCommandHandler<C extends Command> extends AbstractCommandHandler<C> {

    @Resource
//    protected LokiClient lokiClient;
    protected int waitTime = 1000;
    protected int releaseTime = 5000;

    @Override
    public <R> R handle(C command) {
//        String lockKey = Joiner.on("_").join(command.getClass().getSimpleName(), command.hashCode());
//        boolean locked = lokiClient.opsForValue().trySimpleLock(lockKey, waitTime, releaseTime);
//        if (!locked) {
//            log.warn("exception to acquire lock for command:[{}]", command);
//            throw new RuntimeException("exception to acquire lock for command:" + command);
//        }
//        try {
            return super.handle(command);
//        } finally {
//            lokiClient.opsForValue().releaseSimpleLock(lockKey);
//        }
    }

}
