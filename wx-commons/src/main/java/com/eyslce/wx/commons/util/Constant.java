package com.eyslce.wx.commons.util;

import com.google.common.collect.ImmutableList;

public class Constant {
    /**
     * 图片资源状态：0：未引用	1：已被引用
     */
    public static final Integer IMG_FLAG0 = 0;
    public static final Integer IMG_FLAG1 = 1;
    public static final String SUCCESS_MSG = "操作成功";
    public static final String ERROR_MSG = "操作失败，请稍后再试";

    public static final String SYSTEM_NAME = "wx";
    public static final String SYSTEM_VERSION = "1.0.0";
    public static final String SYSTEM_UPDATE_TIME = "2018-06-01";

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

    public static final ImmutableList<String> MENU_NEED_KEY = ImmutableList.<String>builder()
            .add("click")
            .add("scancode_push")
            .add("scancode_waitmsg")
            .add("pic_sysphoto")
            .add("pic_photo_or_album")
            .add("pic_weixin")
            .add("location_select")
            .build();

    public enum CubeMethod {
        GET_USER_SUMMARY("getusersummary"),
        GET_USER_CUMULATE("getusercumulate"),
        GET_ARTICLE_SUMMARY("getarticlesummary"),
        GET_ARTICLE_TOTAL("getarticletotal"),
        GET_ARTICLE_READ("getuserread"),
        GET_ARTICLE_READHOUR("getuserreadhour"),
        GET_ARTICLE_SHARE("getusershare"),
        GET_ARTICLE_SHAREHOUR("getusersharehour");

        private String method;

        CubeMethod(String method) {
            this.method = method;
        }

        public String getMethod() {
            return this.method;
        }

        public static CubeMethod getEnumMethod(String method) {
            for (CubeMethod cubeMethod : CubeMethod.values()) {
                if (cubeMethod.getMethod().equals(method)) {
                    return cubeMethod;
                }
            }
            return null;
        }
    }
}
