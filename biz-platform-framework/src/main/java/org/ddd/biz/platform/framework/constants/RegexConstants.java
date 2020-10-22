package org.ddd.biz.platform.framework.constants;

import java.util.regex.Pattern;

public interface RegexConstants {

    String REGEX_IDENTITY_ID = "(^\\d{18}$)|(^\\d{15}$)|(^\\d{17}([0-9]|x|X){1}$)";

    String REGEX_PASSPORT_NO = "^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$";
    String REGEX_MOBILE_NUMBER = "(1[3-9][0-9])\\d{8}$";
    String REGEX_EMAIL = "(\\w+)\\w{3}[^@]*(@.*$)";

    Pattern PATTERN_MOBILE_NUMBER = Pattern.compile(REGEX_MOBILE_NUMBER);
    Pattern PATTERN_EMAIL = Pattern.compile(REGEX_EMAIL);

    public static final String CARD_TYPE_SINOPEC_CAR_NUM = "^100011.{13}";
    public static final String CARD_TYPE_CNPC_CAR_NUM  = "^9.{15}";


}
