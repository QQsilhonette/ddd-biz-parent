package org.ddd.biz.platform.framework.command.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.google.common.collect.Maps;
import org.ddd.biz.platform.common.dto.Command;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class CommandContext {
    public static final String CONTEXT_PARAM_COMMAND = "command";
    public static final String CONTEXT_PARAM_COMMAND_RESULT = "result";
    public final static String CONTEXT_IDEMPOTENT_RETURN = "idempotentReturn";
    public final static String CONTEXT_PARAM_PLATFORM = "platform";
    public final static String CONTEXT_PARAM_TENANT_ID = "tenantId";
    public final static String CONTEXT_PARAM_REQUESTER_UID = "requesterUid";

    private static TransmittableThreadLocal<Map<String, Object>> contextHolder = new TransmittableThreadLocal<>();

    private CommandContext() {

    }

    public static void initialize() {
        if (contextHolder.get() == null) {
            Map<String, Object> paramMap = Maps.newHashMap();
            contextHolder.set(paramMap);
        }
    }

    public static Map<String, Object> asMap() {
        return Optional.ofNullable(contextHolder.get())
                .orElse(Collections.emptyMap());
    }

    public static void setContextParam(String paramKey, Object paramValue) {
        initialize();
        if (paramValue != null) {
            contextHolder.get().put(paramKey, paramValue);
        }
    }

    public static <R> R getContextParam(String paramKey) {
        initialize();
        return (R) contextHolder.get().get(paramKey);
    }


    public static void clear() {
        contextHolder.remove();
    }

    public static <C extends Command> C getCommand() {
        return getContextParam(CONTEXT_PARAM_COMMAND);
    }

    public static <T> T getCommandResult() {
        return getContextParam(CONTEXT_PARAM_COMMAND_RESULT);
    }

    public static Integer getPlatform() {
        return getContextParam(CONTEXT_PARAM_PLATFORM);
    }

    public static String getTenantId() {
        return getContextParam(CONTEXT_PARAM_TENANT_ID);
    }

    public static <T> T getIdempotentReturn() {
        return getContextParam(CONTEXT_IDEMPOTENT_RETURN);
    }

}
