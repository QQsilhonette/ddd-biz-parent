package org.ddd.biz.platform.common.constants;

import java.util.HashSet;
import java.util.Set;

public class SecurityConstants {

    public static final String SYSTEM = "system";
    public static final String ANONYMOUS = "anonymous";

    public static final Set<String> SYS_USERS = new HashSet<>(2);

    static {
        SYS_USERS.add(SYSTEM);
        SYS_USERS.add(ANONYMOUS);
    }

}
