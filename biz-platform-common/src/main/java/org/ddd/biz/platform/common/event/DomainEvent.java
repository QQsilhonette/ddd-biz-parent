package org.ddd.biz.platform.common.event;

import org.ddd.biz.platform.common.dto.DTO;

import java.time.Instant;

public abstract class DomainEvent extends DTO {
    public static final String FIELD_EVENT_TYPE = "eventType";

    protected Long eventTime = Instant.now().toEpochMilli();

    protected String eventType = getClass().getSimpleName();
    /**
     * 获取消息唯一业务id
     *
     * @return
     */
    public abstract String getEventBizId();

    public abstract String getDomain();

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
