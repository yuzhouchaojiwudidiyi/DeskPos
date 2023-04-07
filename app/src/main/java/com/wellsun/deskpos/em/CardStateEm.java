package com.wellsun.deskpos.em;

/**
 * date     : 2023-03-27
 * author   : ZhaoZheng
 * describe :
 */
public enum CardStateEm {
    NO_INIT("00", "未初始化"),
    INIT("00", "已初始化"),
    ISSUE("01", "已发卡");
    String code;
    String name;

    CardStateEm(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
