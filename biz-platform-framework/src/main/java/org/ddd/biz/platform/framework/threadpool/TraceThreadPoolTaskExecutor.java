package org.ddd.biz.platform.framework.threadpool;

import com.alibaba.ttl.TtlRunnable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.skywalking.apm.toolkit.trace.RunnableWrapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TraceThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
    private ScheduledExecutorService scheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor(new BasicThreadFactory.Builder().namingPattern("async thread executor monitor").build());
    private boolean initialize = false;

    @Override
    public void initialize() {
        if (!initialize) {
            super.initialize();
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                log.info("TraceThreadPoolTaskExecutor monitor for threadPool:[{}], corePoolSize:{}, activeCount:{}, poolSize:{}, maxPoolSize:{}",
                        getThreadNamePrefix(),
                        getCorePoolSize(),
                        getActiveCount(),
                        getPoolSize(),
                        getMaxPoolSize());
            }, 1, 1, TimeUnit.MINUTES);
            initialize = true;
        }

    }

    @Override
    public void execute(Runnable task) {
        Runnable ttlRunnable = TtlRunnable.get(task);
        super.execute(RunnableWrapper.of(ttlRunnable));
    }

}
