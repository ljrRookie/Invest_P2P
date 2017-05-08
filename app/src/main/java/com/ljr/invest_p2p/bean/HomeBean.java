package com.ljr.invest_p2p.bean;

import java.util.List;

/**
 * Created by LinJiaRong on 2017/4/12.
 * TODO：
 */

public class HomeBean {

    /**
     * imageArr : [{"ID":"1","IMAPAURL":"http://gwop.xtrich.com/xtApp/lexianghuo1.html","IMAURL":"http://192.168.24.145:8080/P2PInvest/images/index01.png"},{"ID":"2","IMAPAURL":"http://gwop.xtrich.com/xtApp/new-plan.html","IMAURL":"http://192.168.24.145:8080/P2PInvest/images/index02.png"},{"ID":"3","IMAPAURL":"http://gwop.xtrich.com/xtApp/new-plan.html","IMAURL":"http://192.168.24.145:8080/P2PInvest/images/index03.png"},{"ID":"5","IMAPAURL":"http://gwop.xtrich.com/xtApp/twcx.html","IMAURL":"http://192.168.24.145:8080/P2PInvest/images/index04.png"}]
     * proInfo : {"id":"1","memberNum":"100","minTouMoney":"100","money":"10","name":"硅谷彩虹新手计划","progress":"90","suodingDays":"30","yearRate":"8.00"}
     */

    public ProInfoBean proInfo;
    public List<ImageArrBean> imageArr;

    public static class ProInfoBean {
        /**
         * id : 1
         * memberNum : 100
         * minTouMoney : 100
         * money : 10
         * name : 硅谷彩虹新手计划
         * progress : 90
         * suodingDays : 30
         * yearRate : 8.00
         */

        public String id;
        public String memberNum;
        public String minTouMoney;
        public String money;
        public String name;
        public String progress;
        public String suodingDays;
        public String yearRate;
    }

    public static class ImageArrBean {
        /**
         * ID : 1
         * IMAPAURL : http://gwop.xtrich.com/xtApp/lexianghuo1.html
         * IMAURL : http://192.168.24.145:8080/P2PInvest/images/index01.png
         */

        public String ID;
        public String IMAPAURL;
        public String IMAURL;
    }
}
