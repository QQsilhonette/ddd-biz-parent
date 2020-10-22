package org.ddd.biz.platform.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Setter
@Getter
@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class PlatformCommand extends Command {

    private static final long serialVersionUID = 1L;

    @NotNull
    protected Integer platform;

    @NotNull
    protected String tenantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlatformCommand)) {
            return false;
        }
        PlatformCommand that = (PlatformCommand) o;
        return Objects.equals(getPlatform(), that.getPlatform()) &&
                Objects.equals(getTenantId(), that.getTenantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlatform(), getTenantId());
    }
}
