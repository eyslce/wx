package com.eyslce.wx.commons.util;

public class Constant {
    public static final String SUCCESS_MSG = "操作成功";

    public static final String SYSTEM_NAME = "wx";
    public static final String SYSTEM_VERSION = "1.0.0";
    public static final String SYSTEM_UPDATE_TIME = "2018-06-01";

    public final static String firstMenuName = "生活助手";
    public final static String secondMenuName = "";
    public final static String thirdMenuName = "";


    public enum Menu {
        Delivery("delivery", "查询快递");

        private String key;
        private String name;

        Menu(String key, String name) {
            this.key = key;
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public static Menu getMenu(String key) {
            for (Menu menu : Menu.values()) {
                if (menu.key.equals(key)) {
                    return menu;
                }
            }
            return null;
        }
    }

    public enum Lang {
        Eng("eng"),
        CHI_SIM("chi_sim");
        private String index;

        Lang(String index) {
            this.index = index;
        }

        public String getIndex() {
            return index;
        }
    }

    public enum MsgType {

        Text("text"),//文本消息
        News("news"),//图文消息
        Location("location"),//地理位置消息
        Image("image"),//图片消息
        Voice("voice"),//语音消息
        Video("video"),//视频消息
        Event("event"),//事件消息

        MPNEWS("mpnews"),//群发图文消息

        SUBSCRIBE("subscribe"),//订阅消息
        UNSUBSCRIBE("unsubscribe");//取消订阅

        private String name;

        private MsgType(String name) {
            this.name = name;
        }
    }


}
