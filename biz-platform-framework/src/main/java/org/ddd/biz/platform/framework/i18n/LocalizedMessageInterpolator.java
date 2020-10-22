package org.ddd.biz.platform.framework.i18n;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.MessageInterpolator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class LocalizedMessageInterpolator extends ResourceBundleMessageInterpolator {
    private static final Map<Locale, Locale> compatibleLocale = Maps.newHashMap();

    public LocalizedMessageInterpolator() {
        compatibleLocale.put(Locale.CHINESE, Locale.SIMPLIFIED_CHINESE);
        compatibleLocale.put(Locale.TAIWAN, Locale.SIMPLIFIED_CHINESE);
        compatibleLocale.put(Locale.TRADITIONAL_CHINESE, Locale.SIMPLIFIED_CHINESE);
    }

    /**
     * 将用户的 locale 信息传递给消息解释器，而不是使用默认的 JVM locale 信息
     */
    @Override
    public String interpolate(String message, MessageInterpolator.Context context) {
        Locale locale = Optional.ofNullable(compatibleLocale.get(LocaleContextHolder.getLocale()))
                .orElse(LocaleContextHolder.getLocale());
        log.info("validation locale is:{}", locale);
        return super.interpolate(message, context, locale);
    }

}
