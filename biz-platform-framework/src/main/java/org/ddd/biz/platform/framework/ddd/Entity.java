package org.ddd.biz.platform.framework.ddd;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;

@Component
@Scope("prototype")
public abstract class Entity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    protected T id;
    protected Instant gmtCreate;
    protected Instant gmtModified;

    private EntityStatus status = EntityStatus.PERSISTED;

    public Entity<T> markAsRemoved() {
        status = EntityStatus.DELETED;
        return this;
    }

    public Entity<T> markAsNew() {
        status = EntityStatus.NEW;
        return this;
    }

    public Entity<T> markAsPersisted() {
        status = EntityStatus.PERSISTED;
        return this;
    }

    public boolean isRemoved() {
        return status == EntityStatus.DELETED;
    }

    public boolean isPersisted() {
        return status == EntityStatus.PERSISTED;
    }

    public boolean isNew() {
        return status == EntityStatus.NEW;
    }

    public EntityStatus getEntityStatus() {
        return status;
    }

    public void setEntityStatus(EntityStatus entityStatus) {
        this.status = entityStatus;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Instant getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Instant gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Instant getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Instant gmtModified) {
        this.gmtModified = gmtModified;
    }

    public abstract void persist();

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", status=" + status +
                '}';
    }
}
