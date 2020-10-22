package org.ddd.biz.platform.common.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Pagination<T> implements Iterable, Serializable {

    /**
     * 总数量
     */
    private final long totalCount;

    /**
     * 分页数据
     */
    private final List<T> datas;

    public Pagination() {
        totalCount = 0;
        datas = Collections.emptyList();
    }

    public Pagination(long totalCount, List<T> datas) {
        this.totalCount = totalCount;
        this.datas = datas;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public List<T> getDatas() {
        return datas;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "totalCount=" + totalCount +
                ", datas=" + datas +
                '}';
    }

    @Override
    public Iterator iterator() {
        return datas.iterator();
    }
}
