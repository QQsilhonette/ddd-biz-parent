package autoconfigure;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.ddd.biz.platform.framework.command.CommandBus;
import org.ddd.biz.platform.framework.command.aspect.CommandHandlerAspect;
import org.ddd.biz.platform.framework.command.handler.HandlersProvider;
import lombok.extern.slf4j.Slf4j;
import org.ddd.biz.platform.framework.command.handler.SpringHandlerProvider;
import org.ddd.biz.platform.framework.command.validator.ParamValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.ddd.biz.platform.framework.threadpool.TraceThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@ComponentScan(basePackages = "org.ddd.biz.platform.framework.converter")
@Slf4j
public class BizPlatformAutoConfiguration {

    @Bean
    public HandlersProvider handlersProvider(ConfigurableListableBeanFactory beanFactory) {
        SpringHandlerProvider springHandlerProvider = new SpringHandlerProvider(beanFactory);
        return springHandlerProvider;
    }

    @Bean
    public CommandHandlerAspect commandHandlerAspect() {
        return new CommandHandlerAspect();
    }

    @Bean
    public CommandBus commandBus(HandlersProvider handlersProvider) {
        return new CommandBus(handlersProvider);
    }

    @Bean
    public EventBus eventBus(@Value("${spring.application.name}") String appName) {
        EventBus eventBus = new EventBus(appName + "EventBus");
        return eventBus;
    }

    @Bean
    public AsyncEventBus asyncEventBus(ThreadPoolTaskExecutor threadPoolTaskExecutor, @Value("${spring.application.name}") String appName) {
        AsyncEventBus asyncEventBus = new AsyncEventBus(appName + "AsyncEventBus", threadPoolTaskExecutor);
        return asyncEventBus;
    }

    @Bean
    public ParamValidator paramValidator() {
        return new ParamValidator();
    }

    @Bean
    @Primary
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(@Value("${spring.application.name}") String appName) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new TraceThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix(appName + "-threadPoolTaskExecutor");
        threadPoolTaskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 4);
        threadPoolTaskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 8);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        threadPoolTaskExecutor.setQueueCapacity(Runtime.getRuntime().availableProcessors() * 4);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setThreadFactory(new ThreadFactoryBuilder().setUncaughtExceptionHandler((t, e) -> {
            log.error("thread={} throw Exception={}", t, e.getMessage(), e);
        }).build());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
