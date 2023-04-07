package com.wellsun.deskpos.em;

/**
 * date     : 2023-03-27
 * author   : ZhaoZheng
 * describe :
 */
public enum CardTypeEm {
    RegisterCard("80", "记名卡"),
    NoRegisterCard("88", "不记名卡");
    String code;
    String name;

    CardTypeEm(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
