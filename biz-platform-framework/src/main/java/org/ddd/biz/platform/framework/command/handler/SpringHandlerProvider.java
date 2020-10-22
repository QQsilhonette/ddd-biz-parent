package org.ddd.biz.platform.framework.command.handler;

import lombok.extern.slf4j.Slf4j;
import org.ddd.biz.platform.common.dto.Command;
import org.ddd.biz.platform.common.dto.QueryCommand;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SpringHandlerProvider implements HandlersProvider, ApplicationListener<ApplicationReadyEvent> {
    private final Map<Class<?>, String> cmdHandlerRepository = new HashMap<>();
    private final Map<Class<?>, String> queryHandlerRepository = new HashMap<>();

    private final ConfigurableListableBeanFactory beanFactory;

    public SpringHandlerProvider(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public CommandHandler<Command> getHandler(Command command) {
        String beanName = cmdHandlerRepository.get(command.getClass());
        if (beanName == null) {
            throw new RuntimeException("command handler not found. CommandAnnotation class is " + command.getClass());
        }
        CommandHandler<Command> handler = beanFactory.getBean(beanName, CommandHandler.class);
        return handler;
    }

    @Override
    public QueryCommandHandler<QueryCommand> getQueryHandler(QueryCommand queryCommand) {
        String beanName = queryHandlerRepository.get(queryCommand.getClass());
        if (beanName == null) {
            throw new RuntimeException("query handler not found. CommandAnnotation class is " + queryCommand.getClass());
        }
        QueryCommandHandler<QueryCommand> handler = beanFactory.getBean(beanName, QueryCommandHandler.class);
        return handler;
    }

    private Class<?> getCommandClass(Class<?> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        ParameterizedType type = findByRawType(genericInterfaces, CommandHandler.class);
        if (type == null) {
            Type genericSuperClass = clazz.getGenericSuperclass();
            type = (ParameterizedType) genericSuperClass;
        }
        return (Class<?>) type.getActualTypeArguments()[0];
    }

    private Class<?> getQueryCommandClass(Class<?> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        ParameterizedType type = findByRawType(genericInterfaces, QueryCommandHandler.class);
        if (type == null) {
            Type genericSuperClass = clazz.getGenericSuperclass();
            type = (ParameterizedType) genericSuperClass;
        }
        return (Class<?>) type.getActualTypeArguments()[0];
    }

    private ParameterizedType findByRawType(Type[] genericInterfaces, Class<?> expectedRawType) {
        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parametrized = (ParameterizedType) type;
                if (expectedRawType.equals(parametrized.getRawType())) {
                    return parametrized;
                }
            }
        }
        return null;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        init();
    }

    private void init() {
        log.info("start to init SpringHandlerProvider");
        String[] commandHandlersNames = beanFactory.getBeanNamesForType(CommandHandler.class);
        for (String beanName : commandHandlersNames) {
            BeanDefinition commandHandler = beanFactory.getBeanDefinition(beanName);
            try {
                Class<?> handlerClass = Class.forName(commandHandler.getBeanClassName());
                Class<?> commandType = getCommandClass(handlerClass);
                cmdHandlerRepository.put(commandType, beanName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        String[] queryCommandHandlersNames = beanFactory.getBeanNamesForType(QueryCommandHandler.class);
        for (String beanName : queryCommandHandlersNames) {
            BeanDefinition queryCommandHandler = beanFactory.getBeanDefinition(beanName);
            try {
                Class<?> handlerClass = Class.forName(queryCommandHandler.getBeanClassName());
                Class<?> queryCommandType = getQueryCommandClass(handlerClass);
                queryHandlerRepository.put(queryCommandType, beanName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("complete  to init SpringHandlerProvider");
    }
}
