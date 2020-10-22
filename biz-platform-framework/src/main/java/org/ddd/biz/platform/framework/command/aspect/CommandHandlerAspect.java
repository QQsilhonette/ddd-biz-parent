package org.ddd.biz.platform.framework.command.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.ddd.biz.platform.common.constants.SecurityConstants;
import org.ddd.biz.platform.common.dto.Command;
import org.ddd.biz.platform.common.dto.PlatformCommand;
import org.ddd.biz.platform.common.dto.UserCommand;
import org.ddd.biz.platform.common.exception.BizException;
import org.ddd.biz.platform.common.exception.GlobalErrorCode;
import org.ddd.biz.platform.framework.command.context.CommandContext;
import org.ddd.biz.platform.framework.security.SecurityContextUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Slf4j
public class CommandHandlerAspect {

    @Pointcut("within(org.ddd.biz.platform.framework.command.handler.CommandHandler+) && execution(* handle(..))")
    public void commandHandlerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut("within(org.ddd.biz.platform.framework.command.handler.QueryCommandHandler +) && execution(* query(..))")
    public void queryHandlerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * 打印命令请求日志，并且将应用层异常统一封装成CouncilException
     *
     * @param joinPoint
     * @return
     * @throws BizException
     */
    @Around("commandHandlerPointcut() ||  queryHandlerPointcut()")
    public Object process(ProceedingJoinPoint joinPoint) {
        log.info("Enter commandhandler: {} . {} () with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        try {
            if (joinPoint.getArgs() != null) {
                for (Object arg : joinPoint.getArgs()) {
                    if (arg instanceof Command) {
                        Command command = (Command) arg;
                        initContextParams(command);
                        break;
                    }
                }
            }
            Object result = joinPoint.proceed();
            log.info("result of commandhandler: {}.{}() with argument[s] = {} is:[{}]", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()), result);
            return result;
        } catch (ConstraintViolationException constraintViolationException) {
            Map<String, String> constraintViolationMessages = constraintViolationException.getConstraintViolations().stream()
                    .collect(Collectors.toMap(t -> t.getPropertyPath().iterator().next().getName(), t -> t.getMessage()));
            log.warn("constraintViolationException for commandhandler: {}.{}() with argument[s] = {}, constraintViolations: {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()), constraintViolationMessages);
            String fieldName = constraintViolationMessages.keySet().iterator().next();
            String constraintViolationMessage = constraintViolationMessages.values().iterator().next();
            throw new BizException(GlobalErrorCode.INVALID_PARAMS, constraintViolationMessage);
        } catch (BizException bizException) {
            BizException localizedBizException = new BizException(bizException.getErrorEnum(), bizException.getParams(), bizException.getParamErrors(), bizException.getParams());
            log.warn("BizException for commandhandler: {}.{}() with argument[s] = {}, BizException is:{}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()), localizedBizException);
            throw localizedBizException;
        } catch (Throwable throwable) {
            log.error("Unexpect exception for commandhandler: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()), throwable);
            throw new RuntimeException(throwable);
        } finally {
            CommandContext.clear();
        }
    }

    protected void initContextParams(Command command) {
        CommandContext.initialize();
        CommandContext.setContextParam(CommandContext.CONTEXT_PARAM_COMMAND, command);
        if (command instanceof PlatformCommand) {
            PlatformCommand platformCommand = (PlatformCommand) command;
            CommandContext.setContextParam(CommandContext.CONTEXT_PARAM_PLATFORM, platformCommand.getPlatform());
            CommandContext.setContextParam(CommandContext.CONTEXT_PARAM_TENANT_ID, platformCommand.getTenantId());
        }
        if (StringUtils.isNotBlank(command.getLang())) {
            LocaleContextHolder.setLocale(Locale.forLanguageTag(command.getLang()), true);
        }
        if (command instanceof UserCommand) {
            UserCommand userCommand = (UserCommand) command;
            if (StringUtils.isNotBlank(userCommand.getUid())) {
                SecurityContextUtils.setAuthentication(userCommand.getUid());
            } else {
                SecurityContextUtils.setAuthentication(SecurityConstants.ANONYMOUS);
            }
        } else {
            SecurityContextUtils.setAuthentication(SecurityConstants.SYSTEM);
        }
    }

}
