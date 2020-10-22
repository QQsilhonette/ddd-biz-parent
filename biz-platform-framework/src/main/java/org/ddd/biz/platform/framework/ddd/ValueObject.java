package org.ddd.biz.platform.framework.ddd;

import java.io.Serializable;

public interface ValueObject extends Serializable {

    boolean equals(Object o);

    int hashCode();

}
