package com.ljr.invest_p2p.common;

/**
 * Created by LinJiaRong on 2017/4/12.
 * TODO：配置网络请求相关的地址
 */

public class AppNetConfig {

    public static final String IPADDRESS = "192.168.26.16";

    public static final String BASE_URL="http://"+IPADDRESS+":8080/P2PInvest/";

    public static final String PRODUCT = BASE_URL+"product";

    public static final String LOGIN = BASE_URL+"login";

    public static final String INDEX = BASE_URL+"index";

    public static final String USERREGISTER = BASE_URL+"UserRegister";

    public static final String FEEDBACK = BASE_URL+"FeedBack";

    public static final String UPDATE = BASE_URL+"update.json";

}
