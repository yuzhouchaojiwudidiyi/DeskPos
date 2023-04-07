package com.wellsun.deskpos.util;


import com.wellsun.deskpos.base.BasePreference;

/**储存工具类
 *content:
 *time: 2020/10/21
 *@author: ZhaoZheng
 */
public class SPUtils extends BasePreference {
    private static SPUtils spUtils;

    public synchronized static SPUtils getInstance() {
        if (null == spUtils) {
            spUtils = new SPUtils();
        }
        return spUtils;
    }

    /**
     * 需要增加key就在这里新建
     */
    //用户名的key
    private static final String USER_NAME = "user_name";
    private static final String PASS_WORD = "pass_word";
    private static final String STATION_ID = "station_id";
    private static final String STATION_NAME = "station_name";


    /**
     * 账号
     *
     * @return
     */
    public String getUSER_NAME() {
        return getString(USER_NAME);
    }

    public void setUSER_NAME(String user_name) {
        setString(USER_NAME, user_name);
    }

    /**
     * 密码
     *
     * @return
     */
    public String getPASS_WORD() {
        return  getString(PASS_WORD);
    }

    public void setPASS_WORD(String pass_word) {
        setString(PASS_WORD, pass_word);
    }


    /**
     * 设置站点名字
     *
     * @return
     */
    public String getStationName() {
        return getString(STATION_NAME);
    }

    public void setStationName(String publicKey) {
        setString(STATION_NAME, publicKey);
    }

    /**
     * 设置站点id
     *
     * @return
     */
    public String getStationId() {
        return getString(STATION_ID);
    }

    public void setStationId(String publicKey) {
        setString(STATION_ID, publicKey);
    }
    public void clear() {
        sp.edit().clear();
        sp.edit().commit();
    }
}
