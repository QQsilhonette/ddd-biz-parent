package org.ddd.biz.platform.framework.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ddd.biz.platform.framework.mybatis.annotation.GmtCreate;
import org.ddd.biz.platform.framework.mybatis.annotation.GmtModified;
import org.ddd.biz.platform.framework.mybatis.annotation.LogicDelete;

import java.io.Serializable;

@Getter
@Setter
@ToString
public abstract class AbstractDataObject implements Serializable {
    private static final long serialVersionUID = -1L;

    protected Long id;

    @LogicDelete
    protected Long isDelete;

    @GmtCreate
    protected Long gmtCreate;

    @GmtModified
    protected Long gmtModified;

}
